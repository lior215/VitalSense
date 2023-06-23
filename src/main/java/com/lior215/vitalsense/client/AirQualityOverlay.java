package com.lior215.vitalsense.client;

import com.lior215.vitalsense.items.ModItems;
import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lior215.vitalsense.vitalsense.LOGGER;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class AirQualityOverlay {
    private static final ResourceLocation AIR_QUALITY = new ResourceLocation(vitalsense.MOD_ID, "textures/misc/air_quality.png");
    private static final ResourceLocation AIR_QUALITY_INDICATOR = new ResourceLocation(vitalsense.MOD_ID, "textures/misc/quality_indicator.png");
    public static int indicatorX;

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (Minecraft.getInstance().screen != null) {
            indicatorX = Minecraft.getInstance().screen.width / 2 - 94;
        }
    }

    public static final IGuiOverlay HUD_AIR = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        Player player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AIR_QUALITY);
        LOGGER.info("loaded");
        LOGGER.info("Player != null");
        if (player != null && player.getInventory().contains(new ItemStack(ModItems.AIR_O_METER.get()))) {
            GuiComponent.blit(poseStack, x - 94, y - 54, 0, 0, 160, 16, 160, 16);
            LOGGER.info("bliting the gui");
        }
    });

    public static final IGuiOverlay HUD_QUALITY_INDICATOR = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        Player player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AIR_QUALITY_INDICATOR);
        if (player != null && player.getInventory().contains(new ItemStack(ModItems.AIR_O_METER.get()))) {
            GuiComponent.blit(poseStack, indicatorX, y - 54, 0, 0, 16, 16, 16, 16);
            LOGGER.info("bliting the gui");
        }
    });
}