package it.lior215.vitalsense.Effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.lior215.vitalsense.Event.ModBlinkingEvents;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.client.gui.GuiComponent.blit;

public class BlinkEffectScreen {

    static Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "misc/blink.png");
    protected static int screenWidth;
    protected static int screenHeight;
    private static int multiplier = 1;
    private static int belowMultiplier = 10; //A è 5 mentre B è 6
    static int Timer = 0;
    static int screenDivider = 10;


    public static void Render(double screenDivider, int multiplier, boolean renderBelow, float transparency) {
        Player player = Minecraft.getInstance().player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
        PoseStack pose = RenderSystem.getModelViewStack();
        assert player != null;
        if (ModBlinkingEvents.getPlayerBlinking()) {
            pose.pushPose();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency); //transparency
            RenderSystem.setShaderTexture(0, IMG_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            if (!renderBelow) {
                bufferbuilder.vertex(0.0D, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(0.0F, 1.0F).endVertex(); // IMPORTANT
                bufferbuilder.vertex(screenWidth, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(1.0F, 1.0F).endVertex(); // IMPORTANT
                bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex(); // IMPORTANT
                bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex(); // IMPORTANT
            } else {
                bufferbuilder.vertex(0.0D, (double) screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex(); // Bottom left corner
                bufferbuilder.vertex(screenWidth, (double) screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex(); // Bottom right corner
                bufferbuilder.vertex(screenWidth, (double) screenHeight / screenDivider * belowMultiplier, -90.0D).uv(1.0F, 0.0F).endVertex(); // Top right corner
                bufferbuilder.vertex(0.0D, (double) screenHeight / screenDivider * belowMultiplier, -90.0D).uv(0.0F, 0.0F).endVertex(); // Top left corner
            }
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            pose.popPose();


        }
    }

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)  //TODO: aggiungere un'effetto di accellerazione e aggiungere casistica se <0 allora 0, aggiungere diverse texture in base al light level
    public static class RenderBlinkEventClass {


        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {

            if (event.side.isClient() && event.phase == TickEvent.Phase.END && ModBlinkingEvents.getPlayerBlinking()) {
                //Timer manager
                if (Timer <= 0) {
                    Timer = 15;
                    multiplier = 1;
                    belowMultiplier = 10;

                    ModBlinkingEvents.setPlayerBlinking(false);
                    ModBlinkingEvents.setCanStartBlinkingTimer(true);
                } else {
                    Timer--;
                }

                //RenderUpper manager
                if (Timer > 8 && Timer <= 15) {
                    multiplier++;
                } else if (Timer > 0 && Timer <= 8) {
                    multiplier--;
                }

                //RenderBelow manager
                if (Timer > 8 && Timer <= 13) {
                    belowMultiplier++;
                } else if (Timer > 3 && Timer <= 8) {
                    belowMultiplier--;
                }

                //If player has pressed F1 the render will not be hidden TODO: create config render during F1
                if (ModBlinkingEvents.getPlayerBlinking() && Minecraft.getInstance().options.hideGui) {
                    Render(screenDivider, multiplier, false, 1.0f);

                    if (Timer > 3 && Timer <=13) {
                        Render(screenDivider, belowMultiplier, true, 1.0f);
                    }
                }
            }

        }

        @SubscribeEvent
        public static void playerBlink(RenderGuiOverlayEvent.Post event) {
            Player player = Minecraft.getInstance().player;
            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && ModBlinkingEvents.getPlayerBlinking()) {
                Render(screenDivider, multiplier, false,1.0f);

                if (Timer > 3 && Timer <=13) {
                    Render(screenDivider, belowMultiplier, true,1.0f);
                }
            }
        }
    }
    //TODO: creare un sistema in cui controllando momenti del giorno, lightlevel e il tempo atmosferico ricavo diversi UV all'interno di una color map
    //TODO: che dovrà essere settabile tramite un metodo

}

