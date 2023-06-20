package com.lior215.vitalsense.screen;

import com.lior215.vitalsense.client.ClientEyeHealth;
import com.lior215.vitalsense.vitalsense;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import org.jetbrains.annotations.NotNull;

public class EyeGui extends Screen {

    int x = Minecraft.getInstance().getWindow().getGuiScaledWidth();
    int y = Minecraft.getInstance().getWindow().getGuiScaledHeight();
    private boolean ButtonsRendered = false;

    ImageButton closebutton = new ImageButton((int) ((x + 9 ) / 2.25f), (int) ((y + 19) / 2.25), 66, 15, 151, 199, 16, TEXTURE_RENDER_GUI, 256, 256, this::close);



    private static final ResourceLocation TEXTURE_RENDER_GUI = new ResourceLocation(vitalsense.MOD_ID, "textures/misc/eyestatsgui.png");
    public EyeGui() {
        super(Component.literal("eye gui"));
    }


    public void renderBg(@NotNull PoseStack poseStack) {
        //Base gui
        int xRenderOrigin = 0;
        int yRenderOrigin = 0;
        int xRenderEnd = 174;
        int yRenderEnd = 135;
        int Xscaler = 173 / 100 * 25;
        int Yscaler = 150 / 100 * 25;


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_RENDER_GUI);

        poseStack.pushPose();
        poseStack.scale(1.25f,1.25f,1);
        blit(poseStack, (int) ((x - 173) / 2.75f), (int) ((y - 150) / 2.75f), xRenderOrigin, yRenderOrigin, xRenderEnd - xRenderOrigin, yRenderEnd - yRenderOrigin, 256, 256);
        //default mc color text: 4210752

        //extras
        eyeStatusToImage(poseStack);
        poseStack.popPose();



        //title
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 1.0f);
        font.draw(poseStack, Component.translatable("vitalsense.gui.eyestatsgui").setStyle(Style.EMPTY.withColor(100)), (float) (x - 1) / 1.85f, (float) (y - 145) / 1.85f, 1);
        poseStack.popPose();

    } //TODO: Check the position of the title of the gui y-45 x244


    @Override
    public void renderBackground(PoseStack pPoseStack) {
        super.renderBackground(pPoseStack);
    }

    private void addButtonsToGui(PoseStack poseStack) { //done
        if(!ButtonsRendered) {
            ButtonsRendered = true;
            addRenderableWidget(closebutton);

        }
    }

    private void addTextToGui(PoseStack poseStack, int color) {
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 1.0f);
        //todo: add text to gui stats

        //Button text
        font.draw(poseStack, Component.translatable("vitalsense.gui.button.close").setStyle(Style.EMPTY.withColor(color)), (float) (x + 53) / 1.90f, (float) (y + 26) / 1.90f, 1 );
        poseStack.popPose();
    }


    public void renderEyeInScreen(PoseStack poseStack, int eyeXRenderOrigin, int eyeYRenderOrigin, int eyeXRenderEnd, int eyeYRenderEnd) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_RENDER_GUI);


        blit(poseStack, (int) ((x + 5) / 2.75f), (int) ((y - 79) / 2.75f), eyeXRenderOrigin, eyeYRenderOrigin, eyeXRenderEnd - eyeXRenderOrigin, eyeYRenderEnd - eyeYRenderOrigin, 256, 256);
    }



    public void eyeStatusToImage(PoseStack poseStack) {
        int xRenderOrigin = 0;
        int yRenderOrigin = 0;
        int xRenderEnd = 0;
        int yRenderEnd = 0;
        if(ClientEyeHealth.getEyeHealth() > 80f && ClientEyeHealth.getEyeHealth() <= 100f) {
            xRenderOrigin = 2;
            yRenderOrigin = 200;
            xRenderEnd = 47;
            yRenderEnd = 224;
        } else if (ClientEyeHealth.getEyeHealth() >= 60f && ClientEyeHealth.getEyeHealth() <= 80f) {
            xRenderOrigin = 50;
            yRenderOrigin = 200;
            xRenderEnd = 95;
            yRenderEnd = 224;
        } else if (ClientEyeHealth.getEyeHealth() >= 40f && ClientEyeHealth.getEyeHealth() < 60f) {
            xRenderOrigin = 98;
            yRenderOrigin = 200;
            xRenderEnd = 143;
            yRenderEnd = 224;
        } else if (ClientEyeHealth.getEyeHealth() >= 20f && ClientEyeHealth.getEyeHealth() < 40f) {
            xRenderOrigin = 2;
            yRenderOrigin = 227;
            xRenderEnd = 47;
            yRenderEnd = 252;
        } else if (ClientEyeHealth.getEyeHealth() > 0f && ClientEyeHealth.getEyeHealth() < 20f) {
            xRenderOrigin = 49;
            yRenderOrigin = 227;
            xRenderEnd = 95;
            yRenderEnd = 252;
        } else if (ClientEyeHealth.getEyeHealth() <= 0f){
            xRenderOrigin = 98;
            yRenderOrigin = 227;
            xRenderEnd = 143;
            yRenderEnd = 252;
        }
        renderEyeInScreen(poseStack, xRenderOrigin, yRenderOrigin, xRenderEnd, yRenderEnd);

    }





    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        poseStack.clear();

        renderBackground(poseStack);
        renderBg(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        addButtonsToGui(poseStack);

        if(closebutton.isMouseOver(mouseX,mouseY)) {
            addTextToGui(poseStack, 7368816);
        } else {
            addTextToGui(poseStack, 4210752);
        }
        poseStack.popPose();





    }

    private void close(Button button) {
        onClose();
    }


    @Override
    public void onClose() {
        ButtonsRendered = false;
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }




}
