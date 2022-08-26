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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlotManager {

    private static final String PLOTS_TABLE = "plots";
    private static final String OWNED_PLOTS_TABLE = "owned_plots";

    private static final String GET_PLOTS_SQL = "";
    private static final String GET_OWNED_PLOTS_SQL = "";
    private static final String GET_OWNED_PLOTS_PER_UUID_SQL = "";
    private static final String ADD_PLOT_SQL = "";
    private static final String REMOVE_PLOT_SQL = "";

    private final SqlConnectionManager sqlConnectionManager;

    public PlotManager(SqlConnectionManager sqlConnectionManager) {
        this.sqlConnectionManager = sqlConnectionManager;
    }

    public List<Plot> getPlots() {

    }

    public List<OwnedPlot> getOwnedPlots() {

    }

    public List<OwnedPlot> getOwnedPlots(UUID uuid) {

    }

    public void addPlot(Plot plot) {

    }

    public void removePlot(Plot plot) {

    }

    public Optional<Plot> unclaimPlot(OwnedPlot ownedPlot) {

    }

    public Optional<OwnedPlot> claimPlot(Plot plot, UUID uuid) {

    }
}
