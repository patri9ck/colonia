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
import dev.patri9ck.colonia.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotManager {

    private static final String TABLE = "plots";

    private static final String LOAD_PLOTS_SQL = "SELECT * FROM %s WHERE server = ?";
    private static final String LOAD_PLOTS_PER_UUID_SQL = "SELECT * FROM %s WHERE server = ? AND uuid = ?";
    private static final String SAVE_PLOT_SQL = "REPLACE INTO %s (x, y) VALUES (%s)";

    private final SqlConnectionManager sqlConnectionManager;
    private final String server;

    public PlotManager(SqlConnectionManager sqlConnectionManager, String server) {
        this.sqlConnectionManager = sqlConnectionManager;
        this.server = server;
    }

    public List<Plot> loadPlots() {
        return sqlConnectionManager.consumeConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(LOAD_PLOTS_SQL, TABLE))) {
                preparedStatement.setString(1, server);

                return parsePlots(preparedStatement.executeQuery());
            }
        }).orElse(new ArrayList<>());
    }

    public List<Plot> loadPlots(UUID uuid) {
        return sqlConnectionManager.consumeConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(LOAD_PLOTS_PER_UUID_SQL, TABLE))) {
                preparedStatement.setString(1, server);
                preparedStatement.setString(2, uuid.toString());

                return parsePlots(preparedStatement.executeQuery());
            }

        }).orElse(new ArrayList<>());
    }

    public void savePlot(Plot plot) {
        sqlConnectionManager.supplyConnection(connection -> {

        });
    }

    private List<Plot> parsePlots(ResultSet resultSet) throws SQLException {
        try (resultSet) {
            List<Plot> plots = new ArrayList<>();

            while (resultSet.next()) {
                plots.add(new Plot(resultSet.getLong("id"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getInt("height"),
                        resultSet.getInt("depth"),
                        resultSet.getDouble("price"),
                        Util.parseUuid(resultSet.getString("uuid")).orElse(null)));
            }

            return plots;
        }
    }
}
