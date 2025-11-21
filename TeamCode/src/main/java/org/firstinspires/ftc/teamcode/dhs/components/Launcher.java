package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    public DcMotor flywheel;

    public Launcher(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotor.class, "flywheel");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // Function to spin the flywheel of the bot at a certain speed
    public void setFlywheelPower(double power) {
        flywheel.setPower(power);
    }

    public class Ready implements Action {
        public boolean run(TelemetryPacket packet) {
            // TODO: Implement "Ready" action for getting the launcher prepared to launch
            return false;
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
            // TODO: Implement "Unready" action for winding down the flywheel
            return false;
        }
    }
    public Action unready() {
        return new Unready();
    }
}
