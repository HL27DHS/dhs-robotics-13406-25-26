package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;
import org.firstinspires.ftc.teamcode.dhs.utils.CanvasUtils;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Advanced Auto Aim Test",group="B - Testing Programs")
public class AdvancedAutoAimTest extends OpMode {
    Bot bot;
    FtcDashboard dash;
    SmartController controller;

    public void init() {
        bot = new Bot(hardwareMap, Alliance.RED, new Pose2d(0,0,0));
        dash = FtcDashboard.getInstance();
        controller = new SmartController();
    }

    public void loop() {
        //---------------
        // BOT CONTROLS
        //---------------

        controller.think(gamepad1);
        PoseVelocity2d vel = bot.drivetrain.getDrive().updatePoseEstimate();

        // Reset Bot Pose (X)
        if (controller.x.isPressed())
            bot.drivetrain.getDrive().localizer.setPose(new Pose2d(0,0,0));

        // Swap Alliance (Bumpers)
        if (controller.rightBumper.justPressed() || controller.leftBumper.justPressed())
            bot.swapAlliance();

        // Turn (Right Stick)
        double turn = controller.rightStick.getX();

        // Auto aim (A - hold)
        if (controller.a.isPressed())
            turn = bot.getTurnValueToFaceDepot();

        // Drive (Left Stick)
        bot.drivetrain.rodDrive(
                turn,
                controller.leftStick.getX(),
                -controller.leftStick.getY()
        );

        //------------
        // TELEMETRY
        //------------

        // Show non-dash telemetry warning
        telemetry.addLine("To use this OpMode, open FTC Dashboard");
        telemetry.addLine();
        telemetry.addLine("Left Stick - Drive");
        telemetry.addLine("Right Stick - Steer");
        telemetry.addLine("A (hold) - Auto Aim");
        telemetry.addLine("X (press) - Reset Pose");
        telemetry.addLine("Bumpers (press) - Swap Alliance");
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();

        // Add data stuff
        packet.put("current heading",bot.drivetrain.getRealYaw(AngleUnit.RADIANS));
        packet.put("current pos",bot.drivetrain.getDrive().localizer.getPose().position);
        packet.put("auto aim heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        packet.put("auto aim steer val",bot.getTurnValueToFaceDepot());
        packet.put("auto aim error (deg)",Math.toDegrees(bot.drivetrain.getRealYaw(AngleUnit.RADIANS)
                - bot.getAngleToFaceDepot(AngleUnit.RADIANS)));
        packet.put("current alliance",bot.getAlliance());

        Canvas canvas = packet.fieldOverlay();

        // Draw robot on field
        Pose2d botPos = bot.drivetrain.getDrive().localizer.getPose();
        canvas.setStroke("green");
        CanvasUtils.drawBot(canvas, botPos.position);
        // Draw robot aim line
        CanvasUtils.drawAimLine(canvas, botPos.position, botPos.heading.toDouble());

        // Draw desired aim line on field
        canvas.setStroke("blue");
        CanvasUtils.drawAimLine(canvas, botPos.position, bot.getAutoAimAngleToFaceDepot(AngleUnit.RADIANS));
        // Draw desired aim pos
        Pose2d depotPos = bot.getAutoAimDepotPosition();
        CanvasUtils.drawAimPoint(canvas, depotPos.position);

        dash.sendTelemetryPacket(packet);
    }
}