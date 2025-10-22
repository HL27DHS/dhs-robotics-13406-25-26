package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Spintake {
    public DcMotor spintakeMotor;

    public Spintake(HardwareMap hardwareMap) {
        spintakeMotor = hardwareMap.get(DcMotor.class, "spintake");
    }

    public void setSpintakePower(double power) {
        spintakeMotor.setPower(power);
    }
}
