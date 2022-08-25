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
package dev.patri9ck.colonia.connection.sql;

import dev.patri9ck.colonia.connection.ConnectionConsumer;
import dev.patri9ck.colonia.connection.ConnectionManager;
import dev.patri9ck.colonia.connection.ConnectionRunnable;
import lombok.NonNull;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class SqlConnectionManager implements ConnectionManager<Connection> {

    private static final String SCHEME = "jdbc:mariadb://%s:%d/%s?user=%s&password=%s";

    @NonNull
    private final MariaDbPoolDataSource pool;

    public SqlConnectionManager(@NonNull String host, int port, @NonNull String database, @NonNull String user, @NonNull String password) throws SQLException {
        pool = new MariaDbPoolDataSource(String.format(SCHEME, host, port, database, user, password));
    }

    public <T> Optional<T> connection(@NonNull ConnectionConsumer<Connection, T> connectionConsumer) {
        try (Connection connection = pool.getConnection()) {
            return Optional.ofNullable(connectionConsumer.consume(connection));
        } catch (Exception exception) {
            handleException(exception);
        }

        return Optional.empty();
    }

    public void connection(@NonNull ConnectionRunnable<Connection> connectionRunnable) {
        try (Connection connection = pool.getConnection()) {
            connectionRunnable.run(connection);
        } catch (Exception exception) {
            handleException(exception);
        }
    }

    private void handleException(Exception exception) {
        exception.printStackTrace();
    }
}
