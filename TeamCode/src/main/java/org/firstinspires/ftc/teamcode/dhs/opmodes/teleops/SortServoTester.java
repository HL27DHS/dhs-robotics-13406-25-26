package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Sort Servo Tester", group="Testing Programs")
public class SortServoTester extends OpMode {
    Drivetrain drivetrain;
    Spintake spintake;
    Launcher launcher;

    SmartController controller1 = new SmartController();
    SmartController controller2 = new SmartController();

    double rateOfChange = 0.05;

    public void init() {
        launcher = new Launcher(hardwareMap);
        spintake = new Spintake(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
    }

    public void loop() {
        // update SmartControllers
        controller1.think(gamepad1);
        controller2.think(gamepad2);

        if (controller1.dpadUp.isPressed())
            spintake.sortServo.setPosition(spintake.sortServo.getPosition() + rateOfChange);
        if (controller1.dpadDown.isPressed())
            spintake.sortServo.setPosition(spintake.sortServo.getPosition() - rateOfChange);

        telemetry.addLine("DPad Up: increase servo position");
        telemetry.addLine("DPad Down: decrease servo position");
        telemetry.addLine("//////////////////////////////////");
        telemetry.addData("Rate of change: ",rateOfChange);
        telemetry.addData("Servo position: ",spintake.sortServo.getPosition());
    }
}