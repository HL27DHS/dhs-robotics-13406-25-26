package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Auto Aim Test",group="B - Testing Programs")
public class AutoAimTest extends OpMode {
    Bot bot;
    SmartController controller1;

    MultipleTelemetry dashTelem;

    public void init() {
        bot = new Bot(hardwareMap, Alliance.RED, new Pose2d(0,0,0));
        controller1 = new SmartController();

        dashTelem = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void loop() {
        controller1.think(gamepad1);

        // Auto Aim (Right Stick Button)
        double turn = controller1.rightStick.getX();

        PoseVelocity2d vel = bot.drivetrain.getDrive().updatePoseEstimate();

        if (controller1.rightStickButton.isPressed())
            turn = bot.getTurnValueToFaceDepot();

        // Drive (Left/Right Stick)
        bot.drivetrain.rodDrive(
                turn,
                controller1.leftStick.getX(),
                -controller1.leftStick.getY()
        );

        dashTelem.addData("current heading",bot.drivetrain.getYaw(AngleUnit.RADIANS));
        dashTelem.addData("current pos",bot.drivetrain.getDrive().localizer.getPose().position);
        dashTelem.addData("auto aim heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        dashTelem.addData("auto aim steer val",bot.getTurnValueToFaceDepot());
        dashTelem.addLine();
        dashTelem.addLine("Use left/right stick to drive");
        dashTelem.addLine("Hold right stick button to use auto aim");

        dashTelem.update();
    }
}
