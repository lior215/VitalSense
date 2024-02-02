package com.liorcat.vitalsense.registries.screen;

import com.liorcat.vitalsense.VitalSense;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EyeGui extends Screen {

    private final DiseasesOnGui disease = DiseasesOnGui.RED_EYES;
    private final int x = Minecraft.getInstance().getWindow().getGuiScaledWidth();
    private final int y = Minecraft.getInstance().getWindow().getGuiScaledHeight();

    private int ySpacing = 0;
    private int affectedColorText = 0;
    private boolean buttonsRendered = false;

    // ImageButton closebutton = new ImageButton((int) ((x + 266) / 2.25f), (int) ((y - 130) / 2.25), 18, 18, 220, 198, 19, TEXTURE_RENDER_GUI, 256, 256, this::close);

    private static final ResourceLocation TEXTURE_RENDER_GUI = new ResourceLocation(VitalSense.MOD_ID, "textures/gui/eyestatsgui.png");

    public EyeGui() {
        super(Component.literal("eye gui"));
    }


    public void renderBg(@NotNull GuiGraphics guiGraphics) {
        //Base gui
        int xRenderOrigin = 0;
        int yRenderOrigin = 0;
        int xRenderEnd = 174;
        int yRenderEnd = 135;

        PoseStack poseStack = guiGraphics.pose();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_RENDER_GUI);

        poseStack.pushPose();
        poseStack.scale(1.25f, 1.25f, 1);
        guiGraphics.blit(TEXTURE_RENDER_GUI, (int) ((x - 173) / 2.75f), (int) ((y - 150) / 2.75f), xRenderOrigin, yRenderOrigin, xRenderEnd - xRenderOrigin, yRenderEnd - yRenderOrigin, 256, 256);
        //default mc color text: 4210752

        //extras
        eyeStatusToImage(guiGraphics);
        poseStack.popPose();


        //title
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 1.0f);
        guiGraphics.drawCenteredString(font, Component.translatable("vitalsense.gui.eyestatsgui").setStyle(Style.EMPTY.withColor(4210752)), (int) ((x - 1) / 1.85f), (int) ((y - 145) / 1.85f), 1);
        poseStack.popPose();

    }

    private void addButtonsToGui() {
        if (!buttonsRendered) {
            buttonsRendered = true;
            // addRenderableWidget(closebutton);
        }
    }

    private void addYSpacing(int yValue) {
        ySpacing += yValue;
    }

    public String checkForDiseaseAffections(String checkdisease) {
        disease.setCheckedDisease(checkdisease);

        if (disease.getCheckedDisease()) {
            affectedColorText = 9109504;
            return "vitalsense.gui.text.disease.active";
        } else if (!disease.getCheckedDisease()) {
            affectedColorText = 78368;
            return "vitalsense.gui.text.disease.not.active";
        } else {
            affectedColorText = 9109504;
            return "error";
        }
    }

    private void addTextToGui(GuiGraphics guiGraphics, int color) {
        int spacing = 20;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 1.0f);
        //todo: add text to gui stats

        //Button text
        //font.draw(poseStack, Component.translatable("vitalsense.gui.button.close").setStyle(Style.EMPTY.withColor(color)), (float) (x + 53) / 1.90f, (float) (y + 26) / 1.90f, 1 );

        //Disease Renderer
        guiGraphics.drawCenteredString(font, Component.translatable("vitalsense.gui.text.redeyedisease"),
                (int) ((x - 123) / 1.90f), (int) ((y + 46 + ySpacing) / 1.90f), color);
        guiGraphics.drawCenteredString(font, Component.translatable(checkForDiseaseAffections(disease.checkRedEyes())).setStyle(Style.EMPTY.withColor(affectedColorText)),
                (int) ((x - 23) / 1.90f), (int) ((y + 46 + ySpacing) / 1.90f), color);
        addYSpacing(spacing);
        guiGraphics.drawCenteredString(font, Component.translatable("vitalsense.gui.text.disease2").setStyle(Style.EMPTY.withColor(color)),
                (int) ((x - 123) / 1.90f), (int) ((y + 46 + ySpacing) / 1.90f), color);
        guiGraphics.drawCenteredString(font, Component.translatable(checkForDiseaseAffections(disease.checkDisease2())).setStyle(Style.EMPTY.withColor(affectedColorText)),
                (int) ((x - 23) / 1.90f), (int) ((y + 46 + ySpacing) / 1.90f), color);
        poseStack.popPose();
        ySpacing = 0;
    }


    public void renderEyeInScreen(GuiGraphics guiGraphics, int eyeXRenderOrigin, int eyeYRenderOrigin, int eyeXRenderEnd, int eyeYRenderEnd) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_RENDER_GUI);


        guiGraphics.blit(TEXTURE_RENDER_GUI, (int) ((x + 5) / 2.75f), (int) ((y - 79) / 2.75f), eyeXRenderOrigin, eyeYRenderOrigin, eyeXRenderEnd - eyeXRenderOrigin, eyeYRenderEnd - eyeYRenderOrigin, 256, 256);
    }


    public void eyeStatusToImage(GuiGraphics guiGraphics) {
        /*
        int xRenderOrigin = 0;
        int yRenderOrigin = 0;
        int xRenderEnd = 0;
        int yRenderEnd = 0;
        if (ClientEyeHealth.getEyeHealth() > 80f && ClientEyeHealth.getEyeHealth() <= 100f) {
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
        } else if (ClientEyeHealth.getEyeHealth() <= 0f) {
            xRenderOrigin = 98;
            yRenderOrigin = 227;
            xRenderEnd = 143;
            yRenderEnd = 252;
        }
        renderEyeInScreen(guiGraphics, xRenderOrigin, yRenderOrigin, xRenderEnd, yRenderEnd);
         */
    }


    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int mouseX, int mouseY, float delta) {
        pGuiGraphics.pose().clear();

        renderBackground(pGuiGraphics, mouseX, mouseY, delta);
        renderBg(pGuiGraphics);
        addTextToGui(pGuiGraphics, 4210752);
        super.render(pGuiGraphics, mouseX, mouseY, delta);
        addButtonsToGui();

        //if(closebutton.isMouseOver(mouseX,mouseY)) {
        //    addTextToGui(pGuiGraphics, 7368816);
        //} else {
        //    addTextToGui(pGuiGraphics, 4210752);
        //}
    }

    private void close(Button button) {
        onClose();
    }

    @Override
    public void onClose() {
        buttonsRendered = false;
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
