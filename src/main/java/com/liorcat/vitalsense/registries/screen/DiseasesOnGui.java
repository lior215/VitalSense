package com.liorcat.vitalsense.registries.screen;

import com.liorcat.vitalsense.registries.effects.RedEyesEffect;

import java.util.Objects;

public enum DiseasesOnGui {
    RED_EYES("red_eyes"),
    DISEASE_2("disease2"),
    DISEASE_3("disease3");

    private String checkedDisease;

    DiseasesOnGui(String disease) {
        this.checkedDisease = disease;
    }

    public void setCheckedDisease(String checkedDisease) {
        this.checkedDisease = checkedDisease;
    }

    // What is this for? And why is it not static?
    public String checkRedEyes() {
        return "red_eyes";
    }

    public String checkDisease2() {
        return "disease2";
    }

    public String checkDisease3() {
        return "disease3";
    }

    public boolean getCheckedDisease() {
        if (RedEyesEffect.getRenderDisease() && Objects.equals(checkedDisease, "red_eyes")) {
            return true;
        } else {
            return false;
        }
    }
}
