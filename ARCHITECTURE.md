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
Renders outlines, silhouettes, markers, and beams for tracked targets.
v0.1 uses the vanilla outline-buffer / post-process pipeline (the same
mechanism behind spectator glowing) for a crisp, modern 1px rim outline —
deliberately not the soft additive-bloom look used in armor-aura.

### HUD Layer (`client/hud/`)
Renders distance/arrow indicators on `RenderGuiEvent.Post`. Reuses the
distance-color-coding and ring-indicator concepts proven in boss-radar's
`HudOverlay`, redesigned for a cleaner, modern look (sharper geometry, no
chunky vanilla-font defaults).

### Command Layer (`client/command/`)
Client-only commands registered via `ClientCommandManager` /
`RegisterClientCommandsEvent`:
- `/track lock <target>`
- `/track clear`
- `/track status`

### Configuration Layer (`config/`)
Future GUI and profiles (v0.5+). JSON-backed config following the
armor-aura `AuraConfigFile`/`AuraConfig` pattern.

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
and why.
