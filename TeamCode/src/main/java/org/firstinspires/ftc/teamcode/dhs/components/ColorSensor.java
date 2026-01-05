package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class ColorSensor {
    public NormalizedColorSensor sensor;
    public final NormalizedRGBA GREEN_COLOR = new NormalizedRGBA();
    public final NormalizedRGBA PURPLE_COLOR = new NormalizedRGBA();

    public enum ArtifactState {
        GREEN,
        PURPLE,
        NONE
    }

    public ColorSensor(HardwareMap hardwareMap) {
        sensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

        // TODO: Figure out these colors
        GREEN_COLOR.red = 0;
        GREEN_COLOR.green = 0;
        GREEN_COLOR.blue = 0;
        GREEN_COLOR.alpha = 1;

        PURPLE_COLOR.red = 0;
        PURPLE_COLOR.green = 0;
        PURPLE_COLOR.blue = 0;
        PURPLE_COLOR.alpha = 1;
    }

    // TODO: Implement
    public boolean isBallInSensor() {
        return false;
    }

    // TODO: Implement
    public ArtifactState getBallInSensor() {
        return ArtifactState.NONE;
    }

    // TODO: Implement
    public NormalizedRGBA getSensorColor() {
        return sensor.getNormalizedColors();
    }

    public String rgbToString() {
        NormalizedRGBA color = getSensorColor();

        return "("+color.red+" "+color.green+" "+color.blue+" "+color.alpha+")";
    }
}
