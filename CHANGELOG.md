# Changelog

## [Unreleased]

### Added
- `TrackerHudOverlay` beacon mode: beyond
  `TrackerVisionConfig.getBeaconDistance()`, the bracket reticle is
  replaced by a vertical accent pillar from the target's screen-space
  base (per docs/UI_STYLE_GUIDE.md's Beacon Pillar Marker spec) — a thin
  alpha-gradient line capped with a chevron, since a heavily
  distance-shrunk bracket becomes hard to spot at range. Toggleable via
  `/track config beaconEnabled|beaconDistance` and the config screen.

## [0.5.0-RC1] - Unreleased

### Added
- Project scaffold: Gradle build, NeoForge mod metadata, package structure.
- `/track lock`, `/track clear`, `/track status` client commands.
- `TrackedTargetManager` single-target lock state.
- `TrackedTargetGlowRenderer`: thin additive rim accent on the locked
  target, hooked off `RenderLivingEvent.Post` (works for any `LivingEntity`
  type without per-renderer registration).
- `TrackerHudOverlay`: corner-bracket reticle, off-screen direction caret,
  and distance readout anchored to the locked target's projected screen
  position, with smoothstep distance-based alpha/scale falloff.
- `ScreenProjection`: manual world-to-screen projection helper for HUD
  rendering (reconstructs the camera view/projection matrix since
  `RenderGuiEvent.Post` runs after the 3D pass resets `RenderSystem`'s
  matrices for 2D HUD drawing).
- `docs/RENDERING_RESEARCH.md` and `docs/UI_STYLE_GUIDE.md`: researched
  visual-design decisions behind the above.
- `TrackerVisionConfig`/`TrackerVisionConfigFile`: JSON-backed client
  config (tracking enabled toggle, near/far distance thresholds, bracket
  size, accent color) at `config/trackervision/trackervision-config.json`.
- `/track config enabled|nearDistance|farDistance|show` commands for
  runtime config adjustment, persisted immediately on change.
- `TrackingMode` (`LOCKED`/`NEAREST`) and `/track mode locked|nearest`.
  `NearestTargetScanner` auto-selects the closest living entity within
  the configured far-distance every 10 client ticks (client-side only,
  unlike boss-radar's server-side item-tick scan).
- `TargetState` is now computed live from the tracked entity + distance
  (`TrackedTargetManager.computeState`) instead of always reporting
  `TRACKING`: hostile mobs (`instanceof Enemy`) render red, targets
  beyond the configured far-distance render amber, otherwise cyan.
- Reticle motion: a damped-spring scale pulse on target acquisition
  (settles within ~1.5s) and a continuous subtle breathing scale, both
  driven off `System.nanoTime()` per docs/UI_STYLE_GUIDE.md.
- Through-wall silhouette pass in `TrackedTargetGlowRenderer`: when a
  block occludes the line from the player's eyes to the locked target
  (`Level.clip`), a second `RenderType` (depth-test disabled, flat
  `POSITION_COLOR` format, lower alpha than the normal rim) redraws the
  target's model so the rim accent stays visible through walls instead
  of disappearing entirely — the same `NO_DEPTH_TEST` technique vanilla's
  own spectator-glow outline uses.
- `TrackerVisionConfigScreen`: in-game settings screen (mod list "Config"
  button, via `IConfigScreenFactory`) with a tracking-enabled checkbox and
  near/far distance + bracket size sliders bound directly to
  `TrackerVisionConfig`, saved on close. Built from vanilla GUI widgets
  rather than NeoForge's spec-driven `ConfigurationScreen`, since this
  mod's config is a hand-rolled JSON file, not a `ModConfigSpec`. The
  `/track config` commands remain available alongside it.
