package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Drivetrain {
    public HardwareMap hardwareMap;

    private MecanumDrive drive;
    public IMU imu; // TODO: Figure out why 7571 uses BNO055IMU class

    public Drivetrain(HardwareMap hardwareMap) {
        Pose2d initialPose = new Pose2d(0, 0, 0);

        this.drive = new MecanumDrive(hardwareMap, initialPose);
    }

    public MecanumDrive getDrive() { return drive; }

    public void fodDrive(double turnVal, double driveX, double driveY) {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

        double modifiedX = rotX * 1.1; // set modifier as needed

        double denominator = Math.max(Math.abs(rotY) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        //         Forward    Strafe     Turn
        double fl = (rotY + modifiedX + turnVal) / denominator;
        double bl = (rotY - modifiedX + turnVal) / denominator;
        double fr = (rotY - modifiedX - turnVal) / denominator;
        double br = (rotY + modifiedX - turnVal) / denominator;

        drive.leftFront.setPower(fl);
        drive.leftBack.setPower(bl);
        drive.rightFront.setPower(fr);
        drive.rightBack.setPower(br);
    }

    /**
     * Calculates and executes Mecanum Robot-Oriented Drive values given the below parameters
     *
     * @param turnVal The rate at which the bot should turn between -1 (cw) and 1 (ccw),
     *                for example, a right stick x value.
     * @param strafe The bot's strafe or side-to-side drive power between -1 (left) and 1 (right),
     *               for example, a left stick x value.
     * @param forward The bot's forward and back drive power between -1 (back) and 1 (forward),
     *               for example, a left stick y value.
     */
    public void rodDrive(double turnVal, double strafe, double forward) {
        double modifiedX = strafe * 1.1; // set modifier as needed
        double denominator = Math.max(Math.abs(forward) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        //          Forward     Strafe     Turn
        double fl = (forward + modifiedX + turnVal) / denominator;
        double bl = (forward - modifiedX + turnVal) / denominator;
        double fr = (forward - modifiedX - turnVal) / denominator;
        double br = (forward + modifiedX - turnVal) / denominator;

        drive.leftFront.setPower(fl);
        drive.leftBack.setPower(bl);
        drive.rightFront.setPower(fr);
        drive.rightBack.setPower(br);
    }
}
