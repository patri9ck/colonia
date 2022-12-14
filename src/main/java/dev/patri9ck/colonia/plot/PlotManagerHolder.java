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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlotManagerHolder {

    private final Map<PlotType, PlotManager> plotManagers = new HashMap<>();

    public void addPlotManager(PlotType plotType, PlotManager plotManager) {
        plotManagers.put(plotType, plotManager);
    }

    public Optional<PlotManager> getPlotManager(PlotType plotType) {
        return Optional.ofNullable(plotManagers.get(plotType));
    }
}
