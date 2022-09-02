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

import org.bukkit.Location;
import org.bukkit.World;

public class Area {

    private final World world;

    private final int firstX;
    private final int firstY;
    private final int firstZ;
    private final int secondX;
    private final int secondY;
    private final int secondZ;

    public Area(Location first, Location second) {
        this(first.getWorld(), first.getBlockX(), first.getBlockY(), first.getBlockZ(), second.getBlockX(), second.getBlockY(), second.getBlockZ());
    }

    public Area(World world, int firstX, int firstY, int firstZ, int secondX, int secondY, int secondZ) {
        this.world = world;

        this.firstX = firstX;
        this.firstY = firstY;
        this.firstZ = firstZ;
        this.secondX = secondX;
        this.secondY = secondY;
        this.secondZ = secondZ;
    }

    public int getWidthX() {
        return Math.max(firstX, secondX) - Math.min(firstX, secondX);
    }

    public int getWidthZ() {
        return Math.max(firstZ, secondZ) - Math.min(firstZ, secondZ);
    }

    public int getHeight() {
        return Math.max(firstY, secondY) - Math.min(firstY, secondY);
    }

    public boolean contains(Location location) {
        return contains(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean contains(World world, int x, int y, int z) {
        return world.getName().equals(this.world.getName())
                && x >= Math.min(firstX, secondX) && x < Math.max(firstX, secondX)
                && y >= Math.min(firstY, secondY) && y < Math.max(firstY, secondY)
                && z >= Math.min(firstZ, secondZ) && z < Math.max(firstZ, secondZ);
    }

    public Location getFirst() {
        return new Location(world, firstX, firstY, firstZ);
    }

    public Location getSecond() {
        return new Location(world, secondX, secondY, secondZ);
    }

    public World getWorld() {
        return world;
    }

    public int getFirstX() {
        return firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public int getFirstZ() {
        return firstZ;
    }

    public int getSecondX() {
        return secondX;
    }

    public int getSecondY() {
        return secondY;
    }

    public int getSecondZ() {
        return secondZ;
    }
}
