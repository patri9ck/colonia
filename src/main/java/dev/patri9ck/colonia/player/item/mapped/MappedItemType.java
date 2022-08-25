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
