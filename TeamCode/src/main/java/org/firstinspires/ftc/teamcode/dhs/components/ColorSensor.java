package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class ColorSensor {
    public NormalizedColorSensor sensor;

    public enum ArtifactState {
        GREEN,
        PURPLE,
        NONE
    }

    public ColorSensor(HardwareMap hardwareMap) {
        sensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        sensor.setGain(11.3F);
    }

    // TODO: Implement
    public boolean isBallInSensor() {
        return false;
    }

    // TODO: Implement
    public ArtifactState getBallInSensor() {
        return ArtifactState.NONE;
    }

    public NormalizedRGBA getSensorColor() {
        return sensor.getNormalizedColors();
    }

    public String rgbToString() {
        NormalizedRGBA color = getSensorColor();

        return "("+color.red+" "+color.green+" "+color.blue+" "+color.alpha+")";
    }
}
