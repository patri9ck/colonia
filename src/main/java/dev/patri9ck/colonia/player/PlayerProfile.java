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

import dev.patri9ck.colonia.player.item.Item;
import dev.patri9ck.colonia.player.item.ItemType;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerProfile {

    @NonNull
    private final Map<ItemType, Item> items = new HashMap<>();

    @NonNull
    @Getter
    private final Player player;

    public PlayerProfile(@NonNull Player player) {
        this.player = player;
    }

    public Optional<Item> getData(@NonNull ItemType itemType) {
        return Optional.of(items.get(itemType));
    }

    public void saveData(@NonNull ItemType itemType, @NonNull Item item) {
        items.put(itemType, item);
    }

    public void removeData(@NonNull ItemType itemType) {
        items.remove(itemType);
    }
}
