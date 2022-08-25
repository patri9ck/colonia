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
package dev.patri9ck.colonia.player.item;

import java.util.HashMap;
import java.util.Map;

public class ItemManagerHolder {

    private final Map<Class<?>, ItemManager<?, ?>> itemManagers = new HashMap<>();

    public void addItemManager(ItemManager<?, ?> itemManager) {
        itemManagers.put(itemManager.getItemTypeClass(), itemManager);
    }

    public <I extends Item, T extends ItemType> ItemManager<I, T> getItemManager(ItemType itemType) {
        return (ItemManager<I, T>) itemManagers.get(itemType.getClass());
    }
}
