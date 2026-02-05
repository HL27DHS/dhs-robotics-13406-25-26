package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    public DcMotorEx liftMotorLeft;

    public DcMotorEx liftMotorRight;

    public  Lift(HardwareMap hardwareMap){
        liftMotorLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftMotorRight = hardwareMap.get(DcMotorEx.class, "liftRight");
    }

    public void setLiftPower(double power){
        liftMotorRight.setPower(power);
        liftMotorLeft.setPower(power);
    }

    public void  setLiftMotorLeftPower(double power) {l}


}
