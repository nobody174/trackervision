//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

import java.util.UUID;

import net.minecraft.world.entity.Entity;

/**
 * Client-side store for the currently locked target. v0.1 supports a single
 * locked entity; multi-target tracking is planned for a later milestone.
 */
public final class TrackedTargetManager {
    private static UUID lockedTargetId;

    private TrackedTargetManager() {
    }

    public static void lock(Entity entity) {
        lockedTargetId = entity.getUUID();
    }

    public static void clear() {
        lockedTargetId = null;
    }

    public static boolean isLocked() {
        return lockedTargetId != null;
    }

    public static UUID getLockedTargetId() {
        return lockedTargetId;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
