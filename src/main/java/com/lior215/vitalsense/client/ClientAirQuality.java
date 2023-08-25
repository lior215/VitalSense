package com.lior215.vitalsense.client;

public class ClientAirQuality {
    private static float airQuality;

    public static void set(float value) {
        ClientAirQuality.airQuality = value;
    }

    public static float getAirQuality() {
        return airQuality;
    }

    /**
     * Only use this if you can't use the {@link com.lior215.vitalsense.network.packets.S2CAirQuality} Packet
     */
    @Deprecated
    public static void decreaseAirQuality(float value) {
        airQuality = airQuality - value;
    }
}
