package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    public DcMotorEx liftMotorLeft;

    public DcMotorEx liftMotorRight;

    public int liftTargetPosition;

    public  Lift(HardwareMap hardwareMap, int target){
        liftMotorLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftMotorRight = hardwareMap.get(DcMotorEx.class, "liftRight");
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftTargetPosition = target;
    }

   /* public void setLiftPower(double power){
        liftMotorRight.setPower(power);
        liftMotorLeft.setPower(power);

    }

    //public void  setLiftMotorLeftPower(double power) {liftMotorLeft.setPower(power);}

    //public void  setLiftMotorRightPower(double power) {liftMotorRight.setPower(power);}

*/


    public  void setLiftTargetPosition(int target){
        liftTargetPosition = target;
        liftMotorLeft.setTargetPosition(liftTargetPosition);
        liftMotorRight.setTargetPosition(liftTargetPosition);
    }

    public void setLiftTargetPositionZero(){
        liftMotorLeft.setTargetPosition(0);
        liftMotorRight.setTargetPosition(0);
    }
}
