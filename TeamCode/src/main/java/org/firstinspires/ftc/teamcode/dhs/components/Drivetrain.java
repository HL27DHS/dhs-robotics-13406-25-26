package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain {
    public HardwareMap hardwareMap;

    public DcMotorEx frMotor;
    public DcMotorEx flMotor;
    public DcMotorEx brMotor;
    public DcMotorEx blMotor;

    public IMU imu;

    public Drivetrain(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        frMotor = hardwareMap.get(DcMotorEx.class, "FRMotor");
        flMotor = hardwareMap.get(DcMotorEx.class, "FLMotor");
        brMotor = hardwareMap.get(DcMotorEx.class, "BRMotor");
        blMotor = hardwareMap.get(DcMotorEx.class, "BLMotor");

        imu = hardwareMap.get(IMU.class, "imu");
        // TODO: Initialize IMU when we have Control Hub mounted
    }

    // Field-Oriented Drive
    public void fodDrive(double turnVal, double driveX, double driveY) {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = driveX * Math.cos(-botHeading) - driveY * Math.sin(-botHeading);
        double rotY = driveX * Math.sin(-botHeading) + driveY * Math.cos(-botHeading);

        double modifiedX = rotX * 1.1; // set modifier as needed

        double denominator = Math.max(Math.abs(rotY) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        flMotor.setPower((rotY + modifiedX + turnVal) / denominator);
        blMotor.setPower((rotY - modifiedX + turnVal) / denominator);
        frMotor.setPower((rotY - modifiedX - turnVal) / denominator);
        brMotor.setPower((rotY + modifiedX - turnVal) / denominator);
    }

    // Robot-Oriented Drive
    public void rodDrive(double turnVal, double driveX, double driveY) {
        double modifiedX = driveX * 1.1; // set modifier as needed
        double denominator = Math.max(Math.abs(driveY) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        flMotor.setPower((driveY + modifiedX + turnVal) / denominator);
        blMotor.setPower((driveY - modifiedX + turnVal) / denominator);
        frMotor.setPower((driveY - modifiedX - turnVal) / denominator);
        brMotor.setPower((driveY + modifiedX - turnVal) / denominator);
    }
}
