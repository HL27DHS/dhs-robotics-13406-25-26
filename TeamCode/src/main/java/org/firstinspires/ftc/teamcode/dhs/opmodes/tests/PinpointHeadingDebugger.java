package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;

@TeleOp(name="PinpointHeadingDebugger",group="Testing Programs")
public class PinpointHeadingDebugger extends OpMode {
    Drivetrain drivetrain;

    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
    }

    public void loop() {
        telemetry.addData("Pinpoint Heading:",drivetrain.getDrive().localizer.getPose().heading);
    }
}
