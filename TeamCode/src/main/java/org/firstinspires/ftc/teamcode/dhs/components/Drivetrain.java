package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

public class Drivetrain {
    public DcMotor FRMotor;
    public DcMotor FLMotor;
    public DcMotor BRMotor;
    public DcMotor BLMotor;

    public IMU imu;

    // Field-Oriented Drive
    public void fodDrive(double turnVal, double driveX, double driveY) {

    }

    // Robot-Oriented Drive
    public void rodDrive(double turnVal, double driveX, double driveY) {

    }
}
