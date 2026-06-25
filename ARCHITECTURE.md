# Architecture

## Systems

### Detection Layer (`tracking/`)
Discovers candidate entities (distance/type filtering). v0.1 scope is
command-driven selection only; automatic scanning arrives with Search Mode
(v1.0).

### Tracking Layer (`tracking/`)
`TrackedTargetManager` stores the currently locked target. v0.1 supports a
single locked entity per client; multi-target tracking is a future
expansion.

### Visualization Layer (`client/render/`)
`TrackedTargetGlowRenderer` draws a thin single-pass additive rim on the
locked target, hooked off `RenderLivingEvent.Post` (works for any
`LivingEntity` type without per-renderer registration — `Entity.setGlowingTag`
was investigated but rejected: it's server-synced and gets overwritten,
so it can't carry client-only tracking state; see `docs/RENDERING_RESEARCH.md`).
This is supporting cast, not the primary visual identity — a custom-shader
outline approach was researched and deliberately deferred to v1.0.

### HUD Layer (`client/hud/`)
This is where TrackerVision's actual visual identity lives: screen-space
corner-bracket reticle, off-screen direction caret, and distance readout,
anchored to the target's projected world position on `RenderGuiEvent.Post`.
Built from scratch against `docs/UI_STYLE_GUIDE.md` (thin geometric
brackets + depth-based color/alpha falloff, modern tactical-overlay
language) — not a port of boss-radar's ring-indicator HUD, which doesn't
match this mod's visual bar.

### Command Layer (`client/command/`)
Client-only commands registered via `Commands`/`RegisterClientCommandsEvent`
(the dispatcher uses the regular `CommandSourceStack`, not a separate
client-only command-source type — confirmed by reading armor-aura's actual
working implementation rather than assuming a `ClientCommandManager` API
that doesn't exist in 1.21.1):
- `/track lock <target>`
- `/track clear`
- `/track status`
- `/track config enabled <true|false>`
- `/track config nearDistance <value>`
- `/track config farDistance <value>`
- `/track config show`

### Configuration Layer (`config/`)
`TrackerVisionConfig` (in-memory, clamped on write) +
`TrackerVisionConfigFile` (JSON persistence at
`config/trackervision/trackervision-config.json`), following the armor-aura
`AuraConfig`/`AuraConfigFile` pattern. Loaded on `FMLClientSetupEvent`,
saved immediately on any `/track config` change. An in-game config screen
(replacing the commands per
`TrackerVision_Production_Design_Package_v2/08_COMMAND_SPEC.md`) is v0.5+
scope; commands are the interim v0.1/v0.5 interface.

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
