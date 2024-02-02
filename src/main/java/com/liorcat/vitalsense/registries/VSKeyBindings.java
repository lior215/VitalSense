package com.liorcat.vitalsense.registries;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class VSKeyBindings {
        private static final String KEY_CATEGORY_VITALSENSE = "key.category.vitalsense.vital";
        public static final String open_eye_gui = "key.category.vitalsense.open_eye_gui";

        public static final KeyMapping OPEN_EYE_GUI = new KeyMapping(open_eye_gui, KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_VITALSENSE);

}
