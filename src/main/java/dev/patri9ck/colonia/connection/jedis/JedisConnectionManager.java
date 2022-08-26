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
package dev.patri9ck.colonia.connection.jedis;

import dev.patri9ck.colonia.connection.ConnectionConsumer;
import dev.patri9ck.colonia.connection.ConnectionManager;
import dev.patri9ck.colonia.connection.ConnectionSupplier;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

public class JedisConnectionManager implements ConnectionManager<Jedis> {

    private final JedisPool pool;

    public JedisConnectionManager(String host, int port, String user, String password) {
        pool = new JedisPool(host, port, user, password);
    }

    @Override
    public <T> Optional<T> consumeConnection(ConnectionConsumer<Jedis, T> connectionConsumer) {
        try (Jedis jedis = pool.getResource()) {
            return Optional.ofNullable(connectionConsumer.consume(jedis));
        } catch (Exception exception) {
            handleException(exception);
        }

        return Optional.empty();
    }

    @Override
    public void supplyConnection(ConnectionSupplier<Jedis> connectionSupplier) {
        try (Jedis jedis = pool.getResource()) {
            connectionSupplier.run(jedis);
        } catch (Exception exception) {
            handleException(exception);
        }
    }

    private void handleException(Exception exception) {
        exception.printStackTrace();
    }
}
