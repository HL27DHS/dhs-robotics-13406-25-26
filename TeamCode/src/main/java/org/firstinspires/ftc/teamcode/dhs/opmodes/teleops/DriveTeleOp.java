package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;

@TeleOp(name="Drive the robot")
public class DriveTeleOp extends OpMode {
    Drivetrain drivetrain;
    Spintake spintake;
    Launcher launcher;
    float spintakeModifier = 1;
    boolean rightBumperLastTick = false;
    boolean useFod = false;

    /* function used for testing
    public void reverseMotorDirection(DcMotor motor) {
        if (motor.getDirection() == DcMotorSimple.Direction.REVERSE)
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    */

    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
    }

    public void loop() {
        if (useFod)
            drivetrain.fodDrive(gamepad1.right_stick_x, gamepad1.left_stick_x, -gamepad1.left_stick_y);
        else
            drivetrain.rodDrive(gamepad1.right_stick_x, gamepad1.left_stick_x, -gamepad1.left_stick_y);

        spintake.spintakeMotor.setPower(gamepad1.left_trigger * ((gamepad1.b) ? -1 : 1));
        launcher.flywheel.setPower(gamepad1.right_trigger * ((gamepad1.b) ? -1 : 1));

        if (gamepad1.right_bumper && !rightBumperLastTick)
            useFod = !useFod;

        if (gamepad1.x)
            drivetrain.imu.resetYaw();

        rightBumperLastTick = gamepad1.right_bumper;
    }
}