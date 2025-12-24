package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    public DcMotorEx flywheel;
    private final int FLYWHEEL_MAX_VELOCITY = 2380; // thank you Hayden

    public Launcher(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Sets the power of the flywheel motor
     * @param power The power to set the flywheel motor to
     */
    public void setFlywheelPower(double power) { flywheel.setPower(power); }

    /**
     * Sets the velocity of the flywheel motor
     * @param velocity The velocity to set the flywheel motor to
     */
    public void setFlywheelVelocity(int velocity) { flywheel.setVelocity(velocity); }

    /**
     * Gets the velocity of the flywheel
     * @return The flywheel's current velocity
     */
    public double getFlywheelVelocity() { return flywheel.getVelocity(); }

    /**
     * Gets the maximum velocity the flywheel motor can have
     * @return The maximum velocity of the flywheel
     */
    public int getFlywheelMaxVelocity() { return FLYWHEEL_MAX_VELOCITY; }

    public class Ready implements Action {
        boolean initialized = false;

        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                setFlywheelPower(1);
                initialized = true;
            }

            double vel = getFlywheelVelocity();
            packet.put("shooterVelocity", vel);
            return vel < 10_000.0;
        }
    }
    public Action ready() {
        return new Ready();
    }

    public class Launch implements Action {
        public boolean run(TelemetryPacket packet) {
            // TODO: Implement "Launch" action for launching a ball
            return false;
        }
    }
    public Action launch() {
        return new Launch();
    }

    public class Unready implements Action {
        public boolean run(TelemetryPacket packet) {
            setFlywheelVelocity(0);
            return flywheel.getVelocity() > 0;
        }
    }
    public Action unready() {
        return new Unready();
    }
}
