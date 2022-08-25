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
