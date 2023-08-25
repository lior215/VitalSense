package com.lior215.vitalsense.network.packets;

import com.lior215.vitalsense.client.ClientAirQuality;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CAirQuality {
    private final float airQuality;

    public S2CAirQuality(float airQuality) {
        this.airQuality = airQuality;
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
            ClientAirQuality.set(airQuality);
        });
        return true;
    }
}
