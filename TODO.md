# TODO

## v1.0 Release (in progress)
- [x] Beacon mode (`TrackerHudOverlay.drawBeacon`, vertical accent pillar
      replacing the bracket reticle beyond `beaconDistance`)
- [x] Search mode (`SearchModeManager`/`SearchModeScanner`,
      `/track search <true|false>`, persistent multi-target reveal
      independent of the locked target)
- [x] Multiple tracking profiles (`TrackerVisionProfile`, profile-aware
      `TrackerVisionConfig`, `/track profile list|use|create|delete`,
      config screen cycle button, seeded Default/PvP/Exploration profiles)
- [x] Shader pipeline for advanced visual effects (`RimBoostEffect`,
      real custom core `ShaderInstance` post-process bloom-style boost on
      the locked target's rim, scoped lighter than the full jump-flood
      outline per the explicit conditional in docs/RENDERING_RESEARCH.md)

## v0.5 Beta (feature-complete, RC1 tagged)
- [x] JSON config system (`TrackerVisionConfig`/`TrackerVisionConfigFile`)
      with `/track config` commands
- [x] Tracking modes (`TrackingMode.LOCKED`/`NEAREST`, `/track mode`,
      `NearestTargetScanner`)
- [x] Hostile/out-of-range target-state color differentiation
      (`TrackedTargetManager.computeState`)
- [x] Breathing reticle + lock-acquired pulse motion
- [x] Through-wall silhouette rendering (`TrackedTargetGlowRenderer`,
      `Level.clip` occlusion check + depth-test-disabled `POSITION_COLOR`
      pass)
- [x] In-game config screen (`TrackerVisionConfigScreen`, vanilla widgets,
      registered via `IConfigScreenFactory`; `/track config` commands kept
      alongside it, not removed)
- [x] v0.5.0-RC1 tagged — feature-complete, builds clean
- [ ] Manual in-client test pass (see KNOWN_ISSUES.md) before RC1 can be
      considered fully validated
- [ ] RC2 (bug-fix candidate, pending the manual test pass above)

## v0.1 MVP
- [x] Project scaffold (gradle, mod metadata, package structure)
- [x] `/track lock|clear|status` client commands
- [x] `TrackedTargetManager` single-target lock state
- [x] Target glow accent (`TrackedTargetGlowRenderer`, `RenderLivingEvent.Post`,
      thin single-pass additive rim — not the vanilla outline buffer; see
      docs/RENDERING_RESEARCH.md for why)
- [x] HUD overlay (`TrackerHudOverlay`): corner-bracket reticle, off-screen
      caret, distance readout, smoothstep distance falloff
- [x] Build succeeds (`./gradlew build`), JAR deployed to
      `working-mods/` for manual testing
- [ ] **Manual in-client visual verification** — not yet done; automation
      can't drive Minecraft's GLFW window in this environment. Needs a
      human to launch the client with the deployed JAR and confirm the
      reticle/caret/glow actually look right in practice (see
      KNOWN_ISSUES.md).
- [ ] RC1 pass
