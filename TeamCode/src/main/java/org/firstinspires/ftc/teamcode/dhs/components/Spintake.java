package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Spintake {
    public DcMotor spintakeMotor;
    public Servo sortServo;

    // TODO: Figure out servo sorter positions
    public final double sortOpenPos = 0;
    public final double sortClosePos = 0;

    public boolean sortOpen;

    public Spintake(HardwareMap hardwareMap) {
        spintakeMotor = hardwareMap.get(DcMotor.class, "spintake");
        sortServo = hardwareMap.get(Servo.class, "sorter");

        closeSort();
    }

    public void setSpintakePower(double power) {
        spintakeMotor.setPower(power);
    }

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
