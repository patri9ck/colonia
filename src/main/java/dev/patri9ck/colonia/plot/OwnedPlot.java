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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OwnedPlot extends Plot {

    private UUID uuid;
    private final Set<UUID> members;
    private final Set<Flag> flags;

    public OwnedPlot(long id, int x, int y, double price, String server, UUID uuid, Set<UUID> members, Set<Flag> flags) {
        super(id, x, y, price, server);

        this.uuid = uuid;
        this.members = members;
        this.flags = flags;
    }

    public OwnedPlot(long id, int x, int y, double price, String server, UUID uuid) {
        this(id, x, y, price, server, uuid, new HashSet<>(), new HashSet<>());
    }

    public UUID getUuid() {
        return uuid;
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
