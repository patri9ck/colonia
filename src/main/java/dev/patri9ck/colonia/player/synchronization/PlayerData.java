package dev.patri9ck.colonia.player.synchronization;

import lombok.NonNull;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData implements ConfigurationSerializable {

    private final double health;
    private final int foodLevel;
    private final double saturation;
    private final double exhaustion;
    private final int experience;

    private final ItemStack[] inventory;

    private final Collection<PotionEffect> potionEffects;

    public PlayerData(@NonNull Player player) {
        health = player.getHealth();
        foodLevel = player.getFoodLevel();
        saturation = player.getSaturation();
        exhaustion = player.getExhaustion();
        potionEffects = player.getActivePotionEffects();
        experience = player.getTotalExperience();
        inventory = player.getInventory().getContents();
    }

    public PlayerData(@NonNull Map<String, Object> args) {
        health = (double) args.get("health");
        foodLevel = (int) args.get("foodLevel");
        saturation = (double) args.get("saturation");
        exhaustion = (double) args.get("exhaustion");
        experience = (int) args.get("experience");
        inventory = ((List<ItemStack>) args.get("inventory")).toArray(new ItemStack[0]);
        potionEffects = (Collection<PotionEffect>) args.get("potionEffects");
    }

    public void apply(@NonNull Player player) {
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setSaturation((float) saturation);
        player.setExhaustion((float) exhaustion);
        player.setTotalExperience(experience);
        player.getInventory().setContents(inventory);

        applyPotionEffects(player);
    }

    private void applyPotionEffects(Player player) {
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.addPotionEffects(potionEffects);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> args = new HashMap<>();

        args.put("health", health);
        args.put("foodLevel", foodLevel);
        args.put("saturation", saturation);
        args.put("exhaustion", exhaustion);
        args.put("potionEffects", potionEffects);
        args.put("experience", experience);
        args.put("inventory", inventory);

        return args;
    }
}
