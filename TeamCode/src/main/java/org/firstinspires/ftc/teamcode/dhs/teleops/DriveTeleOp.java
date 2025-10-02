package org.firstinspires.ftc.teamcode.dhs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;

@TeleOp(name="Drive the robot")
public class DriveTeleOp extends OpMode {
    Drivetrain drivetrain;

    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
    }

    public void loop() {
        drivetrain.fodDrive(gamepad1.right_stick_x, gamepad1.left_stick_x, -gamepad1.left_stick_y);
    }
}