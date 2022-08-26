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
package dev.patri9ck.colonia.player;

import dev.patri9ck.colonia.player.item.ItemManagerHolder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {

    private final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();

    private final ItemManagerHolder itemManagerHolder;

    public PlayerProfileManager(ItemManagerHolder itemManagerHolder) {
        this.itemManagerHolder = itemManagerHolder;
    }

    public PlayerProfile getPlayerProfile(Player player) {
        return getPlayerProfile(player.getUniqueId());
    }

    public PlayerProfile getPlayerProfile(UUID uuid) {
        if (playerProfiles.containsKey(uuid)) {
            return playerProfiles.get(uuid);
        }

        PlayerProfile playerProfile = new PlayerProfile(itemManagerHolder, uuid);

        playerProfiles.put(uuid, playerProfile);

        return playerProfile;
    }
}
