package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;
import org.firstinspires.ftc.teamcode.dhs.utils.AutoUtils;

@Autonomous(name="Red Narnia Auto 3+3",group="A - 3+3 Autos",preselectTeleOp="Ready Player Two")
public class RedNarniaAuto3x3 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    AutoUtils utils;

    public void runOpMode() {
        // INITIALIZATION
        Pose2d initialPose = new Pose2d(63,12, Math.PI);

        bot = new Bot(hardwareMap, Alliance.RED, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        utils = new AutoUtils(bot);

        utils.launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.8);

        utils.fireTimeMS = 400;
        utils.fireDelayMS = 0;

        // PATHS & BUILDERS
        double launchHeading = bot.getAngleToFaceDepot(AngleUnit.RADIANS);
        Pose2d launchPose = new Pose2d(60, 15, launchHeading);

        telemetry.addData("heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        telemetry.update();

        Action launchTraj1 = rrDrive.actionBuilder(initialPose)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Vector2d lastRowStartPosition = new Vector2d(36, 36);
        Action artifactGrabTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(lastRowStartPosition, Math.PI/2), Math.PI/2)
                .lineToYConstantHeading(56, new TranslationalVelConstraint(24))
                .build();

        Action launchTraj2 = rrDrive.actionBuilder(new Pose2d(36, 56, Math.PI/2))
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(launchPose, Math.PI)
                .build();

        Action leaveTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(40, 30, -Math.PI/2), -Math.PI/2)
                .build();

        waitForStart();
        // RUNNING CODE

        // Get to launch position, spin up flywheel and ready artifacts
        Actions.runBlocking(
                new ParallelAction(
                        launchTraj1,
                        bot.launcher.getReadyAction(utils.launchVelocity),
                        utils.prepareBalls(false)
                )
        );

        // Fire the three pre-loaded balls
        Actions.runBlocking(utils.fireThreeBalls(false));



        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                bot.spintake.getStartSpintakeAction(1),
                bot.launcher.getStartCycleAction(1),
                new ParallelAction(
                        artifactGrabTraj,
                        new SequentialAction(
                                bot.colorSensor.getWaitForArtifactAction(),
                                bot.launcher.getStopCycleAction()
                        )
                ),
                bot.spintake.getStopSpintakeAction()
        ));

        // go back to shooting pos and fire
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        launchTraj2,
                        bot.launcher.getReadyAction(utils.launchVelocity),
                        utils.prepareBalls(true)
                ),
                utils.fireThreeBalls(true)
        ));

        // get those sweet, succulent leave points
        Actions.runBlocking(leaveTraj);
    }
}
