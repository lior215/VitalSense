package com.liorcat.vitalsense.config;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

public class ModCommonConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ModConfigSpec.ConfigValue<Boolean> ToggleBlinkMechanic;
    public static final ModConfigSpec.ConfigValue<Boolean> ToggleBreathingMechanic;
    public static final ModConfigSpec.ConfigValue<Boolean> ToggleBlinkRenderOnF1;
    public static final ModConfigSpec.ConfigValue<Boolean> ToggleDiseaseScreenOnF1;

    static {
        BUILDER.push("Common configs for VitalSense Mod");

        ToggleBlinkMechanic = BUILDER.comment("Toggle blink mechanic(true/false)")
                .define("Enable/Disable", true);
        ToggleBlinkRenderOnF1 = BUILDER.comment("Toggle blink rendering on F1 mechanic(true/false)")
                .define("Enable/Disable", true);
        ToggleBreathingMechanic = BUILDER.comment("Toggle breathing mechanic(true/false)") //Needs to be implemented in code
                .define("Enable/Disable", true);
        ToggleDiseaseScreenOnF1 = BUILDER.comment("Toggle disease rendering on F1(true/false)") //Needs to be implemented in code
                .define("Enable/Disable", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }



    public static void checkConfigs() {
        LOGGER.info("Common configs");
        String vital = "[VitalSenseMod]";
        LOGGER.info(vital + " Blink Mechanic is: "+ ToggleBlinkMechanic.get());
        LOGGER.info(vital + " Blink Rendering during F1 is: "+ ToggleBlinkMechanic.get());
        LOGGER.info(vital + " Breathing Mechanic is: "+ ToggleBlinkMechanic.get());

    }
}
