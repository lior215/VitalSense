package com.liorcat.vitalsense.registries.effects;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.config.ModCommonConfigs;
import com.liorcat.vitalsense.event.helper.BlinkEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;
import net.neoforged.neoforge.event.TickEvent;

public class PhotophobiaEffect {

    private static int screenWidth;
    private static int screenHeight;
    private static boolean renderEyeDisease = false;
    private static float transparency = 0.0f;
    private static int frame = 1;
    private static final ResourceLocation PFRAME_1 = new ResourceLocation(VitalSense.MOD_ID, "textures/eyes/diseases/all-in/light_1_frame.png");
    private static final ResourceLocation PFRAME_2 = new ResourceLocation(VitalSense.MOD_ID, "textures/eyes/diseases/all-in/light_2_frame.png");
    private static final ResourceLocation PFRAME_3 = new ResourceLocation(VitalSense.MOD_ID, "textures/eyes/diseases/all-in/light_3_frame.png");


    public static boolean getRenderDisease() {
        return renderEyeDisease;
    }

    public static void setRenderEyeDisease(boolean toggle) {
        renderEyeDisease = toggle;
    }

    public static void setTransparency(float value) {
        transparency = value;
    }

    public static void setFrame(int frameset) {
        frame = Math.min(frameset, 4);
    }

    public static ResourceLocation checkForFrame() {
        if (frame == 1) {
            return PFRAME_1;
        } else if (frame == 2) {
            return PFRAME_2;
        } else if (frame == 3) {
            return PFRAME_3;
        } else if (frame == 4) {
            return PFRAME_2;
        } else {
            return PFRAME_1;
        }
    }


    public static void getPhotophobiaDisease() {
        PoseStack poseStack = RenderSystem.getModelViewStack();
        screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        poseStack.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, transparency); //transparency
        RenderSystem.setShaderTexture(0, checkForFrame());
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        if (renderEyeDisease) {
            bufferbuilder.vertex(0.0D, (double) screenHeight, -90.0D).uv(0, 1).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, (double) screenHeight, -90.0D).uv(1, 1).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1, 0).endVertex(); // IMPORTANT
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0, 0).endVertex(); // IMPORTANT
        } else {
            // TunnelVision.modEffectTunnelVisionReset();
        }
        tesselator.end();
        RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }


    @Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RenderBlinkEventClass {
        @SubscribeEvent
        public static void renderDiseaseGlaucoma(RenderGuiOverlayEvent.Post event) {
            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && PhotophobiaEffect.getRenderDisease() && !Minecraft.getInstance().options.hideGui) {
                getPhotophobiaDisease();
            }
        }
    }


    @SubscribeEvent
    public static void onRenderTickGlaucoma(TickEvent.RenderTickEvent event) {
        if (event.type == TickEvent.Type.RENDER && event.side.isClient() && event.phase == TickEvent.Phase.END && BlinkEvents.getPlayerBlinking() && ModCommonConfigs.ToggleBlinkMechanic.get()) {

            // If the player has pressed F1, the render will not be hidden
            if (Minecraft.getInstance().options.hideGui && ModCommonConfigs.ToggleBlinkRenderOnF1.get()
                    && ModCommonConfigs.ToggleDiseaseScreenOnF1.get() && PhotophobiaEffect.getRenderDisease()) {
                getPhotophobiaDisease();
            }
        }
    }
}
