package it.lior215.vitalsense.Effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.lior215.vitalsense.Event.ModBlinkingEvents;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.client.gui.GuiComponent.blit;

public class BlinkEffectScreen {

    static Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "misc/blink.png");
    protected static int screenWidth;
    protected static int screenHeight;


    public static void Render(double screenDivider) {
        Player player = Minecraft.getInstance().player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
        assert player != null;
        if (ModBlinkingEvents.getPlayerBlinking()) {

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.setShaderTexture(0, IMG_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, (double) screenHeight * (1.0 / screenDivider), -90.0D).uv(0.0F, 1.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, (double) screenHeight * (1.0 / screenDivider), -90.0D).uv(1.0F, 1.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex(); // IMPORTANT
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);

        }
    }

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RenderBlinkEventClass {

    static int Timer = 0;
    static int screenDivider = 10;

    
    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {

        if(event.side.isClient() && event.phase == TickEvent.Phase.END && ModBlinkingEvents.getPlayerBlinking()) {

            if (Timer > 11 && Timer <= 22) {
                Render(screenDivider);
                screenDivider--;
                Timer--;

            } else if (Timer > 0 && Timer <= 11 ) {

                screenDivider++;
                Render(screenDivider);
                Timer--;
            } else if (Timer <= 0) {
                screenDivider = 11;
                Timer = 23;
                System.out.println("Timer: " + Timer);
                ModBlinkingEvents.setPlayerBlinking(false);
                ModBlinkingEvents.setCanStartBlinkingTimer(true);
            } else {
                Timer -= 1;
                System.out.println("Timer: " + Timer);
            }

        }
    }
}

}

