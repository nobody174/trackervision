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
Applies the vanilla glowing/outline-buffer mechanism (`Entity.setGlowingTag`
+ `OutlineBufferSource`, the same system behind spectator-mode glow) to the
locked target for a crisp 1px silhouette, tinted by current target state.
This is supporting cast, not the primary visual identity — see
`docs/RENDERING_RESEARCH.md` for why a custom-shader approach was
researched and deliberately deferred to v1.0.

### HUD Layer (`client/hud/`)
This is where TrackerVision's actual visual identity lives: screen-space
corner-bracket reticle, off-screen direction caret, and distance readout,
anchored to the target's projected world position on `RenderGuiEvent.Post`.
Built from scratch against `docs/UI_STYLE_GUIDE.md` (thin geometric
brackets + depth-based color/alpha falloff, modern tactical-overlay
language) — not a port of boss-radar's ring-indicator HUD, which doesn't
match this mod's visual bar.

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
and why. See `docs/RENDERING_RESEARCH.md` and `docs/UI_STYLE_GUIDE.md` for
the researched visual-design decisions behind the Visualization and HUD
layers.
