package com.liorcat.vitalsense.data;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.data.air.AirQuality;
import com.liorcat.vitalsense.data.eyes.EyeHealth;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class VSAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, VitalSense.MOD_ID);

    public static final Supplier<AttachmentType<EyeHealth>> EYE_HEALTH = ATTACHMENT_TYPES.register(
            "eye_health", () -> AttachmentType.serializable(EyeHealth::new).copyOnDeath().build());

    public static final Supplier<AttachmentType<AirQuality>> AIR_QUALITY = ATTACHMENT_TYPES.register(
            "air_quality", () -> AttachmentType.serializable(AirQuality::new).build());
}
