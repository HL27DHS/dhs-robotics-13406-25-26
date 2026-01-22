package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.dhs.utils.DataUtils;
import org.firstinspires.ftc.teamcode.dhs.utils.History;

public class Launcher {
    // TODO: Clean up usages and make private
    public DcMotorEx flywheelMotor;
    private final int FLYWHEEL_MAX_VELOCITY = 2380; // thank you Hayden
    // TODO: Clean up usages and make private
    public DcMotor cycleMotor;

    // TODO: Make this value final and private once it is tuned
    // Threshold for ball fired detection (RPM)
    public double flywheelFireThreshold = 60;

    public final double cycleSpinToFireMS = 50;
    private int flywheelTargetVelocity = 0;

    public final double flywheelTicksPerRotation = 28;

    // flywheel history related variables
    private final int HISTORY_DEPTH = 10;
    private History<Double> flywheelHistory = new History(HISTORY_DEPTH);



    public Launcher(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");
        cycleMotor = hardwareMap.get(DcMotor.class, "cycle");

        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cycleMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        cycleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets the power of the flywheel motor
     * @param power The power to set the flywheel motor to
     */
    public void setFlywheelPower(double power) { flywheelMotor.setPower(power); }

    /**
     * Sets the velocity of the flywheel motor
     * @param velocity The velocity to set the flywheel motor to
     */
    public void setFlywheelVelocity(int velocity) {
        // make sure velocity isn't set too high
        flywheelMotor.setVelocity(Math.min(velocity, getFlywheelMaxVelocity()));
        flywheelTargetVelocity = velocity;
    }

    /**
     * Gets the velocity of the flywheel
     * @return The flywheel's current velocity
     */
    public double getFlywheelVelocity() { return flywheelMotor.getVelocity(); }

    /**
     * @return the flywheel's rotations per second
     */
    public double getFlywheelRPS() { return getFlywheelVelocity() / flywheelTicksPerRotation; }

    /**
     * @return the flywheel's rotations per minute
     */
    public double getFlywheelRPM() { return getFlywheelRPS() * 60; }

    /**
     * @return the flywheel's desired rotations per second
     */
    public double getFlywheelTargetRPS() { return getFlywheelTargetVelocity() / flywheelTicksPerRotation; }

    /**
     * @return the flywheel's desired rotations per minute
     */
    public double getFlywheelTargetRPM() { return getFlywheelTargetRPS() * 60; }

    public int getFlywheelTargetVelocity() {
        return flywheelTargetVelocity;
    }

    /**
     * Gets the maximum velocity the flywheel motor can have
     * @return The maximum velocity of the flywheel
     */
    public int getFlywheelMaxVelocity() { return FLYWHEEL_MAX_VELOCITY; }

    /**
     * Sets the power of the cycle motor
     * @param power the power to set the cycle motor to spin at
     */
    public void setCyclePower(double power) { cycleMotor.setPower(power); }

    // ACTIONS BELOW

    /**
     * @param desiredVel The desired velocity to spin the flywheel at
     * @return an {@link com.acmerobotics.roadrunner.Action} to ready the flywheel for firing
     *         by setting it to a specified velocity. Will return true until velocity is met.
     */
    public Action getReadyAction(int desiredVel) {
        // anonymous class
        return new Action() {
            boolean initialized = false;
            final int desiredVelocity;

            // instance initializer
            { this.desiredVelocity = Math.min(desiredVel, getFlywheelMaxVelocity()); }

            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    setFlywheelPower(1);
                    setFlywheelVelocity(desiredVelocity);
                    initialized = true;
                }

                double vel = getFlywheelVelocity();
                packet.put("shooterVelocity", vel);
                return vel < desiredVelocity;
            }
        };
    }

    /**
     * @return an {@link com.acmerobotics.roadrunner.Action} to launch an artifact
     */
    public Action getLaunchAction() {
        return new Action() {
            boolean initialized = false;

            public boolean run(@NonNull TelemetryPacket packet) {
                // start cycler
                if (!initialized) {
                    setCyclePower(1);
                    packet.addLine("shot not fired yet!");
                }

                // TODO: Find better, more consistent way to check if shot has been fired
                // if within firing threshold, stop because we've fired
                if (DataUtils.threshold(getFlywheelRPM(), getFlywheelTargetRPM(), flywheelFireThreshold)) {
                    setCyclePower(0);
                    packet.addLine("shots fired!");
                    return false;
                }

                return true;
            }
        };
    }

    /**
     * @param fireMS the time (milliseconds) to spin the cycler to fire
     * @return an {@link com.acmerobotics.roadrunner.Action} to spin the cycler for a certain
     *         amount of time to fire an artifact
     */
    public Action getLaunchWithTimeAction(double fireMS) {
        return new Action() {
            final double fireTimeMS;

            // instance initializer
            { this.fireTimeMS = fireMS; }

            public boolean run(@NonNull TelemetryPacket packet) {
                // TODO: Implement LaunchWithTime
                return false;
            }};
    }

    /**
     * @return an {@link com.acmerobotics.roadrunner.Action} to unready the flywheel
     */
    public Action getUnreadyAction() {
        return new Action() {
            public boolean run(@NonNull TelemetryPacket packet) {
                setFlywheelVelocity(0);
                return false;
                //return flywheelMotor.getVelocity() > 0;
            }
        };
    }

    /**
     * @param pwr the power to set the cycler motor to spin at
     * @return an {@link com.acmerobotics.roadrunner.Action} to start the cycler motor with specified
     *         power
     */
    public Action getStartCycleAction(double pwr) {
        return new Action() {
            final double power;

            // instance initializer
            { this.power = pwr; }

            public boolean run(@NonNull TelemetryPacket packet) {
                setCyclePower(power);
                return false;
            }
        };
    }

    /**
     * @return an {@link com.acmerobotics.roadrunner.Action} to stop the cycler motor
     */
    public Action getStopCycleAction() {
        return new Action() {
            public boolean run(@NonNull TelemetryPacket packet) {
                setCyclePower(0);
                return false;
            }
        };
    }
}
