package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Drivetrain {
    public HardwareMap hardwareMap;

    //private MecanumDrive drive;
    public DcMotor flMotor;
    public DcMotor frMotor;
    public DcMotor blMotor;
    public DcMotor brMotor;

    public IMU imu; // TODO: Figure out why 7571 uses BNO055IMU class
    public double imuOffset; // IMU offset in RADIANS

    public Drivetrain(HardwareMap hardwareMap) {
        //Pose2d initialPose = new Pose2d(0, 0, 0);

        //this.drive = new MecanumDrive(hardwareMap, initialPose);

        flMotor = hardwareMap.get(DcMotor.class, "FLMotor");
        frMotor = hardwareMap.get(DcMotor.class, "FRMotor");
        blMotor = hardwareMap.get(DcMotor.class, "BLMotor");
        brMotor = hardwareMap.get(DcMotor.class, "BRMotor");

        imu = hardwareMap.get(IMU.class, "imu");

        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                        )
                )
        );

        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /** @return The IMU object */
    public IMU getIMU() { return imu; }

    /** @return The front left wheel motor */
    public DcMotor getFlMotor() { return flMotor; }

    /** @return The back left wheel motor */
    public DcMotor getBlMotor() { return blMotor; }

    /** @return The front right wheel motor */
    public DcMotor getFrMotor() { return frMotor; }

    /** @return The back right wheel motor */
    public DcMotor getBrMotor() { return brMotor; }

    /** @return The front left wheel motor */
    //public DcMotor getFlMotor() { return drive.leftFront; }

    /** @return The back left wheel motor */
    //public DcMotor getBlMotor() { return drive.leftBack; }

    /** @return The front right wheel motor */
    //public DcMotor getFrMotor() { return drive.rightFront; }

    /** @return The back right wheel motor */
    //public DcMotor getBrMotor() { return drive.rightBack; }


    /** @return The roadrunner {@code MecanumDrive} drivetrain object */
    //public MecanumDrive getDrive() { return drive; }

    /**
     * Resets the robot's IMU offset, effectively works like imu.resetYaw
     * without disturbing RoadRunner
     */
    public void resetImuOffset() {
        imuOffset = getIMU().getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    /**
     * @param angleUnit the angle unit to return in (degrees, radians)
     * @return the robot's yaw angle + the IMU offset in specified angle unit
     */
    public double getYaw(AngleUnit angleUnit) {
        return getIMU().getRobotYawPitchRollAngles().getYaw(angleUnit)
                + ((angleUnit == AngleUnit.DEGREES) ? Math.toDegrees(imuOffset) : imuOffset);
    }

    /**
     * Calculates and executes Mecanum Field-Oriented Drive values given the below parameters
     *
     * @param turnVal The rate at which the bot should turn between -1 (cw) and 1 (ccw),
     *                for example, a right stick x value.
     * @param strafe The bot's strafe or side-to-side drive power between -1 (left) and 1 (right),
     *               for example, a left stick x value.
     * @param forward The bot's forward and back drive power between -1 (back) and 1 (forward),
     *               for example, a left stick y value.
     */
    public void fodDrive(double turnVal, double strafe, double forward) {
        double botHeading = getYaw(AngleUnit.RADIANS);

        double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

        double modifiedX = rotX * 1.1; // set modifier as needed

        double denominator = Math.max(Math.abs(rotY) + Math.abs(modifiedX) + Math.abs(turnVal), 1);

        //         Forward    Strafe     Turn
        double fl = (rotY + modifiedX + turnVal) / denominator;
        double bl = (rotY - modifiedX + turnVal) / denominator;
        double fr = (rotY - modifiedX - turnVal) / denominator;
        double br = (rotY + modifiedX - turnVal) / denominator;

        getFlMotor().setPower(fl);
        getBlMotor().setPower(bl);
        getFrMotor().setPower(fr);
        getBrMotor().setPower(br);
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

        getFlMotor().setPower(fl);
        getBlMotor().setPower(bl);
        getFrMotor().setPower(fr);
        getBrMotor().setPower(br);
    }
}
