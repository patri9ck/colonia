package dev.patri9ck.colonia.player.item;

import lombok.NonNull;

import java.util.UUID;

public interface Item {

    @NonNull
    UUID getUuid();
}
