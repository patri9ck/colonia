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
package dev.patri9ck.colonia.player.item.mapped;

import dev.patri9ck.colonia.connection.sql.SqlConnectionManager;
import dev.patri9ck.colonia.player.item.ItemManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MappedItemManager implements ItemManager<MappedItem, MappedItemType> {

    private static final String SAVE_SQL = "REPLACE INTO %s (%s) VALUES (%s)";
    private static final String LOAD_SQL = "SELECT 1 FROM %s where uuid = ?";

    private final SqlConnectionManager sqlConnectionManager;

    public MappedItemManager(SqlConnectionManager sqlConnectionManager) {
        this.sqlConnectionManager = sqlConnectionManager;
    }

    @Override
    public boolean save(MappedItem mappedItem) {
        return sqlConnectionManager.connection(connection -> {
            List<Field> fields = getFields(mappedItem);

            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(SAVE_SQL, mappedItem.getMappedItemType().getTable(), getFormattedNames(fields), getFormattedQuestionMarks(fields.size())))) {
                for (int i = 0; i < fields.size(); i++) {
                    preparedStatement.setObject(i + 1, fields.get(i).get(mappedItem));
                }

                return preparedStatement.execute();
            } catch (ReflectiveOperationException exception) {
                exception.printStackTrace();
            }

            return false;
        }).orElse(false);
    }

    @Override
    public Optional<MappedItem> load(UUID uuid, MappedItemType mappedItemType) {
        return sqlConnectionManager.connection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(LOAD_SQL, mappedItemType.getTable()))) {
                preparedStatement.setString(1, uuid.toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    MappedItem mappedItem = mappedItemType.getItemClass().getConstructor().newInstance();

                    if (resultSet.next()) {
                        List<Field> fields = getFields(mappedItem);

                        for (Field field : fields) {
                            field.setAccessible(true);

                            field.set(mappedItem, resultSet.getObject(getName(field)));
                        }

                        return mappedItem;
                    }
                }
            } catch (ReflectiveOperationException exception) {
                exception.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public Class<MappedItemType> getItemTypeClass() {
        return MappedItemType.class;
    }

    private List<Field> getFields(MappedItem mappedItem) {
        List<Field> fields = new ArrayList<>();

        for (Field field : mappedItem.getClass().getDeclaredFields()) {
            int modifiers = field.getModifiers();

            if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                continue;
            }

            fields.add(field);
        }

        return fields;
    }

    private String getFormattedNames(List<Field> fields) {
        return fields.stream().map(this::getName).collect(Collectors.joining(", "));
    }

    private String getFormattedQuestionMarks(int amount) {
        return String.join(", ", Collections.nCopies(amount, "?"));
    }

    private String getName(Field field) {
        return field.getName().toLowerCase();
    }
}
