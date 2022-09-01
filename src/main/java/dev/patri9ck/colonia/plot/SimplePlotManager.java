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

import dev.patri9ck.colonia.connection.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SimplePlotManager implements PlotManager {

    private static final String SELECT_PLOTS_SQL = "SELECT * FROM %s";
    private static final String SELECT_PLOTS_PER_USER_SQL = "SELECT * FROM %s WHERE uuid = ?";
    private static final String SELECT_PLOT_SQL = "SELECT * FROM %s WHERE id = ?";

    private final SqlConnectionManager sqlConnectionManager;
    private final String table;

    protected SimplePlotManager(SqlConnectionManager sqlConnectionManager, String table) {
        this.sqlConnectionManager = sqlConnectionManager;
        this.table = table;
    }

    @Override
    public List<Plot> load() {
        return sqlConnectionManager.consumeConnection(connection -> {
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(String.format(SELECT_PLOTS_SQL, table))) {
                return parsePlots(resultSet);
            }
        }).orElse(new ArrayList<>());
    }

    @Override
    public List<Plot> load(UUID uuid) {
        return sqlConnectionManager.consumeConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(SELECT_PLOTS_PER_USER_SQL, table))) {
                preparedStatement.setString(1, uuid.toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return parsePlots(resultSet);
                }
            }
        }).orElse(new ArrayList<>());
    }

    protected abstract List<Plot> parsePlots(ResultSet resultSet) throws SQLException;

    protected boolean exists(Connection connection, Plot plot) throws SQLException {
        long id = plot.getId();

        if (id == Plot.NONE_ID) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(SELECT_PLOT_SQL, table))) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
