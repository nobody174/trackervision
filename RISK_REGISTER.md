# Risk Register

## Risk: Outline rendering via vanilla post-shader pipeline may conflict with Iris/shader packs
- Impact: Medium (visual breakage for shader-pack users)
- Likelihood: Medium
- Mitigation: Compatibility-test against Iris early (see ROADMAP v0.5
  compatibility research); provide additive-glow fallback render path if
  the outline buffer proves incompatible. Full compatibility target list
  (per `TrackerVision_Production_Design_Package_v2/10_COMPATIBILITY_MATRIX.md`):
  Embeddium, Iris, Sodium derivatives, JourneyMap, Xaero.
- Status: Monitoring

## Risk: Entity scan cost may scale poorly with many tracked/nearby entities
- Impact: Medium (frame drops in crowded areas)
- Likelihood: Low at v0.1 scope (single target only)
- Mitigation: Throttle scans (every N ticks), revisit when Search Mode
  (v1.0) introduces multi-entity scanning. Target performance budget (per
  `..._v2/11_PERFORMANCE_BUDGET.md`): <3 FPS average impact while tracking
  100+ entities, tracking updates per-tick, render updates per-frame.
- Status: Monitoring
