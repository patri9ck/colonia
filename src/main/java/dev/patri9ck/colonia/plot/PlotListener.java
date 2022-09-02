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

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class PlotListener implements Listener {

    private final PlotHelper plotHelper;

    public PlotListener(PlotHelper plotHelper) {
        this.plotHelper = plotHelper;
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        Plot from = plotHelper.getPlot(event.getBlock().getLocation()).orElse(null);
        Plot to = plotHelper.getPlot(event.getToBlock().getLocation()).orElse(null);

        event.setCancelled(from != null && (to == null || from.getId() != to.getId()));
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        Block block = event.getBlock();

        if (!(block.getBlockData() instanceof Piston piston)) {
            return;
        }

        Plot from = plotHelper.getPlot(block.getLocation()).orElse(null);
        Plot to = plotHelper.getPlot(block.getRelative(piston.getFacing()).getLocation()).orElse(null);

        event.setCancelled(from != null && (to == null || from.getId() != to.getId()));
    }
}
