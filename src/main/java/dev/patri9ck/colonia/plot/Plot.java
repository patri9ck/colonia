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

import dev.patri9ck.colonia.Colonia;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Plot {

    public static final long NOT_SAVED_ID = -1L;

    private long id;
    private int x;
    private int y;
    private int height;
    private int depth;
    private double price;
    private UUID uuid;

    private final Set<UUID> members = new HashSet<>();
    private final Set<Flag> flags = new HashSet<>();

    protected Plot(long id, int x, int y, int height, int depth, double price, UUID uuid) {
        this.id = id;
        this.x = x;
        this.y= y;
        this.height = height;
        this.depth = depth;
        this.price = price;
        this.uuid = uuid;
    }

    public static Plot newPlot(int x, int y, int height, int depth, double price) {
        return new Plot(NOT_SAVED_ID, x, y, height, depth, price, null);
    }

    public static Plot newOwnedPlot(int x, int y, int height, int depth, double price, UUID uuid, String server) {
        return new Plot(NOT_SAVED_ID, x, y, height, depth, price, uuid);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Optional<UUID> getUuid() {
        return Optional.ofNullable(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Set<Flag> getFlags() {
        return flags;
    }
}
