package it.lior215.vitalsense.Effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.lior215.vitalsense.Event.ModBlinkingEvents;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

public class RenderBlinkEffectScreen {

    static Minecraft mc = Minecraft.getInstance();
    protected static int screenWidth;
    protected static int screenHeight;
    private static int multiplier = 1;
    private static int belowMultiplier = 10; //A è 5 mentre B è 6
    private static int Timer = 0;
    private static int screenDivider = 10;
    private static float UV_U;
    private static float UV_V;
    private final static ResourceLocation DEFAULT_IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "misc/blink.png");
    private static ResourceLocation CUSTOM_IMG_LOCATION = null;
    private static ResourceLocation RENDERED_IMG_LOCATION;




    //Image Manager
    public static ResourceLocation setBlinkImage(String Mod_Id, String resourceLocation) { //for modders to use
        CUSTOM_IMG_LOCATION = new ResourceLocation(Mod_Id, resourceLocation);
        return CUSTOM_IMG_LOCATION;
    }

    public static void checkCustomImage() {
        if(CUSTOM_IMG_LOCATION == null) {
            RENDERED_IMG_LOCATION = DEFAULT_IMG_LOCATION;
        } else {
            RENDERED_IMG_LOCATION = CUSTOM_IMG_LOCATION;
        }
    }

    public static void checkUvToLightLevel() { //check for sky light too
        float textureSize = 359;
        Player player = Minecraft.getInstance().player;
        Level level = Minecraft.getInstance().level;
        int lightLevel = player.level.getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+1,0));
        //texture size 359 pixels
        if(lightLevel >= 0 && lightLevel < 3){
            UV_U = 30/textureSize;
            UV_V = 150/textureSize;
        } else if (lightLevel >= 3 && lightLevel < 6) {
            UV_U = 30/textureSize;
            UV_V = 90/textureSize;
        } else if (lightLevel >= 6 && lightLevel < 9) {
            UV_U = 90/textureSize;
            UV_V = 30/textureSize;
        } else if (lightLevel >= 9 && lightLevel < 11) {
            UV_U = 260/textureSize;
            UV_V = 170/textureSize;
        } else if (lightLevel >= 11 && lightLevel < 13) {
            UV_U = 140/textureSize;
            UV_V = 170/textureSize;
        } else if (lightLevel >=13) {
            UV_U = (250 / textureSize);
            UV_V = (330 / textureSize);
        }
    }





    //Rendering Manager
    public static void Render(double screenDivider, int multiplier, boolean renderBelow, float transparency) {
        setBlinkImage(vitalsense.MOD_ID, "misc/blinkcolormap.png");
        checkUvToLightLevel();
        checkCustomImage();
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
            RenderSystem.setShaderColor(1f, 1F, 1F, transparency); //transparency
            RenderSystem.setShaderTexture(0, CUSTOM_IMG_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            if (!renderBelow) {
                bufferbuilder.vertex(0.0D, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(UV_U, UV_V).endVertex(); // IMPORTANT
                bufferbuilder.vertex(screenWidth, (double) screenHeight / screenDivider * multiplier, -90.0D).uv(UV_U, UV_V).endVertex(); // IMPORTANT
                bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(UV_U, UV_V).endVertex(); // IMPORTANT
                bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(UV_U, UV_V).endVertex(); // IMPORTANT
            } else {
                bufferbuilder.vertex(0.0D, (double) screenHeight, -90.0D).uv(UV_U, UV_V).endVertex(); // Bottom left corner
                bufferbuilder.vertex(screenWidth, (double) screenHeight, -90.0D).uv(UV_U, UV_V).endVertex(); // Bottom right corner
                bufferbuilder.vertex(screenWidth, (double) screenHeight / screenDivider * belowMultiplier, -90.0D).uv(UV_U, UV_V).endVertex(); // Top right corner
                bufferbuilder.vertex(0.0D, (double) screenHeight / screenDivider * belowMultiplier, -90.0D).uv(UV_U, UV_V).endVertex(); // Top left corner
            }
            player.sendSystemMessage(Component.literal("U: "+UV_U+" V: "+UV_V));
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            pose.popPose();


        }
    }

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)  //TODO: aggiungere un'effetto di accellerazione e aggiungere casistica se <0 allora 0
    public static class RenderBlinkEventClass {


        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {

            if (event.side.isClient() && event.phase == TickEvent.Phase.END && ModBlinkingEvents.getPlayerBlinking()) {
                //Timer manager
                if (Timer <= 0) {
                    Timer = 15;
                    multiplier = 0;
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
                    belowMultiplier--;
                } else if (Timer > 3 && Timer <= 8) {
                    belowMultiplier++;
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

}

