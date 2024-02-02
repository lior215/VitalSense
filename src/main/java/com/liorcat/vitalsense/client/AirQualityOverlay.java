package com.liorcat.vitalsense.client;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.registries.VSItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

import static com.liorcat.vitalsense.VitalSense.LOGGER;

public class AirQualityOverlay {
    private static final ResourceLocation AIR_QUALITY = new ResourceLocation(VitalSense.MOD_ID, "textures/misc/air_quality.png");
    private static final ResourceLocation AIR_QUALITY_INDICATOR = new ResourceLocation(VitalSense.MOD_ID, "textures/misc/quality_indicator.png");
    public static int getIndicatorX;

    public static final IGuiOverlay HUD_AIR = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        Player player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AIR_QUALITY);
        if (player.getMainHandItem().is(VSItems.AIR_O_METER.get())) {
            guiGraphics.blit(AIR_QUALITY, x - 94, y - 54, 0, 0, 160, 16, 160, 16);
        }
    });

    public static final IGuiOverlay HUD_QUALITY_INDICATOR = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;

        Player player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AIR_QUALITY_INDICATOR);
        if (player.getMainHandItem().is(VSItems.AIR_O_METER.get())) {
            guiGraphics.blit(AIR_QUALITY_INDICATOR, getIndicatorX, height - 54, 0, 0, 16, 16, 16, 16);
            LOGGER.info("bliting the gui");
        }
    });
}