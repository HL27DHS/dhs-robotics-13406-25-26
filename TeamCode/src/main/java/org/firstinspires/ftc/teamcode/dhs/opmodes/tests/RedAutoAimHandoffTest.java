package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;

@Autonomous(name="Auto Aim Handoff Test (RED)", group="TestAuto", preselectTeleOp="AutoAimTest")
public class RedAutoAimHandoffTest extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    public void runOpMode() {
        // Define poses used in autonomous
        Pose2d initialPose = new Pose2d(63,12, Math.PI);
        Pose2d endPose = new Pose2d(30, 12, Math.PI);

        bot = new Bot(hardwareMap, Alliance.RED, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        // Define drive actions used in the autonomous
        Action moveForward = rrDrive.actionBuilder(initialPose)
                .strafeTo(endPose.position)
                .build();

        waitForStart();

        // Actually run the actions yay
        Actions.runBlocking(moveForward);
    }
}
