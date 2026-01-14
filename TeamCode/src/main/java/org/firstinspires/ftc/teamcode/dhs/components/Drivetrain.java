package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Drivetrain {
    public HardwareMap hardwareMap;

    private MecanumDrive drive;


    public IMU imu;
    public double imuOffset_radians; // IMU offset in RADIANS

    public Drivetrain(HardwareMap hardwareMap, Pose2d pose) {
        this.drive = new MecanumDrive(hardwareMap, pose);
    }

    public Drivetrain(HardwareMap hardwareMap) {
        Pose2d initialPose = new Pose2d(0, 0, 0);

        this.drive = new MecanumDrive(hardwareMap, initialPose);
    }

    /** @return The IMU object */
    public IMU getIMU() { return drive.lazyImu.get(); }

    /** @return The front left wheel motor */
    public DcMotor getFlMotor() { return drive.leftFront; }

    /** @return The back left wheel motor */
    public DcMotor getBlMotor() { return drive.leftBack; }

    /** @return The front right wheel motor */
    public DcMotor getFrMotor() { return drive.rightFront; }

    /** @return The back right wheel motor */
    public DcMotor getBrMotor() { return drive.rightBack; }


    /** @return The roadrunner {@code MecanumDrive} drivetrain object */
    public MecanumDrive getDrive() { return drive; }

    /**
     * Resets the robot's IMU offset, effectively works like imu.resetYaw
     * without disturbing RoadRunner
     */
    public void resetImuOffset() {
        imuOffset_radians = getRealYaw(AngleUnit.RADIANS);
    }

    /**
     * Gets the IMU's real, unmodified yaw value
     * @param angleUnit the unit of angle measurement to return the yaw in
     * @return the robot's current yaw angle
     */
    public double getRealYaw(AngleUnit angleUnit) {
        return getIMU().getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    /**
     * @param angleUnit the angle unit to return in (degrees, radians)
     * @return the robot's yaw angle + the IMU offset in specified angle unit
     */
    public double getYaw(AngleUnit angleUnit) {
        double radians = AngleUnit.normalizeRadians(getRealYaw(AngleUnit.RADIANS) - imuOffset_radians);

        if (angleUnit == AngleUnit.DEGREES)
            return Math.toDegrees(radians);

        return radians;
    }

    /**
     * Gets the IMU's real, unmodified yaw value
     * @param angleUnit the unit of angle measurement to return the yaw in
     * @return the robot's current yaw angle
     */
    public double getPinpointRealYaw(AngleUnit angleUnit) {
        double radians = getDrive().localizer.getPose().heading.toDouble();

        if (angleUnit == AngleUnit.DEGREES)
            return Math.toDegrees(radians);

        return radians;
    }

    /**
     * @param angleUnit the angle unit to return in (degrees, radians)
     * @return the robot's yaw angle + the IMU offset in specified angle unit
     */
    public double getPinpointYaw(AngleUnit angleUnit) {
        double radians = AngleUnit.normalizeRadians(getRealYaw(AngleUnit.RADIANS) - imuOffset_radians);

        if (angleUnit == AngleUnit.DEGREES)
            return Math.toDegrees(radians);

        return radians;
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
