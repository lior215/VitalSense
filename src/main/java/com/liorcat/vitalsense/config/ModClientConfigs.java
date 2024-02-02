package com.liorcat.vitalsense.config;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

public class ModClientConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        BUILDER.push("Client configs for the VitalSense Mod");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void checkConfigs() {
        String vital = "[VitalSenseMod]";
        LOGGER.info(vital + " Client configs");
        LOGGER.info(vital + " Needs to be added");
    }
}
