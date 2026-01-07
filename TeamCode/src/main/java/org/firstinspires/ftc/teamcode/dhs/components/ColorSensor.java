package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensor {
    public NormalizedColorSensor normalizedColorSensor;
    public OpticalDistanceSensor opticalDistanceSensor;
    public DistanceSensor distanceSensor;

    public enum ArtifactState {
        GREEN,
        PURPLE,
        NONE
    }

    public ColorSensor(HardwareMap hardwareMap) {
        normalizedColorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        normalizedColorSensor.setGain(11.3F);

        opticalDistanceSensor = (OpticalDistanceSensor) normalizedColorSensor;
        distanceSensor = (DistanceSensor) normalizedColorSensor;
    }

    public NormalizedColorSensor getNormalizedColorSensor() {
        return normalizedColorSensor;
    }

    public OpticalDistanceSensor getOpticalDistanceSensor() {
        return opticalDistanceSensor;
    }

    public DistanceSensor getDistanceSensor() {
        return distanceSensor;
    }

    public double getDistance(DistanceUnit unit) {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    /**
     * Gets how confident the color sensor is that there is an artifact present in front of it.
     * @return The color sensor's confidence between 0 and 1
     */
    public double getArtifactPresentConfidence() {
        // TODO: Implement
        return 0.0;
    }

    /**
     * Gets how confident the color sensor is that there is a purple artifact in front of it.
     * @return The color sensor's confidence between 0 and 1
     */
    public double getPurpleArtifactConfidence() {
        // TODO: Implement
        return 0.0;
    }

    /**
     * Gets how confident the color sensor is that there is a green artifact in front of it.
     * @return The color sensor's confidence between 0 and 1
     */
    public double getGreenArtifactConfidence() {
        // TODO: Implement
        return 0.0;
    }

    /**
     * Checks if there is an artifact in front of the sensor
     * @return true if there is; false if there isn't
     */
    public boolean isArtifactInSensor() {
        // TODO: Implement in a way that uses confidence instead of blanket assumptions
        NormalizedRGBA color = getSensorColor();
        return (color.red > 0.01) && (color.green > 0.01) && (color.blue > 0.01);
    }

    /**
     * Checks what type of artifact the color sensor is most confident is in front of the sensor,
     * or NONE if it isn't confident enough one is present in front of the sensor.
     * @return the type of artifact in front of the sensor
     */
    public ArtifactState getArtifactInSensor() {
        // TODO: Implement
        return ArtifactState.NONE;
    }

    /**
     * Gets the color that the color sensor is reading
     * @return the color in front of the color sensor
     */
    public NormalizedRGBA getSensorColor() {
        return normalizedColorSensor.getNormalizedColors();
    }

    /**
     * Convert the color sensor's read values into a string formatted as follows: (R G B A)
     * @return the color sensor's values in a pretty string
     */
    public String rgbToString() {
        NormalizedRGBA color = getSensorColor();
        return "("+color.red+" "+color.green+" "+color.blue+" "+color.alpha+")";
    }

    /**
     * @return an {@code Action} that waits for an artifact to be present in front of the color sensor
     */
    public Action getWaitForArtifactAction() {
        // TODO: Research lambdas and anonymous classes.
        //       This stuff is odd and I don't have the time to give it thought right now.
        return (packet) -> {
            packet.put("sensor",isArtifactInSensor());
            return isArtifactInSensor();
        };
    }

    /**
     * @return an {@code Action} that waits for an artifact to not be present in front of the color sensor
     */
    public Action getWaitForArtifactLeaveAction() {
        return (packet) -> {
            packet.put("sensor",isArtifactInSensor());
            return !isArtifactInSensor();
        };
    }
}
