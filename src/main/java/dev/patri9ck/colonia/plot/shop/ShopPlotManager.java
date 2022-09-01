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

import dev.patri9ck.colonia.connection.sql.SqlConnectionManager;
import dev.patri9ck.colonia.plot.Plot;
import dev.patri9ck.colonia.plot.SimplePlotManager;
import dev.patri9ck.colonia.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopPlotManager extends SimplePlotManager {

    private static final String TABLE = "shops";

    private static final String INSERT_SHOP_SQL = "INSERT INTO %s (world, x1, y1, z1, x2, y2, z2, uuid, server, expiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SHOP_SQL = "UPDATE %s SET world = ?, x1 = ?, y1 = ?, z1 = ?, x2 = ?, y2 = ?, z2 = ?, uuid = ?, server = ?, expiration = ? WHERE uuid = ?";

    private final SqlConnectionManager sqlConnectionManager;

    protected ShopPlotManager(SqlConnectionManager sqlConnectionManager) {
        super(sqlConnectionManager, TABLE);

        this.sqlConnectionManager = sqlConnectionManager;
    }

    @Override
    public void save(Plot plot) {
        if (!(plot instanceof Shop shop)) {
            return;
        }

        Location first = shop.getFirst();
        Location second = shop.getSecond();

        sqlConnectionManager.supplyConnection(connection -> {
            boolean exists = exists(connection, shop);

            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(exists ? UPDATE_SHOP_SQL : INSERT_SHOP_SQL, TABLE))) {
                preparedStatement.setString(1, first.getWorld().getName());
                preparedStatement.setInt(2, first.getBlockX());
                preparedStatement.setInt(3, first.getBlockY());
                preparedStatement.setInt(4, first.getBlockZ());
                preparedStatement.setInt(5, second.getBlockX());
                preparedStatement.setInt(6, second.getBlockY());
                preparedStatement.setInt(7, second.getBlockZ());
                preparedStatement.setString(8, shop.getUuid().map(UUID::toString).orElse(null));
                preparedStatement.setString(9, shop.getServer());
                preparedStatement.setObject(10, shop.getExpiration().map(expiration -> LocalDate.ofInstant(expiration, ZoneId.systemDefault())));

                if (exists) {
                    preparedStatement.setLong(11, shop.getId());
                }

                preparedStatement.execute();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        shop.setId(generatedKeys.getLong(1));
                    }
                }
            }
        });
    }

    @Override
    protected List<Plot> parsePlots(ResultSet resultSet) throws SQLException {
        List<Plot> plots = new ArrayList<>();

        while (resultSet.next()) {
            World world = Bukkit.getWorld(resultSet.getString("world"));

            plots.add(new Shop(resultSet.getLong("id"),
                    new Location(world, resultSet.getInt("x1"), resultSet.getInt("y1"), resultSet.getInt("z1")),
                    new Location(world, resultSet.getInt("x1"), resultSet.getInt("y1"), resultSet.getInt("z1")),
                    Util.parseUuid(resultSet.getString("uuid")).orElse(null),
                    resultSet.getString("server"),
                    resultSet.getObject("expiration", LocalDate.class).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return plots;
    }
}
