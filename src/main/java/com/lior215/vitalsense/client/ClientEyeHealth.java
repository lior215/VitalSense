package com.lior215.vitalsense.client;

public class ClientEyeHealth {
    private static float eyeHealth;

    public static void set(float value) {
        ClientEyeHealth.eyeHealth = value;
    }

    public static float getEyeHealth() {
        return eyeHealth;
    }
}
