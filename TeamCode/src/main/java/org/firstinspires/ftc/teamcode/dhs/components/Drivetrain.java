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

        double rotX = driveX * Math.cos(-botHeading) - driveY * Math.sin(-botHeading);
        double rotY = driveX * Math.sin(-botHeading) + driveY * Math.cos(-botHeading);

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

    public void rodDrive(double turnVal, double driveX, double driveY) {
        double modifiedX = driveX * 1.1; // set modifier as needed
        double denominator = Math.max(Math.abs(driveY) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        //          Forward     Strafe     Turn
        double fl = (driveY + modifiedX + turnVal) / denominator;
        double bl = (driveY - modifiedX + turnVal) / denominator;
        double fr = (driveY - modifiedX - turnVal) / denominator;
        double br = (driveY + modifiedX - turnVal) / denominator;

        drive.leftFront.setPower(fl);
        drive.leftBack.setPower(bl);
        drive.rightFront.setPower(fr);
        drive.rightBack.setPower(br);
    }
}
