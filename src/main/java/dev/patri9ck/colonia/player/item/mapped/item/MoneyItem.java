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

import java.util.UUID;

public class MoneyItem implements MappedItem {

    private UUID uuid;
    private double amount;

    @Override
    public MappedItemType getMappedItemType() {
        return MappedItemType.MONEY;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
