package com.liorcat.vitalsense.client;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.air.IAirQuality;
import com.liorcat.vitalsense.registries.VSItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class AirQualityOverlay {
    private static final ResourceLocation AIR_QUALITY = new ResourceLocation(VitalSense.MOD_ID, "textures/misc/air_quality.png");
    private static final ResourceLocation AIR_QUALITY_INDICATOR = new ResourceLocation(VitalSense.MOD_ID, "textures/misc/quality_indicator.png");

    public static final IGuiOverlay HUD_AIR = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;

        Player player = Minecraft.getInstance().player;

        if (player.getMainHandItem().is(VSItems.AIR_O_METER.get())) {
            guiGraphics.blit(AIR_QUALITY, x - 94, height - 54, 0, 0, 160, 16, 160, 16);
        }
    });

    public static final IGuiOverlay HUD_QUALITY_INDICATOR = ((gui, guiGraphics, partialTick, width, height) -> {
        Player player = Minecraft.getInstance().player;
        IAirQuality airQuality = player.getCapability(VSCapabilities.AirQuality.ENTITY);
        VitalSense.LOGGER.debug("Air quality: {}", airQuality.getQuality());

        if (player.getMainHandItem().is(VSItems.AIR_O_METER.get())) {
            guiGraphics.blit(AIR_QUALITY_INDICATOR, width / 2 - 94, height - 54, 0, 0, 16, 16, 16, 16);
        }
    });
}