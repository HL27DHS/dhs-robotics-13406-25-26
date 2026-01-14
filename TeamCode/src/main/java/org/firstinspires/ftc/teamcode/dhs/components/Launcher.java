package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.dhs.utils.History;

public class Launcher {
    public DcMotorEx flywheelMotor;
    private final int FLYWHEEL_MAX_VELOCITY = 2380; // thank you Hayden
    public DcMotor cycleMotor;

    public final double cycleSpinToFireMS = 50;
    private int flywheelTargetVelocity = 0;

    public final double FLYWHEEL_POLLING_RATE_MS = 150;
    public final int FLYWHEEL_HISTORY_DEPTH = 10;
    private final History<Double> flywheelHistory;
    private ElapsedTime deltaTimer;
    private double deltaTimeSum_ms = 0;


    public Launcher(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");
        cycleMotor = hardwareMap.get(DcMotor.class, "cycle");

        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cycleMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        cycleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flywheelHistory = new History<Double>(FLYWHEEL_HISTORY_DEPTH);

        deltaTimer = new ElapsedTime();
        deltaTimer.reset();
    }

    public void think() {
        double dt = deltaTimer.milliseconds();
        deltaTimer.reset();

        deltaTimeSum_ms += dt;

        if (deltaTimeSum_ms >= FLYWHEEL_POLLING_RATE_MS) {
            deltaTimeSum_ms = 0;

            flywheelHistory.add(getFlywheelVelocity());
        }
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
        flywheelMotor.setVelocity(velocity);
        flywheelTargetVelocity = velocity;
    }

    /**
     * Gets the velocity of the flywheel
     * @return The flywheel's current velocity
     */
    public double getFlywheelVelocity() { return flywheelMotor.getVelocity(); }

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
            int desiredVelocity;

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
            ElapsedTime timer;
            boolean initialized;

            { // instance initializer
                timer = new ElapsedTime();
                timer.reset();
            }

            public boolean run(TelemetryPacket packet) {
                // TODO: Make launch functionality more consistent, use dips in flywheel velocity
                if (timer.milliseconds() >= cycleSpinToFireMS) {
                    setCyclePower(0);
                    return false;
                }

                if (!initialized)
                    setCyclePower(1);

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
            public double fireTimeMS;

            // instance initializer
            { this.fireTimeMS = fireMS; }

            public boolean run(TelemetryPacket packet) {
                // TODO: Implement LaunchWithTime
                return false;
            }};
    }

    /**
     * @return an {@link com.acmerobotics.roadrunner.Action} to unready the flywheel
     */
    public Action getUnreadyAction() {
        return new Action() {
            public boolean run(TelemetryPacket packet) {
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
            double power;

            // instance initializer
            { this.power = pwr; }

            public boolean run(TelemetryPacket packet) {
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
            public boolean run(TelemetryPacket packet) {
                setCyclePower(0);
                return false;
            }
        };
    }
}
