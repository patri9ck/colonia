/*
 * Copyright (C) 2022 Patrick Zwick and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.patri9ck.colonia.player.synchronization;

import dev.patri9ck.colonia.connection.jedis.JedisConnectionManager;
import dev.patri9ck.colonia.player.PlayerProfileManager;
import dev.patri9ck.colonia.player.item.mapped.MappedItemType;
import dev.patri9ck.colonia.player.item.mapped.item.PlayerItem;
import dev.patri9ck.colonia.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerSynchronizationManager implements Listener {

    private static final String PLAYER_KEY = "player.%s";
    private static final int ATTEMPTS = 5;
    private static final long WAIT_TIME_SECONDS = 3;
    private static final long REFRESH_TIME_SECONDS = 20;

    private final Map<UUID, BukkitTask> bukkitTasks = new HashMap<>();

    private final JedisConnectionManager jedisConnectionManager;
    private final PlayerProfileManager playerProfileManager;
    private final Plugin plugin;

    public PlayerSynchronizationManager(JedisConnectionManager jedisConnectionManager, PlayerProfileManager playerProfileManager, Plugin plugin) {
        this.jedisConnectionManager = jedisConnectionManager;
        this.playerProfileManager = playerProfileManager;
        this.plugin = plugin;
    }

    // To-Do: Exception Handling
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        String key = getPlayerKey(uuid);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (int i = 0; i < ATTEMPTS; i++) {
                if (jedisConnectionManager.consumeConnection(jedis -> !jedis.exists(key)).orElse(false)) {
                    playerProfileManager
                            .getPlayerProfile(player)
                            .getItem(MappedItemType.PLAYER)
                            .map(playerItem -> (PlayerItem) playerItem)
                            .ifPresent(playerItem -> Bukkit.getScheduler().runTask(plugin, () -> playerItem.toPlayerData().ifPresent(playerData -> playerData.apply(player))));

                    bukkitTasks.put(uuid, Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> jedisConnectionManager.supplyConnection(jedis -> jedis.setex(key, REFRESH_TIME_SECONDS / 2, ".")), 0L, TimeUnit.SECONDS.toMillis(REFRESH_TIME_SECONDS)));

                    return;
                }

                Util.sleep(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
            }
        });
    }

    // To-Do: Exception Handling
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bukkit.getServer().getName();

        UUID uuid = player.getUniqueId();

        if (bukkitTasks.containsKey(uuid)) {
            bukkitTasks.remove(uuid).cancel();

            PlayerData playerData = new PlayerData(player);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                playerProfileManager.getPlayerProfile(player).saveItem(MappedItemType.PLAYER, new PlayerItem(playerData));

                jedisConnectionManager.supplyConnection(jedis -> jedis.del(getPlayerKey(uuid)));
            });
        }
    }

    private String getPlayerKey(UUID uuid) {
        return String.format(PLAYER_KEY, uuid);
    }
}
