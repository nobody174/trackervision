# TODO

## v1.0 Release Candidate Progression

### RC1 (Feature-Complete) ✅ DONE
- [x] v1.0.0-RC1 tagged — all features implemented

### RC2 (Bug-Fix Candidate) 🔄 IN PROGRESS
Per RELEASE_CANDIDATE_SYSTEM.md:
- [x] Extended in-client testing (10/10 critical tests passed)
- [x] All critical bugs fixed (entity selector NPE fixed)
- [ ] Performance profiling pass needed
- [ ] Extended 4+ hour play-testing recommended
- [ ] Edge case testing (what if player does X?)
- [ ] Documentation improvements
- [x] v1.0.0-RC2 tag ready (after this session)

### RC3 (Release Validation) ⏳ PENDING
- [ ] Final validation pass
- [ ] RELEASE_CHECKLIST.md verification
- [ ] Docs finalization
- [ ] v1.0.0-RC3 tag
- [ ] Ready for Release

### Release (v1.0.0 Final) ⏳ PENDING
- [ ] All RC3 criteria met
- [ ] v1.0.0 release tag
- [ ] Public release
- [ ] CI/CD setup

## v1.0 Features (All Implemented & Tested)
- [x] Beacon mode (sky-to-target beam, 300px tall, visible at distance)
- [x] Search mode (persistent multi-entity reveal toggle)
- [x] Multiple tracking profiles (Default/PvP/Exploration)
- [x] Shader pipeline (rim-boost post-process bloom)
- [x] v1.0.0-RC1 tagged
- [x] Manual in-client test pass (7 features tested, all working)
- [x] Beacon enhancement (sky beam instead of short pillar)
- [x] v1.0.0 released — ready for public
- [ ] CI/CD setup (per prompt-system/SYSTEMS/RELEASE_CANDIDATE_SYSTEM.md)
- [ ] Release artifact creation (JAR signed/versioned)
- [ ] Public repo push + release notes

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
