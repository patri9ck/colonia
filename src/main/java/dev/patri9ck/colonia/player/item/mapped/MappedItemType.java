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
import dev.patri9ck.colonia.player.item.mapped.item.EuroItem;
import dev.patri9ck.colonia.player.item.mapped.item.PlayerItem;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public enum MappedItemType implements ItemType {

    EURO("euro", EuroItem.class),
    PLAYER("player", PlayerItem.class);

    @NonNull
    @Getter
    private final String table;

    @NonNull
    @Getter
    private final Class<? extends MappedItem> type;

    MappedItemType(@NotNull String table, @NotNull Class<? extends MappedItem> type) {
        this.table = table;
        this.type = type;
    }
}
