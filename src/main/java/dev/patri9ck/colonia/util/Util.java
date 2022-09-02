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
package dev.patri9ck.colonia.util;

import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Util {

    private Util() {}

    public static void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException ignored) {}
    }

    public static void assertAsync() {
        if (Bukkit.isPrimaryThread()) {
            throw new RuntimeException("Must be run asynchronously!");
        }
    }

    public static Optional<UUID> parseUuid(String uuid) {
        try {
            return Optional.of(UUID.fromString(uuid));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    public static <E extends Enum<E>> Optional<E> parseEnum(String name, Class<E> enumClass) {
        try {
            return Optional.of(enumClass.cast(enumClass.getDeclaredMethod("valueOf", String.class).invoke(null, name)));
        } catch (ReflectiveOperationException exception) {
            return Optional.empty();
        }
    }
}
