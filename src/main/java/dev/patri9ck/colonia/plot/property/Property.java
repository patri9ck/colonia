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

import dev.patri9ck.colonia.plot.Area;
import dev.patri9ck.colonia.plot.Plot;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Property extends Plot {

    private double price;
    private Set<Flag> flags;
    private Set<UUID> members;

    public Property(long id, Area area, UUID uuid, String server, double price, Set<Flag> flags, Set<UUID> members) {
        super(id, area, uuid, server);

        this.price = price;
        this.flags = flags;
        this.members = members;
    }

    public Property(Area area, String server, double price) {
        this(NONE_ID, area, null, server, price, new HashSet<>(), new HashSet<>());
    }

    public boolean isMember(Player player) {
        return isMember(player.getUniqueId());
    }

    public boolean isMember(UUID uuid) {
        return members.contains(uuid);
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

    public void setFlags(Set<Flag> flags) {
        this.flags = flags;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public void setMembers(Set<UUID> members) {
        this.members = members;
    }
}
