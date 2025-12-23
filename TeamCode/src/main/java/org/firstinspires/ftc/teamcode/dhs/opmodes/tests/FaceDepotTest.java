package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;

@TeleOp(name="Face Depot Test")
public class FaceDepotTest extends OpMode {
    Bot bot;

    public void init() {
        bot = new Bot(hardwareMap, Bot.Team.RED);

        bot.setAllMotorsZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void loop() {
        telemetry.addData("Bot Needed Heading",bot.getAngleToFaceDepot(AngleUnit.DEGREES));
    }
}
