package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.dhs.components.ColorSensor;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Color Sensor Test",group="B - Testing Programs")
public class ColorSensorTest extends OpMode {
    ColorSensor sensor;
    SmartController controller1;
    double gainStep = 0.1;

    public void init() {
        sensor = new ColorSensor(hardwareMap);
        controller1 = new SmartController();
    }

    public void loop() {
        controller1.think(gamepad1);

        if (controller1.dpadUp.justPressed())
            sensor.getNormalizedColorSensor().setGain(sensor.getNormalizedColorSensor().getGain() + (float) gainStep);
        if (controller1.dpadDown.justPressed())
            sensor.getNormalizedColorSensor().setGain(sensor.getNormalizedColorSensor().getGain() - (float) gainStep);


        telemetry.addData("color",sensor.rgbToString());
        telemetry.addData("gain",sensor.getNormalizedColorSensor().getGain());
        telemetry.addLine();
        telemetry.addData("distance",sensor.getDistance(DistanceUnit.CM));
        telemetry.update();
    }
}
