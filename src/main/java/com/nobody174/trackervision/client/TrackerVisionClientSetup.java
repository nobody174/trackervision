//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.trackervision.client.command.TrackCommand;
import com.nobody174.trackervision.client.hud.TrackerHudOverlay;
import com.nobody174.trackervision.client.render.TrackedTargetGlowRenderer;
import com.nobody174.trackervision.tracking.NearestTargetScanner;

public final class TrackerVisionClientSetup {
    private TrackerVisionClientSetup() {
    }

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(TrackerVisionClientSetup::registerCommands);
        TrackedTargetGlowRenderer.register(modEventBus);
        TrackerHudOverlay.register(modEventBus);
        NearestTargetScanner.register(modEventBus);
    }

    private static void registerCommands(final RegisterClientCommandsEvent event) {
        TrackCommand.register(event.getDispatcher());
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
