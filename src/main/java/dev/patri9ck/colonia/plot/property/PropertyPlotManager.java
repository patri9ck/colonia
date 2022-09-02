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
import dev.patri9ck.colonia.plot.Area;
import dev.patri9ck.colonia.plot.Plot;
import dev.patri9ck.colonia.plot.SimplePlotManager;
import dev.patri9ck.colonia.util.Util;
import org.bukkit.Bukkit;
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

    private static final String DELIMITER = ", ";

    private static final String TABLE = "properties";

    private static final String INSERT_PROPERTY_SQL = "INSERT INTO %s (world, first_x, first_y, first_z, second_x, second_y, second_z, uuid, server, price, flags, members) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PROPERTY_SQL = "UPDATE %s SET world = ?, first_x = ?, first_y = ?, first_z = ?, second_x = ?, second_y = ?, second_z = ?, uuid = ?, server = ?, price = ?, flags = ?, members = ? WHERE id = ?";

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

        Area area = property.getArea();

        sqlConnectionManager.supplyConnection(connection -> {
            boolean exists = exists(connection, property);

            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(exists ? UPDATE_PROPERTY_SQL : INSERT_PROPERTY_SQL, TABLE))) {
                preparedStatement.setString(1, area.getWorld().getName());
                preparedStatement.setInt(2, area.getFirstX());
                preparedStatement.setInt(3, area.getFirstY());
                preparedStatement.setInt(4, area.getFirstZ());
                preparedStatement.setInt(5, area.getSecondX());
                preparedStatement.setInt(6, area.getSecondY());
                preparedStatement.setInt(7, area.getSecondZ());
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

            if (world == null) {
                continue;
            }

            plots.add(new Property(resultSet.getLong("id"),
                    new Area(world, resultSet.getInt("first_x"), resultSet.getInt("first_y"), resultSet.getInt("first_z"), resultSet.getInt("second_x"), resultSet.getInt("second_y"), resultSet.getInt("second_z")),
                    Util.parseUuid(resultSet.getString("uuid")).orElse(null),
                    resultSet.getString("server"),
                    resultSet.getDouble("price"),
                    Stream.of(resultSet.getString("flags").split(DELIMITER)).map(flag -> Util.parseEnum(flag, Flag.class).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet()),
                    Stream.of(resultSet.getString("members").split(DELIMITER)).map(member -> Util.parseUuid(member).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet())));
        }

        return plots;
    }
}
