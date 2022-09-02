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

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlotHelper {

    private final Set<Plot> plots = new HashSet<>();

    private final PlotManagerHolder plotManagerHolder;
    private final String server;

    public PlotHelper(PlotManagerHolder plotManagerHolder, String server) {
        this.plotManagerHolder = plotManagerHolder;
        this.server = server;
    }

    public void load() {
        for (PlotType plotType : PlotType.values()) {
            plotManagerHolder.getPlotManager(plotType)
                    .map(plotManager -> plotManager.load(server))
                    .stream()
                    .flatMap(Collection::stream)
                    .forEach(plots::add);
        }
    }

    public Optional<Plot> getPlot(Location location) {
        return plots.stream().filter(plot -> plot.getArea().contains(location)).findFirst();
    }
}
