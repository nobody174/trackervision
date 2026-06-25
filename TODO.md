# TODO

## v0.5 Beta (in progress)
- [x] JSON config system (`TrackerVisionConfig`/`TrackerVisionConfigFile`)
      with `/track config` commands
- [x] Tracking modes (`TrackingMode.LOCKED`/`NEAREST`, `/track mode`,
      `NearestTargetScanner`)
- [x] Hostile/out-of-range target-state color differentiation
      (`TrackedTargetManager.computeState`)
- [x] Breathing reticle + lock-acquired pulse motion
- [ ] Through-wall silhouette rendering
- [ ] In-game config screen (replacing commands per command spec)

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
