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

import dev.patri9ck.colonia.plot.PlotHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

public class PropertyPlotListener implements Listener {

    private final PlotHelper plotHelper;

    public PropertyPlotListener(PlotHelper plotHelper) {
        this.plotHelper = plotHelper;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        interact(event.getPlayer(), event.getBlock().getLocation()).ifPresent(interact -> event.setCancelled(!interact));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        interact(event.getPlayer(), event.getBlock().getLocation()).ifPresent(interact -> event.setCancelled(!interact));
    }

    private Optional<Boolean> interact(Player player, Location location) {
        return plotHelper.getPlot(location)
                .filter(plot -> plot instanceof Property)
                .map(plot -> (Property) plot)
                .map(property -> property.isOwner(player) || property.isMember(player));
    }
}
