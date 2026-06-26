# Known Issues

## Not yet manually verified in-client
All of v0.1, v0.5, and v1.0's rendering/HUD/config-screen/shader features
build successfully, but none have been visually confirmed in a running
client (reticle placement, off-screen caret behavior, glow visibility,
through-wall silhouette, config screen layout, beacon pillar, search-mode
rim, profile switching, the rim-boost post-process shader) — automated
tooling in the dev environment can't drive Minecraft's native GLFW window.
Needs a manual test pass (`/track lock <target>`, walking behind walls,
opening the mod's config screen, toggling search mode, switching
profiles, locking a target with the rim-boost shader enabled) before RC1
can be considered fully validated per DEFINITION_OF_DONE. Carried over
from v0.1 at the user's explicit choice to skip automated visual
verification for now; still an open item for any "done" release claim.

## Rim boost shader not compatibility-tested against Iris/shader packs
`RimBoostEffect`'s custom core shader and manual full-screen-quad
post-process pass have not been tested running alongside Iris or other
shader-pack mods. See RISK_REGISTER.md's Iris-compatibility risk entry —
its scope was written against the (not-yet-built) full outline rewrite,
but the lighter `RimBoostEffect` that shipped instead carries the same
unverified-compatibility caveat.

## FOV accuracy in HUD projection
`ScreenProjection` uses the raw `Minecraft.options.fov()` setting rather
than the actual rendered FOV (`GameRenderer.getFov` is private/inaccessible
to mod code), so projected screen positions will drift slightly during
fog, zoom, or the death-animation FOV pull-in. Acceptable for a HUD
overlay; documented in `ScreenProjection`'s javadoc.
