package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Spintake {
    public DcMotor spintakeMotor;
    public DcMotor cycleMotor;
    public Servo sortServo;


    public final double sortOpenPos = 1;
    public final double sortClosePos = 0.5;

    public boolean sortOpen;

    public Spintake(HardwareMap hardwareMap) {
        spintakeMotor = hardwareMap.get(DcMotor.class, "spintake");
        cycleMotor = hardwareMap.get(DcMotor.class, "cycle");
        sortServo = hardwareMap.get(Servo.class, "sorter");

        // Reverse motors as needed
        spintakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cycleMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        spintakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cycleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        closeSort();
    }

    public void setSpintakePower(double power) {
        spintakeMotor.setPower(power);
    }
    public void setCyclePower(double power) { cycleMotor.setPower(power); }

    public void closeSort() {
        sortServo.setPosition(sortClosePos);
        sortOpen = false;
    }

    public void openSort() {
        sortServo.setPosition(sortOpenPos);
        sortOpen = true;
    }

    public void toggleSort() {
        if (sortOpen)
            closeSort();
        else
            openSort();
    }
}
