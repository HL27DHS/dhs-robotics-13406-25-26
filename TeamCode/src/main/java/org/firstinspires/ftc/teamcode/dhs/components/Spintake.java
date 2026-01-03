package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Spintake {
    public DcMotor spintakeMotor;
    public Servo sortServo;

    // CHUTE OPEN: 0.2
    // GATE CLOSED: 0.1
    // CHUTE CLOSED: 0

    public final double sortOpenPos = 0.15;
    public final double sortClosePos = 0;

    public boolean sortOpen;

    public Spintake(HardwareMap hardwareMap) {
        spintakeMotor = hardwareMap.get(DcMotor.class, "spintake");
        sortServo = hardwareMap.get(Servo.class, "sorter");

        // Reverse motors as needed
        spintakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        spintakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        closeSort();
    }

    public void setSpintakePower(double power) {
        spintakeMotor.setPower(power);
    }

    public class StartSpintake implements Action {
        double power;

        public StartSpintake(double power) {
            this.power = power;
        }

        public boolean run(TelemetryPacket packet) {
            setSpintakePower(power);
            return false;
        }
    }
    public Action getStartSpintakeAction(double power) {
        return new StartSpintake(power);
    }

    public class StopSpintake implements Action {
        public boolean run(TelemetryPacket packet) {
            setSpintakePower(0);
            return false;
        }
    }
    public Action getStopSpintakeAction() {
        return new StopSpintake();
    }

    public class CloseSort implements Action {
        public boolean run(@NonNull TelemetryPacket packet) {
            closeSort();
            return false;
        }
    }
    public Action getCloseSortAction() {
        return new CloseSort();
    }

    public void closeSort() {
        sortServo.setPosition(sortClosePos);
        sortOpen = false;
    }

    public class OpenSort implements Action {
        public boolean run(TelemetryPacket packet) {
            openSort();
            return false;
        }
    }
    public Action getOpenSortAction() {
        return new OpenSort();
    }

    public void openSort() {
        sortServo.setPosition(sortOpenPos);
        sortOpen = true;
    }

    public class ToggleSort implements Action {
        public boolean run(TelemetryPacket packet) {
            toggleSort();
            return false;
        }
    }
    public Action getToggleSortAction() {
        return new ToggleSort();
    }

    public void toggleSort() {
        if (sortOpen)
            closeSort();
        else
            openSort();
    }
}
