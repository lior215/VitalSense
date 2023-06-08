package com.lior215.vitalsense.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.lior215.vitalsense.config.ModCommonConfigs;
import com.lior215.vitalsense.utils.LightLevelProvider;
import com.lior215.vitalsense.event.ModBlinkingEvents;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RenderBlinkEffectScreen {

    private static LightLevelProvider lightLevelProvider = new LightLevelProvider();
    static Minecraft mc = Minecraft.getInstance();
    protected static int screenWidth;
    protected static int screenHeight;
    private static int multiplier = 1;
    private static int belowMultiplier = 10; //A è 5 mentre B è 6
    private static int Timer = 0;
    private static int screenDivider = 10;
    private static float UV_U;
    private static float UV_V;
    private final static ResourceLocation DEFAULT_IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "misc/blinkcolormap.png");
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

    public static void checkUvToLightLevelOrWater() {
        float textureSize = 256;
        Player player = Minecraft.getInstance().player;
        assert player != null;
        int actualLightLevel;

        if(!player.isUnderWater()) {
            actualLightLevel = lightLevelProvider.playerPosCheckDynamicLightLevel(player);
            if(actualLightLevel >= 0 && actualLightLevel < 2){
                UV_U = 2/textureSize;
                UV_V = 255/textureSize;
            } else if (actualLightLevel >= 2 && actualLightLevel < 4) {
                UV_U = 168/textureSize;
                UV_V = 240/textureSize;
            } else if (actualLightLevel >= 4 && actualLightLevel < 6) {
                UV_U = 168/textureSize;
                UV_V = 230/textureSize;
            } else if (actualLightLevel >= 6 && actualLightLevel < 8) {
                UV_U = 170/textureSize;
                UV_V = 150/textureSize;
            } else if (actualLightLevel >= 8 && actualLightLevel < 10) {
                UV_U = 176/textureSize;
                UV_V = 145/textureSize;
            } else if (actualLightLevel >= 10 && actualLightLevel < 13) {
                UV_U = 191/textureSize;
                UV_V = 140/textureSize;
            } else if (actualLightLevel >=13) {
                UV_U = (191 / textureSize);
                UV_V = (128 / textureSize);
            }
        } else if (player.isUnderWater()) { //If player in water.
            UV_U = (30 / textureSize);
            UV_V = (2 / textureSize);
        }
    }





    //Rendering Manager
    public static void render(double screenDivider, int multiplier, boolean renderBelow, float transparency) {
        checkUvToLightLevelOrWater();
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
            RenderSystem.setShaderColor(1f, 1f, 1f, transparency); //transparency
            RenderSystem.setShaderTexture(0, RENDERED_IMG_LOCATION);
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
            tesselator.end();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            pose.popPose();


        }
    }

    public void renderGetUv() {
        assert mc.player != null;
        mc.player.sendSystemMessage(Component.literal("U: "+UV_U+" V: "+UV_V));
    }

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)  //TODO: aggiungere un'effetto di accellerazione e aggiungere casistica se <0 allora 0
    public static class RenderBlinkEventClass {


        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {

            if (event.side.isClient() && event.phase == TickEvent.Phase.START && ModBlinkingEvents.getPlayerBlinking() && ModCommonConfigs.ToggleBlinkMechanic.get()) {
                assert mc.player != null;
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

                //If player has pressed F1 the render will not be hidden
                if (ModBlinkingEvents.getPlayerBlinking() && Minecraft.getInstance().options.hideGui && ModCommonConfigs.ToggleBlinkRenderOnF1.get()) {
                    render(screenDivider, multiplier, false, 1.0f);

                    if (Timer > 3 && Timer <=13) {
                        render(screenDivider, belowMultiplier, true, 1.0f);
                    }
                }
            }

        }

        @SubscribeEvent
        public static void playerBlink(RenderGuiOverlayEvent.Post event) {
            Player player = Minecraft.getInstance().player;
            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && ModBlinkingEvents.getPlayerBlinking() && ModCommonConfigs.ToggleBlinkMechanic.get()) {
                render(screenDivider, multiplier, false,1.0f);

                if (Timer > 3 && Timer <=13) {
                    render(screenDivider, belowMultiplier, true,1.0f);
                }
            }
        }
    }
}

