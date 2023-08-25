package com.lior215.vitalsense.network.packets;

import com.lior215.vitalsense.client.ClientEyeHealth;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CEyeHealth {
    private final float eyeHealth;

    public S2CEyeHealth(float eyeHealth) {
        this.eyeHealth = eyeHealth;
    }

    public S2CEyeHealth(FriendlyByteBuf buf) {
        this.eyeHealth = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(eyeHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientEyeHealth.set(eyeHealth);
        });
        return true;
    }
}
