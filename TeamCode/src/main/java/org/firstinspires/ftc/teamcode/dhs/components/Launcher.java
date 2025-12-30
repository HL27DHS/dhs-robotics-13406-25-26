package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

public class Launcher {
    public DcMotorEx flywheelMotor;
    private final int FLYWHEEL_MAX_VELOCITY = 2380; // thank you Hayden
    public DcMotor cycleMotor;

    public final double cycleSpinToFireMS = 50;

    public Launcher(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");
        cycleMotor = hardwareMap.get(DcMotor.class, "cycle");

        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cycleMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        cycleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    public void setFlywheelVelocity(int velocity) { flywheelMotor.setVelocity(velocity); }

    /**
     * Gets the velocity of the flywheel
     * @return The flywheel's current velocity
     */ // FIXME: currently does not report flywheel velocity correctly
    public double getFlywheelVelocity() { return flywheelMotor.getVelocity(); }

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

    public class Ready implements Action {
        boolean initialized = false;
        int desiredVelocity;

        public Ready(int desiredVelocity) {
            this.desiredVelocity = Math.min(desiredVelocity, getFlywheelMaxVelocity());
        }

        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                setFlywheelPower(1);
                setFlywheelVelocity(desiredVelocity);
                initialized = true;
            }

            // FIXME: Currently runs forever due to the fact that getFlywheelVelocity incorrectly reports value
            double vel = getFlywheelVelocity();
            packet.put("shooterVelocity", vel);
            return vel < desiredVelocity;
        }
    }
    public Action getReadyAction(int desiredVelocity) {
        return new Ready(desiredVelocity);
    }

    // This action (as of right now) just spins the cycle
    // motor for the time set in cycleSpinToFireMS
    public class Launch implements Action {
        ElapsedTime timer;
        boolean initialized;

        public Launch() {
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
    }
    public Action getLaunchAction() {
        return new Launch();
    }

    public class Unready implements Action {
        public boolean run(TelemetryPacket packet) {
            setFlywheelVelocity(0);
            return flywheelMotor.getVelocity() > 0;
        }
    }
    public Action getUnreadyAction() {
        return new Unready();
    }

    public class StartCycle implements Action {
        double power;

        public StartCycle(double power) {
            this.power = power;
        }

        public boolean run(TelemetryPacket packet) {
            setCyclePower(power);
            return false;
        }
    }
    public Action getStartCycleAction(double power) {
        return new StartCycle(power);
    }

    public class StopCycle implements Action {
        public boolean run(TelemetryPacket packet) {
            setCyclePower(0);
            return false;
        }
    }
    public Action getStopCycleAction() {
        return new StopCycle();
    }
}
