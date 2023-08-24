package com.lior215.vitalsense.client;

public class ClientEyeHealth {
    private static float eyeHealth;

    public static void set(float value) {
        ClientEyeHealth.eyeHealth = value;
    }

    public static float getEyeHealth() {
        return eyeHealth;
    }



    //USE THE PACKET S2C BUT IF YOU CAN'T THEN USE THIS

    public static void decreaseEyeHealth(float value) {
        eyeHealth = eyeHealth - value;
    }
}
