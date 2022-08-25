package dev.patri9ck.colonia.player.item;

import dev.patri9ck.colonia.player.item.mapped.MappedItem;
import dev.patri9ck.colonia.player.item.mapped.MappedItemType;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface ItemManager {

    boolean save(@NonNull MappedItem mappedItem);

    Optional<MappedItem> load(@NonNull UUID uuid, @NonNull MappedItemType mappedDataType);
}
