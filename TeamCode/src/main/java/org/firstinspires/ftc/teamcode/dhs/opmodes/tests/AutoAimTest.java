package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.roadrunner.Pose2d;
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

    public void init() {
        bot = new Bot(hardwareMap, Alliance.RED, new Pose2d(0,0,0));
        controller1 = new SmartController();
    }

    public void loop() {
        controller1.think(gamepad1);

        // Auto Aim (Right Stick Button)
        double turn = controller1.rightStick.getX();

        if (controller1.rightStickButton.isPressed())
            turn = bot.getTurnValueToFaceDepot();

        // Drive (Left/Right Stick)
        bot.drivetrain.rodDrive(
                turn,
                controller1.leftStick.getX(),
                -controller1.leftStick.getY()
        );

        telemetry.addData("current heading",bot.drivetrain.getDrive().localizer.getPose().heading);
        telemetry.addData("auto aim heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        telemetry.addData("auto aim steer val",bot.getTurnValueToFaceDepot());
        telemetry.addLine();
        telemetry.addLine("Use left/right stick to drive");
        telemetry.addLine("Hold right stick button to use auto aim");

        telemetry.update();
    }
}
