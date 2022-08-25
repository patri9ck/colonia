package dev.patri9ck.colonia.player.item.mapped.item;

import dev.patri9ck.colonia.player.item.mapped.MappedItem;
import dev.patri9ck.colonia.player.item.mapped.MappedItemType;
import dev.patri9ck.colonia.player.synchronization.PlayerData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Optional;

@NoArgsConstructor
public class PlayerItem implements MappedItem {

    @Getter
    private String data;

    public PlayerItem(@NonNull PlayerData playerData) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        yamlConfiguration.set("data", playerData);

        data = yamlConfiguration.saveToString();
    }

    @NonNull
    public Optional<PlayerData> toPlayerData() {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        try {
            yamlConfiguration.loadFromString(data);

            return Optional.ofNullable(yamlConfiguration.getSerializable("data", PlayerData.class));
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @NonNull
    @Override
    public MappedItemType getMappedItemType() {
        return MappedItemType.PLAYER;
    }
}
