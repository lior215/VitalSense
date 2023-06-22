package com.lior215.vitalsense.effects;

import com.lior215.vitalsense.mobeffects.RedEyes;
import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RedEyesEffect {

    private static int screenWidth;
    private static int screenHeight;
    private static int textureWidth = 680;
    private static int textureHeight = 378;
    private static boolean renderEyeDisease = false;
    private static float transparency = 0.0f;

    private static Minecraft mc = Minecraft.getInstance();

    public static boolean getRenderDisease() {
        return renderEyeDisease;
    }

    public static void setRenderEyeDisease(boolean toggle) {
        renderEyeDisease = toggle;
    }

    public static void setTransparency(float value) {
        transparency = value;
    }


    private static final ResourceLocation REDEYESDISEASELOCATION = new ResourceLocation(vitalsense.MOD_ID, "textures/eyes/redeyevignette.png");

    public static final void RED_EYES_DISEASE() { //todo: impostare il transparency la prima volta che viene chiamato a 0
        PoseStack poseStack = RenderSystem.getModelViewStack();
        screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        float UV_U = (float) screenWidth / (float) textureWidth;
        float UV_V = (float) screenHeight / (float) textureHeight;
        poseStack.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, transparency); //transparency
        RenderSystem.setShaderTexture(0, REDEYESDISEASELOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        //poseStack.scale(UV_U, UV_V, 1.0F);
        if(renderEyeDisease) { //todo: controllare
            vitalsense.LOGGER.info("sono dentro");
            bufferbuilder.vertex(0.0D, (double) screenHeight, -90.0D).uv(0, 1).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, (double) screenHeight, -90.0D).uv(1, 1).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1, 0).endVertex(); // IMPORTANT
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0, 0).endVertex(); // IMPORTANT
        } else {
            RedEyes.modEffectRedEyesReset();
        }
        tesselator.end();
        RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }


    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RenderBlinkEventClass {
        @SubscribeEvent
        public static void renderDisease(RenderGuiOverlayEvent.Post event) {
            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && RedEyesEffect.getRenderDisease()) {
                RedEyesEffect.RED_EYES_DISEASE();
            }
        }
    }
}
