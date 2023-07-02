package com.lior215.vitalsense.network;

import com.lior215.vitalsense.client.ClientEyeHealth;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CAirQuality {
    private final float airQuality;

    public S2CAirQuality(float eyeHealth) {
        this.airQuality = eyeHealth;
    }

    public S2CAirQuality(FriendlyByteBuf buf) {
        this.airQuality = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(airQuality);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //client
            //ClientEyeHealth.set(airQuality);
        });
        return true;
    }
}
