package com.lior215.vitalsense.client;

public class ClientAirQuality {
    private static float airQuality;

    public static void set(float value) {
        airQuality = value;
    }

    public static float getAirQuality() {
        return airQuality;
    }
}
