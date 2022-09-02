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
package dev.patri9ck.colonia.plot.shop;

import dev.patri9ck.colonia.plot.Area;
import dev.patri9ck.colonia.plot.Plot;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class Shop extends Plot {

    private Instant expiration;

    public Shop(long id, Area area, UUID uuid, String server, Instant expiration) {
        super(id, area, uuid, server);

        this.expiration = expiration;
    }

    public Shop(Area area, String server) {
        this(NONE_ID, area, null, server, null);
    }

    @Override
    public void unclaim() {
        super.unclaim();

        setExpiration(null);
    }

    public void claim(UUID uuid, Instant expiration) {
        setUuid(uuid);
        setExpiration(expiration);
    }

    public Optional<Instant> getExpiration() {
        return Optional.ofNullable(expiration);
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
