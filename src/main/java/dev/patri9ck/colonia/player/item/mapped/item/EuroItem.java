package dev.patri9ck.colonia.player.item.mapped.item;

import dev.patri9ck.colonia.player.item.mapped.MappedItem;
import dev.patri9ck.colonia.player.item.mapped.MappedItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class EuroItem implements MappedItem {

    @Getter
    private long amount;

    @NonNull
    @Override
    public MappedItemType getMappedItemType() {
        return MappedItemType.EURO;
    }
}
