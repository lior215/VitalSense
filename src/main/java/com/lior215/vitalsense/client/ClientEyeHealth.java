package com.lior215.vitalsense.client;

public class ClientEyeHealth {
    private static float eyeHealth;

    public static void set(float value) {
        ClientEyeHealth.eyeHealth = value;
    }

    public static float getEyeHealth() {
        return eyeHealth;
    }

    /**
     * Only use this if you can't use the {@link com.lior215.vitalsense.network.packets.S2CEyeHealth} Packet
     */
    @Deprecated
    public static void decreaseEyeHealth(float value) {
        eyeHealth = eyeHealth - value;
    }
}
