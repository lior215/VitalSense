package com.liorcat.vitalsense.capabilities;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class VSCapabilities {
    public static final class EyeHealth {
        public static final EntityCapability<IEyeHealth, Void> ENTITY = EntityCapability.createVoid(create("eye_health"), IEyeHealth.class);

        private EyeHealth() {
        }
    }

    public static final class AirQuality {

        private AirQuality() {
        }
    }

    private static ResourceLocation create(String path) {
        return new ResourceLocation(VitalSense.MOD_ID, path);
    }
}
