package com.lior215.vitalsense.config;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ForgeConfigSpec.ConfigValue<Boolean> ToggleBlinkMechanic;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ToggleBreathingMechanic;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ToggleBlinkRenderOnF1;

    static {
        BUILDER.push("Configs for Tutorial Mod");

        ToggleBlinkMechanic = BUILDER.comment("Toggle blink mechanic(true/false)")
                .define("Enable/Disable", true);
        ToggleBlinkRenderOnF1 = BUILDER.comment("Toggle blink mechanic(true/false)")
                .define("Enable/Disable", true);
        ToggleBreathingMechanic = BUILDER.comment("Toggle breathing mechanic(true/false)") //Needs to be implemented in code
                .define("Enable/Disable", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }



    public static void checkConfigs() {
        LOGGER.info("Common configs");
        String vital = "[VitalSenseMod]";
        LOGGER.info(vital+" Blink Mechanic is: "+ToggleBlinkMechanic.get());
        LOGGER.info(vital+" Blink Rendering during F1 is: "+ToggleBlinkMechanic.get());
        LOGGER.info(vital+" Breathing Mechanic is: "+ToggleBlinkMechanic.get());

    }
}
