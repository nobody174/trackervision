# Changelog

## [Unreleased]

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
