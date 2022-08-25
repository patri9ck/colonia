package dev.patri9ck.colonia.player.item.mapped;

import lombok.NonNull;

public interface MappedItem {

    @NonNull
    MappedItemType getMappedItemType();
}
