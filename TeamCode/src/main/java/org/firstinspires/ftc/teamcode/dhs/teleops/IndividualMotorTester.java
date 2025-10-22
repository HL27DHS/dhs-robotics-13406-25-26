package org.firstinspires.ftc.teamcode.dhs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;

@TeleOp(name="Individual Motor Tester")
public class IndividualMotorTester extends OpMode {
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
            drivetrain.blMotor.setPower(1*modifier);
        else
            drivetrain.blMotor.setPower(0*modifier);

        if (gamepad1.b)
            drivetrain.brMotor.setPower(1*modifier);
        else
            drivetrain.brMotor.setPower(0*modifier);

        if (gamepad1.x)
            drivetrain.flMotor.setPower(1*modifier);
        else
            drivetrain.flMotor.setPower(0*modifier);

        if (gamepad1.y)
            drivetrain.frMotor.setPower(1*modifier);
        else
            drivetrain.frMotor.setPower(0*modifier);

    }
}
