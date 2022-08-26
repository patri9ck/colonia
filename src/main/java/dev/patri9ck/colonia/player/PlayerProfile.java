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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.patri9ck.colonia.player.item.Item;
import dev.patri9ck.colonia.player.item.ItemManager;
import dev.patri9ck.colonia.player.item.ItemManagerHolder;
import dev.patri9ck.colonia.player.item.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PlayerProfile {

    private static final long ITEM_EXPIRATION_MINUTES = 3;

    private final ItemManagerHolder itemManagerHolder;
    private final UUID uuid;

    private final LoadingCache<ItemType, Item> items = CacheBuilder
            .newBuilder()
            .expireAfterWrite(ITEM_EXPIRATION_MINUTES, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public @NotNull Item load(@NotNull ItemType itemType) {
                    return itemManagerHolder.getItemManager(itemType).flatMap(itemManager -> itemManager.load(uuid, itemType)).orElseThrow();
                }
            });

    public PlayerProfile(ItemManagerHolder itemManagerHolder, UUID uuid) {
        this.itemManagerHolder = itemManagerHolder;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void saveItem(ItemType itemType, Item item) {
        if (items.asMap().containsKey(itemType)) {
            items.put(itemType, item);
        }

        itemManagerHolder.getItemManager(itemType).ifPresent(itemManager -> itemManager.save(item));
    }

    public Optional<Item> getItem(ItemType itemType) {
        try {
            return Optional.of(items.get(itemType));
        } catch (ExecutionException exception) {
            return Optional.empty();
        }
    }
}
