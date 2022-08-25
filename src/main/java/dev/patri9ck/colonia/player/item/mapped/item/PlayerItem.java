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
package dev.patri9ck.colonia.player.item.mapped.item;

import dev.patri9ck.colonia.player.item.mapped.MappedItem;
import dev.patri9ck.colonia.player.item.mapped.MappedItemType;
import dev.patri9ck.colonia.player.synchronization.PlayerData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Optional;

@NoArgsConstructor
public class PlayerItem implements MappedItem {

    @Getter
    private String data;

    public PlayerItem(@NonNull PlayerData playerData) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        yamlConfiguration.set("data", playerData);

        data = yamlConfiguration.saveToString();
    }

    @NonNull
    public Optional<PlayerData> toPlayerData() {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        try {
            yamlConfiguration.loadFromString(data);

            return Optional.ofNullable(yamlConfiguration.getSerializable("data", PlayerData.class));
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @NonNull
    @Override
    public MappedItemType getMappedItemType() {
        return MappedItemType.PLAYER;
    }
}
