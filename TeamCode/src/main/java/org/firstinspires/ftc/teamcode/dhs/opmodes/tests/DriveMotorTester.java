package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;

@TeleOp(name="Drive Motor Tester",group="B - Testing Programs")
public class DriveMotorTester extends OpMode {
    Drivetrain drivetrain;
    double modifier = 1;


    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
    }

    public void loop() {
        if (gamepad1.right_bumper || gamepad1.left_bumper)
            modifier = -1;
        else
            modifier = 1;

        if (gamepad1.a)
            drivetrain.getBlMotor().setPower(1*modifier);
        else
            drivetrain.getBlMotor().setPower(0);

        if (gamepad1.b)
            drivetrain.getBrMotor().setPower(1*modifier);
        else
            drivetrain.getBrMotor().setPower(0);

        if (gamepad1.x)
            drivetrain.getFlMotor().setPower(1*modifier);
        else
            drivetrain.getFlMotor().setPower(0);

        if (gamepad1.y)
            drivetrain.getFrMotor().setPower(1*modifier);
        else
            drivetrain.getFrMotor().setPower(0);
    }
}
