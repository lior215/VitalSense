package com.lior215.vitalsense.effects;

import com.lior215.vitalsense.config.ModCommonConfigs;
import com.lior215.vitalsense.event.ModBlinkingTimerEvents;
import com.lior215.vitalsense.utils.LightLevelProvider;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
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

import java.lang.reflect.Field;

public class RenderBlinkEffectScreen {

    private static TimerProvider Timer = new TimerProvider(15);
    private static boolean redEyesDisease = false;
    private static int renderdelay = 100;
    private static boolean skipDelay = false;
    private static LightLevelProvider lightLevelProvider = new LightLevelProvider();
    private static Minecraft mc = Minecraft.getInstance();
    protected static int screenWidth;
    protected static int screenHeight;
    private static float renderTransparency = 1.0f;
    private static int multiplier = 1;
    private static int belowMultiplier = 10;
    private static final int screenDivider = 10;
    private static float UV_U;
    private static float UV_V;
    private final static ResourceLocation DEFAULT_IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "textures/eyes/blink_colormap.png");
    private final static ResourceLocation REDEYES_IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "textures/eyes/redeyevignette.png");
    private static ResourceLocation CUSTOM_IMG_LOCATION = null;
    private static ResourceLocation RENDERED_IMG_LOCATION;

    public static void setSkipDelay(boolean toggle) {
        skipDelay = toggle;
    }

    public static void setRedEyesDisease(boolean toggle) {
        redEyesDisease = toggle;
    }
    public static void setRenderTransparency(float value) {
        renderTransparency = value;
    }

    //Image Manager
    public static ResourceLocation setBlinkImage(String Mod_Id, String resourceLocation) { //for modders to use
        CUSTOM_IMG_LOCATION = new ResourceLocation(Mod_Id, resourceLocation);
        return CUSTOM_IMG_LOCATION;
    }

    public static void checkCustomImageAndDiseases() {
        if (CUSTOM_IMG_LOCATION == null) {
            RENDERED_IMG_LOCATION = DEFAULT_IMG_LOCATION;
        } else {
            RENDERED_IMG_LOCATION = CUSTOM_IMG_LOCATION;
        }
    }

    public static void checkUvToLightLevel() {
        float textureSize = 256;
        Player player = Minecraft.getInstance().player;
        assert player != null;
        int actualLightLevel = lightLevelProvider.playerPosCheckDynamicLightLevel(player);

        if (actualLightLevel >= 0 && actualLightLevel < 2) {
            UV_U = 2 / textureSize;
            UV_V = 255 / textureSize;
        } else if (actualLightLevel >= 2 && actualLightLevel < 4) {
            UV_U = 248 / textureSize;
            UV_V = 210 / textureSize;
        } else if (actualLightLevel >= 4 && actualLightLevel < 6) {
            UV_U = 248 / textureSize;
            UV_V = 185 / textureSize;
        } else if (actualLightLevel >= 6 && actualLightLevel < 8) {
            UV_U = 253 / textureSize;
            UV_V = 131 / textureSize;
        } else if (actualLightLevel >= 8 && actualLightLevel < 10) {
            UV_U = 248 / textureSize;
            UV_V = 131 / textureSize;
        } else if (actualLightLevel >= 10 && actualLightLevel < 13) {
            UV_U = 200 / textureSize;
            UV_V = 131 / textureSize;
        } else if (actualLightLevel >= 13) {
            UV_U = (191 / textureSize);
            UV_V = (128 / textureSize);
        }
    }


    //Rendering Manager
    public static void render(double screenDivider, int multiplier, boolean renderBelow, float transparency) {
        checkUvToLightLevel();
        Player player = Minecraft.getInstance().player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
        PoseStack pose = RenderSystem.getModelViewStack();
        assert player != null;

        if (ModBlinkingTimerEvents.getPlayerBlinking()) {
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
        mc.player.sendSystemMessage(Component.literal("U: " + UV_U + " V: " + UV_V));
    }

    //static int fps = Minecraft.getInstance().getWindow().getRefreshRate(); //check




    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    //TODO: make so the blink render is faster
    public static class RenderBlinkEventClass {
        public static void checkForFps() throws NoSuchFieldException, IllegalAccessException {
            Field fps = Minecraft.class.getDeclaredField("fps");
            fps.setAccessible(true);
            if (fps.getInt(fps) <= 40) {
                skipDelay = true;
            }else if (fps.getInt(fps) >=100) {
                renderdelay = fps.getInt(fps) / 30;
                skipDelay = false;
            } else {
                renderdelay = 2;
                skipDelay = false;
            }
            //assert Minecraft.getInstance().player != null;
            //Minecraft.getInstance().player.sendSystemMessage(Component.literal("fps: "+fps + " delay: "+renderdelay + " skip "+skipDelay));
        }

        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) throws NoSuchFieldException, IllegalAccessException {
            if (event.type == TickEvent.Type.RENDER && event.side.isClient() && event.phase == TickEvent.Phase.END && ModBlinkingTimerEvents.getPlayerBlinking() && ModCommonConfigs.ToggleBlinkMechanic.get()) {
                if (renderdelay == 1 || skipDelay == true) {
                assert mc.player != null;
                // Timer manager
                if (Timer.getTimer() <= 0) {
                    Timer.setTimerToStartValue();
                    multiplier = 0;
                    belowMultiplier = 10;

                    ModBlinkingTimerEvents.setPlayerBlinking(false);
                    ModBlinkingTimerEvents.setCanStartBlinkingTimer(true);
                }

                // RenderUpper manager
                if (Timer.getTimer() > 8 && Timer.getTimer() <= 15) {
                    multiplier++;
                } else if (Timer.getTimer() > 0 && Timer.getTimer() <= 8) {
                    multiplier--;
                }

                // RenderBelow manager
                if (Timer.getTimer() > 8 && Timer.getTimer() <= 13) {
                    belowMultiplier--;
                } else if (Timer.getTimer() > 3 && Timer.getTimer() <= 8) {
                    belowMultiplier++;
                }

                // If the player has pressed F1, the render will not be hidden
                if (ModBlinkingTimerEvents.getPlayerBlinking() && Minecraft.getInstance().options.hideGui && ModCommonConfigs.ToggleBlinkRenderOnF1.get()) {
                    render(screenDivider, multiplier, false, 1.0f);

                    if (Timer.getTimer() > 3 && Timer.getTimer() <= 13) {
                        render(screenDivider, belowMultiplier, true, 1.0f);
                    }
                }
                Timer.decreaseTimer();
                } else if (renderdelay == 0) {
                    checkForFps();
                }
                if (skipDelay) {
                    checkForFps();
                }
                renderdelay--;
            }
        }

        @SubscribeEvent
        public static void playerBlink(RenderGuiOverlayEvent.Post event) {
            if (event.getOverlay().id() == VanillaGuiOverlay.VIGNETTE.id() && ModBlinkingTimerEvents.getPlayerBlinking() && ModCommonConfigs.ToggleBlinkMechanic.get()) {
                render(screenDivider, multiplier, false, 1.0f);

                if (Timer.getTimer() > 3 && Timer.getTimer() <= 13) {
                    render(screenDivider, belowMultiplier, true, 1.0f);
                }
            }
        }
    }
}