package com.liorcat.vitalsense.client;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.event.BlinkEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class BlinkHud {
    private static final ResourceLocation RENDERED_EYEHUD_IMAGE = new ResourceLocation(VitalSense.MOD_ID, "textures/eyes/eyehud.png");
    private static int xRenderOrigin;
    private static int yRenderOrigin;
    private static int xRenderEnd;
    private static int yRenderEnd;


    public static boolean checkImageBasedOnBlinkTimerAndStatus() {
        float divider = BlinkEvents.getBlinkCountdownTimer() / 4f;
        float phase1 = divider * 4;
        float phase2 = divider * 3;
        float phase3 = divider * 2;
        float phase4 = divider * 1;

        if (BlinkEvents.blinkTimerToBlink.getTimer() > phase2 && BlinkEvents.blinkTimerToBlink.getTimer() <= phase1) {
            xRenderOrigin = 6;
            yRenderOrigin = 2;
            xRenderEnd = 22;
            yRenderEnd = 13;
        } else if (BlinkEvents.blinkTimerToBlink.getTimer() > phase3 && BlinkEvents.blinkTimerToBlink.getTimer() <= phase2) {
            xRenderOrigin = 23;
            yRenderOrigin = 2;
            xRenderEnd = 39;
            yRenderEnd = 13;
        } else if (BlinkEvents.blinkTimerToBlink.getTimer() > phase4 && BlinkEvents.blinkTimerToBlink.getTimer() <= phase3) {
            xRenderOrigin = 40;
            yRenderOrigin = 2;
            xRenderEnd = 56;
            yRenderEnd = 13;
        } else if (BlinkEvents.blinkTimerToBlink.getTimer() > 2 && BlinkEvents.blinkTimerToBlink.getTimer() <= phase4) {
            xRenderOrigin = 57;
            yRenderOrigin = 2;
            xRenderEnd = 73;
            yRenderEnd = 13;
        } else if (BlinkEvents.blinkTimerToBlink.getTimer() >= 0 && BlinkEvents.blinkTimerToBlink.getTimer() <= 2) { //Correction
            xRenderOrigin = 6;
            yRenderOrigin = 2;
            xRenderEnd = 22;
            yRenderEnd = 13;
        }
        return true;
    }

    public static final IGuiOverlay BLINK_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RENDERED_EYEHUD_IMAGE);
        if (checkImageBasedOnBlinkTimerAndStatus()) {
            guiGraphics.blit(RENDERED_EYEHUD_IMAGE, x + 96, height - 18, xRenderOrigin, yRenderOrigin, xRenderEnd - xRenderOrigin, yRenderEnd - yRenderOrigin, 80, 16);
        }
    });

}
