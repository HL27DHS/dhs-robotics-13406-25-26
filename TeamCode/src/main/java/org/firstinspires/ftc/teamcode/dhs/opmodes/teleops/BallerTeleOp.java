package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Ballin'",group="0A - Outreach OpModes")
public class BallerTeleOp extends OpMode {
    Bot bot;

    SmartController driver;
    SmartController manipulator;

    // If the shutdown is acive, don't let the bot do anything
    boolean shutdownActive = false;

    // Whether or not the bot will use Field Oriented Drive
    boolean useFod = false;

    public void init() {
        bot = new Bot(hardwareMap);
        driver = new SmartController();
        manipulator = new SmartController();
    }

    public void loop() {
        // Manage drive controllers
        driver.think(gamepad1);
        manipulator.think(gamepad2);

        // Drive fwd and strafe values
        double driveForward = 0;
        double driveStrafe = 0;


    }
}
