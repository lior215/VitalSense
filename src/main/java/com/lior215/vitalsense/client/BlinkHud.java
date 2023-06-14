package com.lior215.vitalsense.client;

import com.lior215.vitalsense.event.ModBlinkingTimerEvents;
import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BlinkHud {

    private static final ResourceLocation RENDERED_EYEHUD_IMAGE = new ResourceLocation(vitalsense.MOD_ID,"textures/misc/eyehud.png");

    private static int xRenderOrigin;
    private static int yRenderOrigin;
    private static int xRenderEnd;
    private static int yRenderEnd;
    private static boolean shouldRenderFaster = false;


    public static boolean checkImageBasedOnBlinkTimerAndStatus() {
        int phase1 = ModBlinkingTimerEvents.getBlinkCountdownTimer();
        int phase2 = ModBlinkingTimerEvents.getBlinkCountdownTimer()/2;
        int phase3 = ModBlinkingTimerEvents.getBlinkCountdownTimer()/3;
        int phase4 = ModBlinkingTimerEvents.getBlinkCountdownTimer()/4;

            if (ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() > phase2 && ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() <= phase1) {
                xRenderOrigin = 6;
                yRenderOrigin = 2;
                xRenderEnd = 22;
                yRenderEnd = 13;
            } else if (ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() > phase3 && ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() <= phase2) {
                xRenderOrigin = 23;
                yRenderOrigin = 2;
                xRenderEnd = 39;
                yRenderEnd = 13;
            } else if (ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() > phase4 && ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() <= phase3) {
                xRenderOrigin = 40;
                yRenderOrigin = 2;
                xRenderEnd = 56;
                yRenderEnd = 13;
            } else if (ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() > 2 && ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() <= phase4) {
                xRenderOrigin = 57;
                yRenderOrigin = 2;
                xRenderEnd = 73;
                yRenderEnd = 13;
            } else if (ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() >= 0 && ModBlinkingTimerEvents.blinkTimerToBlink.getTimer() <= 2) {
                xRenderOrigin = 6;
                yRenderOrigin = 2;
                xRenderEnd = 22;
                yRenderEnd = 13;
            }
        return true;
        }



    public static final IGuiOverlay BLINK_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RENDERED_EYEHUD_IMAGE);
        if(checkImageBasedOnBlinkTimerAndStatus()) {
            GuiComponent.blit(poseStack, x + 96, y - 18, xRenderOrigin, yRenderOrigin, xRenderEnd - xRenderOrigin, yRenderEnd - yRenderOrigin, 80, 16);
        }
    });



}
