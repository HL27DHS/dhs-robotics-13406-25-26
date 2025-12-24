package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    // Function to spin the flywheel of the bot at a certain speed
    public void setFlywheelPower(double power) {
        flywheel.setPower(power);
    }

    // Function to set the velocity of the flywheel
    public void setFlywheelVelocity(int velocity) { flywheel.setVelocity(velocity); }

    //
    public int getFlywheelMaxVelocity() { return maxVelocity; }

    public class Ready implements Action {
        public boolean run(TelemetryPacket packet) {
            int desiredVelocity = maxVelocity;

            setFlywheelPower(1);
            setFlywheelVelocity(desiredVelocity);

            return flywheel.getVelocity() < desiredVelocity;
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
            return false;
        }
    }
    public Action unready() {
        return new Unready();
    }
}
