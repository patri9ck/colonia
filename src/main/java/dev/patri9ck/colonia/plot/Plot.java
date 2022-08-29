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
package dev.patri9ck.colonia.plot;

import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.UUID;

public abstract class Plot {

    public static final long NOT_SAVED_ID = -1L;

    private long id;
    private Vector first;
    private Vector second;
    private UUID uuid;

    private final String server;

    protected Plot(long id, Vector first, Vector second, UUID uuid, String server) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.uuid = uuid;
        this.server = server;
    }

    public void unclaim() {
        setUuid(null);
    }

    public boolean isClaimed() {
        return uuid != null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vector getFirst() {
        return first;
    }

    public void setFirst(Vector first) {
        this.first = first;
    }

    public Vector getSecond() {
        return second;
    }

    public void setSecond(Vector second) {
        this.second = second;
    }

    public Optional<UUID> getUuid() {
        return Optional.ofNullable(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getServer() {
        return server;
    }
}
