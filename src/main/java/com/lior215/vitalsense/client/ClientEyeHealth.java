package com.lior215.vitalsense.client;

public class ClientEyeHealth {
    private static float eyeHealth;

    public static void set(float thirst) {
        ClientEyeHealth.eyeHealth = thirst;
    }

    public static float getEyeHealth() {
        return eyeHealth;
    }
}
