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
package dev.patri9ck.colonia.player.item.mapped;

import dev.patri9ck.colonia.player.item.ItemType;
import dev.patri9ck.colonia.player.item.mapped.item.MoneyItem;
import dev.patri9ck.colonia.player.item.mapped.item.PlayerItem;
import org.jetbrains.annotations.NotNull;

public enum MappedItemType implements ItemType {

    MONEY("money", MoneyItem.class),
    PLAYER("player", PlayerItem.class);

    private final String table;
    private final Class<? extends MappedItem> itemClass;

    MappedItemType(@NotNull String table, @NotNull Class<? extends MappedItem> itemClass) {
        this.table = table;
        this.itemClass = itemClass;
    }

    public String getTable() {
        return table;
    }

    public Class<? extends MappedItem> getItemClass() {
        return itemClass;
    }
}
