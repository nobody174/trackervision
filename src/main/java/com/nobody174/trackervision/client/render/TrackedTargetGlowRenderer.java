//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nobody174.trackervision.tracking.TargetState;
import com.nobody174.trackervision.tracking.TrackedTargetManager;

/**
 * Draws a thin, crisp single-pass rim around the locked target by
 * re-rendering its own model very slightly enlarged with additive
 * blending, hooked off {@link RenderLivingEvent.Post} so it works for any
 * {@link LivingEntity} type without per-renderer registration. Deliberately
 * a single tight pass (not a dual inner/outer soft-bloom halo) so it reads
 * as a sharp accent rather than a glow — the HUD overlay layer carries
 * TrackerVision's actual visual identity (see docs/UI_STYLE_GUIDE.md); this
 * is a subtle supporting cue, not the focal effect.
 */
public final class TrackedTargetGlowRenderer {

    private static final float RIM_SCALE = 1.035f;
    private static final int RIM_ALPHA = 110;

    private static final Map<ResourceLocation, RenderType> ADDITIVE_RIM_TYPES = new ConcurrentHashMap<>();

    private TrackedTargetGlowRenderer() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(RenderLivingEvent.Post.class, TrackedTargetGlowRenderer::onRenderLivingPost);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void onRenderLivingPost(RenderLivingEvent.Post event) {
        LivingEntity entity = event.getEntity();
        if (entity.isInvisible() || !entity.getUUID().equals(TrackedTargetManager.getLockedTargetId())) {
            return;
        }

        var player = Minecraft.getInstance().player;
        float distance = player != null ? (float) player.position().distanceTo(entity.position()) : 0.0F;
        TargetState state = TrackedTargetManager.computeState(entity, distance);
        int rgb = state.colorRgb();
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        LivingEntityRenderer renderer = event.getRenderer();
        EntityModel model = renderer.getModel();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource bufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();

        VertexConsumer consumer = bufferSource.getBuffer(additiveRim(renderer.getTextureLocation(entity)));
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

        poseStack.pushPose();
        poseStack.scale(RIM_SCALE, RIM_SCALE, RIM_SCALE);
        model.renderToBuffer(poseStack, consumer, packedLight, overlay,
            FastColor.ARGB32.color(RIM_ALPHA, red, green, blue));
        poseStack.popPose();
    }

    private static RenderType additiveRim(ResourceLocation texture) {
        return ADDITIVE_RIM_TYPES.computeIfAbsent(texture, location -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true);
            return RenderType.create("trackervision_additive_rim", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, state);
        });
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
