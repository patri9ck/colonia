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
