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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyPlotManager extends SimplePlotManager {

    private static final String DELIMITER = " ,";

    private static final String TABLE = "properties";

    private static final String INSERT_PROPERTY_SQL = "INSERT INTO %s (world, x1, y1, z1, x2, y2, z2, uuid, server, price, flags, members) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PROPERTY_SQL = "UPDATE %s SET world = ?, x1 = ?, y1 = ?, z1 = ?, x2 = ?, y2 = ?, z2 = ?, uuid = ?, server = ?, price = ?, flags = ?, members = ? WHERE id = ?";

    private final SqlConnectionManager sqlConnectionManager;

    public PropertyPlotManager(SqlConnectionManager sqlConnectionManager) {
        super(sqlConnectionManager, TABLE);

        this.sqlConnectionManager = sqlConnectionManager;
    }

    @Override
    public void save(Plot plot) {
        if (!(plot instanceof Property property)) {
            return;
        }

        Location first = property.getFirst();
        Location second = property.getSecond();

        sqlConnectionManager.supplyConnection(connection -> {
            boolean exists = exists(connection, property);

            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(exists ? UPDATE_PROPERTY_SQL : INSERT_PROPERTY_SQL, TABLE))) {
                preparedStatement.setString(1, first.getWorld().getName());
                preparedStatement.setInt(2, first.getBlockX());
                preparedStatement.setInt(3, first.getBlockY());
                preparedStatement.setInt(4, first.getBlockZ());
                preparedStatement.setInt(5, second.getBlockX());
                preparedStatement.setInt(6, second.getBlockY());
                preparedStatement.setInt(7, second.getBlockZ());
                preparedStatement.setString(8, property.getUuid().map(UUID::toString).orElse(null));
                preparedStatement.setString(9, property.getServer());
                preparedStatement.setDouble(10, property.getPrice());
                preparedStatement.setString(11, property.getFlags().stream().map(Object::toString).collect(Collectors.joining(DELIMITER)));
                preparedStatement.setString(12, property.getMembers().stream().map(Object::toString).collect(Collectors.joining(DELIMITER)));

                if (exists) {
                    preparedStatement.setLong(13, property.getId());
                }

                preparedStatement.execute();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        plot.setId(generatedKeys.getLong(1));
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

            plots.add(new Property(resultSet.getLong("id"),
                    new Location(world, resultSet.getInt("x1"), resultSet.getInt("y1"), resultSet.getInt("z1")),
                    new Location(world, resultSet.getInt("x1"), resultSet.getInt("y1"), resultSet.getInt("z1")),
                    Util.parseUuid(resultSet.getString("uuid")).orElse(null),
                    resultSet.getString("server"),
                    resultSet.getDouble("price"),
                    Stream.of(resultSet.getString("flags").split(DELIMITER)).map(flag -> Util.parseEnum(flag, Flag.class).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet()),
                    Stream.of(resultSet.getString("members").split(DELIMITER)).map(member -> Util.parseUuid(member).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet())));
        }

        return plots;
    }
}
