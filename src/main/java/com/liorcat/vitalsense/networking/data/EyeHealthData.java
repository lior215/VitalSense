package com.liorcat.vitalsense.networking.data;

import com.liorcat.vitalsense.VitalSense;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record EyeHealthData(boolean active, float health, float minHealth, float maxHealth) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(VitalSense.MOD_ID, "eye_health_data");

    public EyeHealthData(final FriendlyByteBuf buffer) {
        this(buffer.readBoolean(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(active());
        buffer.writeFloat(health());
        buffer.writeFloat(minHealth());
        buffer.writeFloat(maxHealth());
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
