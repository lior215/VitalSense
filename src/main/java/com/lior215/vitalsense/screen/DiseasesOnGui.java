package com.lior215.vitalsense.screen;

import com.lior215.vitalsense.effects.RedEyesEffect;

import java.util.Objects;

public enum DiseasesOnGui {


    Red_Eyes("red_eyes"),
    Disease2("disease2"),
    Disease3("disease3");

    private String checkedDisease;

    DiseasesOnGui(String disease) {
        this.checkedDisease = disease;
    }

    public void setCheckedDisease(String checkedDisease) {
        this.checkedDisease = checkedDisease;
    }

    public String checkRedEyes() {
        return "red_eyes";
    }

    public String checkDisease2() { return "disease2"; }
    public String checkDisease3() {
        return "disease3";
    }

    public boolean getCheckedDisease() {
        if(RedEyesEffect.getRenderDisease() && Objects.equals(checkedDisease, "red_eyes")) {
            return true;
        } else {
            return false;
        }
    }
}
