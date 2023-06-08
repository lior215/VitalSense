package it.lior215.vitalsense.Config;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

public class ModClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    private static final Logger LOGGER = LogUtils.getLogger();

        static {
            BUILDER.push("Client configs for the VitalSense Mod");

            // HERE DEFINE YOUR CONFIGS

            BUILDER.pop();
            SPEC = BUILDER.build();
        }



        public static void checkConfigs() {
            String vital = "[VitalSenseMod]";
            LOGGER.info(vital+" Client configs");
            LOGGER.info(vital+" Needs to be added");
        }
}
