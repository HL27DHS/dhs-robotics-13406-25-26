package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.dhs.components.Lift;
@TeleOp(name="Lift Test",group="B - Testing Programs")
public class LiftTest extends OpMode {

    Lift lift;


    public void init(){
        lift = new Lift(hardwareMap, 0);
    }

    public void loop(){
        if (gamepad1.dpad_up){
            lift.setLiftTargetPosition(lift.liftTargetPosition + 1);

        } else if (gamepad1.dpad_down){
            lift.setLiftTargetPosition(lift.liftTargetPosition - 1);

        }

        telemetry.addData("position", lift.liftTargetPosition);
        telemetry.update();
    }
}
