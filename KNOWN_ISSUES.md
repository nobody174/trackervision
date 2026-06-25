# Known Issues

## Not yet manually verified in-client
v0.1's glow renderer and HUD overlay build successfully and the JAR is
deployed to `working-mods/`, but have not been visually confirmed in a
running client (reticle placement, off-screen caret behavior, glow
visibility) — automated tooling in the dev environment can't drive
Minecraft's native GLFW window. Needs a manual `/track lock <target>` test
pass before this is considered done per DEFINITION_OF_DONE.

## FOV accuracy in HUD projection
`ScreenProjection` uses the raw `Minecraft.options.fov()` setting rather
than the actual rendered FOV (`GameRenderer.getFov` is private/inaccessible
to mod code), so projected screen positions will drift slightly during
fog, zoom, or the death-animation FOV pull-in. Acceptable for a HUD
overlay; documented in `ScreenProjection`'s javadoc.
