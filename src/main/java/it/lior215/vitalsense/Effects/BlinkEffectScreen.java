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
    static int Timer = 0;
    static int screenDivider = 11;


    public static void Render(double screenDivider, int multiplier) {
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
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.setShaderTexture(0, IMG_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(0.0F, 1.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(1.0F, 1.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex(); // IMPORTANT
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex(); // IMPORTANT
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            pose.popPose();


        }
    }

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)  //TODO: aggiungere un'effetto di accellerazione e far si che circa 1/5 delle palpebre su schermo si chiuda da sotto e aggiungere casistica se <0 allora 0
    public static class RenderBlinkEventClass {


        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {

            if (event.side.isClient() && event.phase == TickEvent.Phase.END && ModBlinkingEvents.getPlayerBlinking()) {

                if (Timer > 11 && Timer <= 22) {
                    multiplier++;
                    Timer--;

                } else if (Timer > 0 && Timer <= 11) {

                    multiplier--;
                    Timer--;
                } else if (Timer <= 0) {

                    screenDivider = 11;
                    multiplier = 1;
                    Timer = 23;
                    ModBlinkingEvents.setPlayerBlinking(false);
                    ModBlinkingEvents.setCanStartBlinkingTimer(true);
                } else {
                    Timer -= 1;
                }


                //If player has pressed F1 the render will not be hided!
                if (ModBlinkingEvents.getPlayerBlinking() && Minecraft.getInstance().options.hideGui) {
                    Render(screenDivider, multiplier);
                }

            }


        }

        @SubscribeEvent
        public static void playerBlink(RenderGuiOverlayEvent.Post event) {
            Player player = Minecraft.getInstance().player;

            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && ModBlinkingEvents.getPlayerBlinking()) {
                Render(screenDivider, multiplier);
            }

        }
    }
}

