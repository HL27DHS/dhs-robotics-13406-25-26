package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

import java.util.concurrent.TimeUnit;

/**
 * Program used to test distance sensor values while firing
 */
@TeleOp(name="Distance Sensor Test",group="B - Testing Programs")
public class DistanceSensorTest extends OpMode {
    @Config
    public static class DistanceSensorTestConf {
        static double percentToDetectThreshold = 0.5;
        static double historyIntervalMS = 150;
    }

    Bot bot;
    SmartController controller1;

    MultipleTelemetry dashTelem;
    ElapsedTime deltaTimer;
    double latestDistance;
    int numChecks = 0;
    int lastCheckPass = 0;

    public void init() {
        bot = new Bot(hardwareMap);
        controller1 = new SmartController();
        dashTelem = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        deltaTimer = new ElapsedTime();
    }

    public void loop() {
        controller1.think(gamepad1);

        // control flywheel with left trigger
        int launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.75);

        if (controller1.leftTrigger.getValue() >= 0.5)
            bot.launcher.setFlywheelVelocity(launchVelocity);
        else
            bot.launcher.setFlywheelVelocity(0);

        // control cycle motor with right trigger
        if (controller1.rightTrigger.getValue() >= 0.5)
            bot.launcher.setCyclePower(1);
        else
            bot.launcher.setCyclePower(0);

        // control intake with A
        if (controller1.a.isPressed())
            bot.spintake.setSpintakePower(1);
        else
            bot.spintake.setSpintakePower(0);

        // handle history & checking
        if (deltaTimer.time(TimeUnit.MILLISECONDS) >= DistanceSensorTestConf.historyIntervalMS) {
            double currentDistance = bot.colorSensor.getDistance(DistanceUnit.CM);

            // Do the check
            if (latestDistance * DistanceSensorTestConf.percentToDetectThreshold <= currentDistance)
                lastCheckPass = numChecks;

            deltaTimer.reset();
            numChecks++;
        }

        dashTelem.addData("distance",bot.colorSensor.getDistance(DistanceUnit.CM));
        dashTelem.addData("current velocity",bot.launcher.getFlywheelVelocity());
        dashTelem.addLine();
        dashTelem.addData("current check",numChecks);
        dashTelem.addData("last check to pass",lastCheckPass);
        dashTelem.addData("needed distance to pass",latestDistance * DistanceSensorTestConf.percentToDetectThreshold);
        dashTelem.addLine();
        dashTelem.addLine("Flywheel - Left Trigger");
        dashTelem.addLine("Cycler - Right Trigger");
        dashTelem.addLine("Intake - A");

        dashTelem.update();
    }
}
