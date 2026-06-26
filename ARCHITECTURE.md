# Architecture

## Systems

### Detection Layer (`tracking/`)
`NearestTargetScanner` discovers candidate entities for
`TrackingMode.NEAREST`: every 10 client ticks, scans `LivingEntity`s within
the configured far-distance radius (`level.getEntitiesOfClass` + an AABB,
the same pattern boss-radar uses, but client-tick-driven rather than a
server-side item tick, since this state never needs to leave the client).

`SearchModeScanner` runs the same scan pattern but collects every valid
entity in range instead of just the closest one, feeding
`SearchModeManager`'s revealed-target set.

### Tracking Layer (`tracking/`)
`TrackedTargetManager` stores the currently tracked target and its
`TrackingMode` (`LOCKED` — manual via `/track lock`, or `NEAREST` — auto-
selected by the Detection Layer above). `TargetState` (tracking/hostile/
out-of-range) is computed per-frame from the live entity + distance via
`computeState(Entity, float)`, not cached, since it depends on context
only the HUD/glow layers have at render time. v0.5 supports a single
tracked entity at a time; `FILTERED` mode remains future scope.

`SearchModeManager` is a separate, independent on/off toggle
(`/track search <true|false>`) holding a set of revealed entity UUIDs —
the multi-target "area scan/reveal" case the v2 planning pack's Tracking
Engine doc originally filed under a future `GROUP` mode. It's deliberately
decoupled from `TrackedTargetManager` rather than folded into
`TrackingMode`, since Search Mode reveals many entities at once and
doesn't interact with which single entity (if any) is locked — both can
be active simultaneously without conflict.

### Visualization Layer (`client/render/`)
`TrackedTargetGlowRenderer` draws a thin single-pass additive rim on the
locked target, hooked off `RenderLivingEvent.Post` (works for any
`LivingEntity` type without per-renderer registration — `Entity.setGlowingTag`
was investigated but rejected: it's server-synced and gets overwritten,
so it can't carry client-only tracking state; see `docs/RENDERING_RESEARCH.md`).
This is supporting cast, not the primary visual identity — a custom-shader
outline approach was researched and deliberately deferred to v1.0.

The same renderer also draws a lighter version of the rim (neutral color,
lower alpha, no through-wall silhouette pass) on every entity in
`SearchModeManager`'s revealed set when Search Mode is on, so a broad
area scan reads as visually distinct from — and doesn't compete with —
an actual locked target.

When a block occludes the line from the player's eyes to the target
(checked via `Level.clip`), a second pass redraws the model with
depth-testing disabled (`RenderStateShard.NO_DEPTH_TEST`) using a flat
`DefaultVertexFormat.POSITION_COLOR` RenderType instead of the textured
rim format, so the rim accent stays visible through walls at a lower
alpha. This is the same technique vanilla's own spectator-glow outline
uses; the flat format avoids the full textured model bleeding through
geometry, which would look messier than a clean silhouette.

### HUD Layer (`client/hud/`)
This is where TrackerVision's actual visual identity lives: screen-space
corner-bracket reticle, off-screen direction caret, and distance readout,
anchored to the target's projected world position on `RenderGuiEvent.Post`.
Built from scratch against `docs/UI_STYLE_GUIDE.md` (thin geometric
brackets + depth-based color/alpha falloff, modern tactical-overlay
language) — not a port of boss-radar's ring-indicator HUD, which doesn't
match this mod's visual bar.

Beyond `TrackerVisionConfig.getBeaconDistance()`, the bracket reticle is
replaced by a beacon pillar (`drawBeacon`): a vertical accent line from
the target's projected screen-space base, alpha-gradient fading
top-to-bottom, capped with a chevron — per the style guide's Beacon
Pillar Marker spec. The bracket alone becomes hard to spot once
distance-based scale falloff has shrunk it down, which is exactly the
range where a beacon's "locate extremely distant targets" purpose
applies.

### Command Layer (`client/command/`)
Client-only commands registered via `Commands`/`RegisterClientCommandsEvent`
(the dispatcher uses the regular `CommandSourceStack`, not a separate
client-only command-source type — confirmed by reading armor-aura's actual
working implementation rather than assuming a `ClientCommandManager` API
that doesn't exist in 1.21.1):
- `/track lock <target>`
- `/track clear`
- `/track status`
- `/track mode locked|nearest`
- `/track config enabled <true|false>`
- `/track config nearDistance <value>`
- `/track config farDistance <value>`
- `/track config show`

### Configuration Layer (`config/`)
`TrackerVisionConfig` (in-memory, clamped on write) +
`TrackerVisionConfigFile` (JSON persistence at
`config/trackervision/trackervision-config.json`), following the armor-aura
`AuraConfig`/`AuraConfigFile` pattern. Loaded on `FMLClientSetupEvent`,
saved immediately on any `/track config` change.

`TrackerVisionConfigScreen` (`client/gui/`) is an in-game settings screen
reachable from the mod list's "Config" button, registered via
`ModContainer.registerExtensionPoint(IConfigScreenFactory.class, ...)` in
`TrackerVisionMod`'s constructor. It's built from vanilla `Screen`/
`GridLayout`/`Checkbox`/`AbstractSliderButton` widgets rather than
NeoForge's spec-driven `ConfigurationScreen`, since this mod's config is a
hand-rolled JSON file (`TrackerVisionConfig`/`TrackerVisionConfigFile`),
not a `ModConfigSpec` — the built-in screen only binds to the latter. The
`/track config` commands remain available as an alternate interface, not
replaced by the GUI.

## Package Layout

```
com.nobody174.trackervision/
├── TrackerVisionMod.java        (main @Mod class)
├── tracking/                    (detection + lock state)
├── client/
│   ├── TrackerVisionClientSetup.java
│   ├── command/                 (client commands)
│   ├── render/                  (outline/silhouette renderers)
│   └── hud/                     (HUD overlay)
├── network/                     (CustomPacketPayload records + handlers)
└── config/                      (JSON config load/save)
```

## Reuse Notes

See `REUSED_FROM.md` for what was adapted from armor-aura and boss-radar,
and why. See `docs/RENDERING_RESEARCH.md` and `docs/UI_STYLE_GUIDE.md` for
the researched visual-design decisions behind the Visualization and HUD
layers.
