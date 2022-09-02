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

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public abstract class Plot {

    public static final long NONE_ID = -1L;
    private final String server;
    private long id;
    private Area area;
    private UUID uuid;

    protected Plot(long id, Area area, UUID uuid, String server) {
        this.id = id;
        this.area = area;
        this.uuid = uuid;

        this.server = server;
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getUniqueId());
    }

    public boolean isOwner(UUID uuid) {
        return this.uuid.equals(uuid);
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
