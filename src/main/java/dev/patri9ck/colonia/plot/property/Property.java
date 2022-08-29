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
package dev.patri9ck.colonia.plot.property;

import dev.patri9ck.colonia.plot.Plot;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Property extends Plot {

    private double price;

    private final Set<Flag> flags = new HashSet<>();
    private final Set<UUID> members = new HashSet<>();

    public Property(long id, Vector first, Vector second, UUID uuid, String server, double price) {
        super(id, first, second, uuid, server);

        this.price = price;
    }

    public Property(Vector first, Vector second, String server, double price) {
        this(Plot.NOT_SAVED_ID, first, second, null, server, price);
    }

    public void claim(UUID uuid) {
        setUuid(uuid);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Flag> getFlags() {
        return flags;
    }

    public Set<UUID> getMembers() {
        return members;
    }


}
