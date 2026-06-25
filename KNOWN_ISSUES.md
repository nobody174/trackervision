# Known Issues

## Not yet manually verified in-client
All of v0.1 and v0.5's rendering/HUD/config-screen features build
successfully and the JAR is deployed to `working-mods/`, but none have been
visually confirmed in a running client (reticle placement, off-screen caret
behavior, glow visibility, through-wall silhouette, config screen layout)
— automated tooling in the dev environment can't drive Minecraft's native
GLFW window. Needs a manual test pass (`/track lock <target>`, walking
behind walls, opening the mod's config screen) before RC1 can be
considered fully validated per DEFINITION_OF_DONE. Carried over from v0.1
at the user's explicit choice to skip automated visual verification for
now; still an open item for any "done" release claim.

## FOV accuracy in HUD projection
`ScreenProjection` uses the raw `Minecraft.options.fov()` setting rather
than the actual rendered FOV (`GameRenderer.getFov` is private/inaccessible
to mod code), so projected screen positions will drift slightly during
fog, zoom, or the death-animation FOV pull-in. Acceptable for a HUD
overlay; documented in `ScreenProjection`'s javadoc.
