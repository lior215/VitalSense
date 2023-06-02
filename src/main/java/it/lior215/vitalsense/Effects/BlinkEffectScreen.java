package it.lior215.vitalsense.Effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.lior215.vitalsense.Event.ModBlinkingEvents;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BlinkEffectScreen {
    static Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation IMG_LOCATION = new ResourceLocation(vitalsense.MOD_ID, "misc/blink.png");
    protected static int screenWidth;
    protected static int screenHeight;

    public static void Render() {
        Player player = Minecraft.getInstance().player;
        //Options settings = Minecraft.getInstance().options;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
        assert player != null;
        if (ModBlinkingEvents.getPlayerBlinking()) {

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.setShaderTexture(0, IMG_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(screenWidth, (double) screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);

        }
    }

}


