package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

/**
 * Program you can use to collect data on appropriate flywheel velocities for different
 * distances from the depot.
 */
@TeleOp(name="Fire Velocity Test",group="B - Testing Programs")
public class FireVelocityTest extends OpMode {
    Bot bot;

    SmartController controller1;

    int step = 10;
    int velocity = 0;

    public void init() {
        bot = new Bot(hardwareMap, Alliance.RED, new Pose2d(0,0,0));

        controller1 = new SmartController();
    }

    public void loop() {
        controller1.think(gamepad1);

        // Spintake (A)
        if (controller1.a.isPressed())
            bot.spintake.setSpintakePower(1);
        else
            bot.spintake.setSpintakePower(0);

        // Cycler (Right Trigger)
        if (controller1.rightTrigger.getValue() >= 0.5)
            bot.launcher.setCyclePower(1);
        else
            bot.launcher.setCyclePower(0);

        // Velocity Step Control (X/Y)
        if (controller1.y.justPressed())
            step *= 10;

        if (controller1.x.justPressed())
            step /= 10;

        // Velocity Control (DPad Up/Down)
        if (controller1.dpadUp.justPressed())
            velocity += step;

        if (controller1.dpadDown.justPressed())
            velocity -= step;

        // Flywheel (Left Trigger)
        if (controller1.leftTrigger.getValue() >= 0.5)
            bot.launcher.setFlywheelVelocity(velocity);
        else
            bot.launcher.setFlywheelVelocity(0);

        // Telemetry
        telemetry.addData("current distance",bot.getDistanceFromDepot());
        telemetry.addData("launch velocity ",velocity);
        telemetry.addData("real velocity",bot.launcher.getFlywheelVelocity());
        telemetry.addData("launch velocity step",step);
        telemetry.addLine();
        telemetry.addLine("A - Spintake");
        telemetry.addLine("Right Trigger - Cycler");
        telemetry.addLine("Left Trigger - Ready Flywheel");
        telemetry.addLine("DPad Up/Down - Change Launch Velocity by Step");
        telemetry.addLine("Y/X - Increase / Decrease Step");
    }
}
