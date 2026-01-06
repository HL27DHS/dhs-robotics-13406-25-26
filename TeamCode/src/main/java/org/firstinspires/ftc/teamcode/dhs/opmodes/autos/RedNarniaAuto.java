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

@Autonomous(name="Red Narnia Auto",preselectTeleOp="Ready Player Two")
public class RedNarniaAuto extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    int launchVelocity;

    public Action launchWithSensor() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactLeaveAction(),
                bot.launcher.getStopCycleAction()
        );
    }

    public Action fireThreeBalls() {
        return new SequentialAction(
                bot.launcher.getReadyAction(launchVelocity),
                launchWithSensor(), // First Launch
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls()
                ),
                launchWithSensor(), // Second Launch
                new SleepAction(0.5), // small buffer in case extra time for rolling needed
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls()
                ),
                launchWithSensor(), // Third Launch
                new SleepAction(0.5), // small buffer in case extra time for rolling needed
                bot.launcher.getUnreadyAction()
        );
    }

    public Action prepareBalls() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactAction(),
                bot.launcher.getStopCycleAction()
        );
    }

    public void runOpMode() {
        // INITIALIZATION
        Pose2d initialPose = new Pose2d(-39.5,58.6, Math.PI / 2);

        bot = new Bot(hardwareMap, Alliance.RED, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.8);

        // PATHS & BUILDERS
        double launchHeading = bot.getAngleToFaceDepot(AngleUnit.RADIANS);
        Pose2d launchPose = new Pose2d(60, 12, launchHeading);

        Action launchTraj1 = rrDrive.actionBuilder(initialPose)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Vector2d lastRowStartPosition = new Vector2d(36, 36);
        Action artifactGrabTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(lastRowStartPosition, Math.PI/2), Math.PI/2)
                .lineToYConstantHeading(56, new TranslationalVelConstraint(24))
                .build();

        Action launchTraj2 = rrDrive.actionBuilder(new Pose2d(36, 56, Math.PI/2))
                .splineToLinearHeading(launchPose, Math.PI)
                .build();

        Action leaveTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(36, 12, 0), 0)
                .build();

        waitForStart();
        // RUNNING CODE

        // Get to launch position, spin up flywheel and ready artifacts
        Actions.runBlocking(
                new ParallelAction(
                        launchTraj1,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls()
                )
        );

        // Fire the three pre-loaded balls
        Actions.runBlocking(fireThreeBalls());

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
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls()
                ),
                fireThreeBalls()
        ));

        // get those sweet, succulent leave points
        Actions.runBlocking(leaveTraj);
    }
}
