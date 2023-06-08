package com.lior215.vitalsense.client;

import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AirQualityOverlay {
    private static final ResourceLocation AIR_QUALITY = new ResourceLocation(vitalsense.MOD_ID, "textures/misc/air_quality.png");


    public static final IGuiOverlay HUD_AIR = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;
        Player player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AIR_QUALITY);
        if (player != null) {
            if (player.getMainHandItem().equals(new ItemStack(Items.AMETHYST_SHARD))) {
                GuiComponent.blit(poseStack, x - 94, y - 54, 0, 0, 160, 16, 160, 16);
                System.out.println("test");
            }
        }
    });
}