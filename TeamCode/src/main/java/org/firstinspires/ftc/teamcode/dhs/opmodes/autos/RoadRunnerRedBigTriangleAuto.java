package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;

@Autonomous(name="RR Red Big Tri Auto")
public class RoadRunnerRedBigTriangleAuto extends LinearOpMode {

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-39.5,58.6, Math.PI / 2);

        // Init code here
        Bot bot = new Bot(hardwareMap, Alliance.RED, initialPose);

        MecanumDrive rrDrive = bot.drivetrain.getDrive();

        // first trajectory - move backward to prepare to shoot
        double launchPrep1Heading = Math.toRadians(112);
        TrajectoryActionBuilder launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .lineToYLinearHeading(15, launchPrep1Heading);

        Vector2d firstRowStartPosition = new Vector2d(-12, 36);
        TrajectoryActionBuilder artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-39.5, 15, launchPrep1Heading))
                .stopAndAdd(bot.spintake.getBlockSortAction()) // TODO: move this into an action variable?
                .stopAndAdd(bot.spintake.getStartSpintakeAction(1))
                .stopAndAdd(bot.launcher.getStartCycleAction(0.5))
                .splineToLinearHeading(new Pose2d(firstRowStartPosition, Math.PI/2),Math.PI/2)
                .lineToYConstantHeading(56, new TranslationalVelConstraint(24))
                .afterTime(0.5, bot.spintake.getStopSpintakeAction())
                .afterTime(0.5, bot.launcher.getStopCycleAction())
                .afterTime(0.5, bot.spintake.getCloseSortAction());

        TrajectoryActionBuilder grabArtifacts1 = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition, Math.PI/2))
                .lineToYConstantHeading(50, new TranslationalVelConstraint(20));

        TrajectoryActionBuilder backToShootingPos = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, 50, Math.PI/2))
                .splineToLinearHeading(new Pose2d(-39.5, 15, launchPrep1Heading),Math.PI/3);

        int launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.8);
        double fireTime = 100;

        // action to fire three balls
        Action prepareBalls = new SequentialAction(
                bot.spintake.getBlockSortAction(),
                bot.launcher.getStartCycleAction(0.5),
                new SleepAction(0.25),
                bot.launcher.getStopCycleAction(),
                bot.spintake.getCloseSortAction()
        );

        // Spin up and fire three balls
        Action fireThreeBalls = new SequentialAction(
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchWithTimeAction(fireTime), // First Launch
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls
                ),
                bot.launcher.getLaunchWithTimeAction(fireTime), // Second Launch
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls
                ),
                bot.launcher.getLaunchWithTimeAction(fireTime), // Third Launch
                bot.launcher.getUnreadyAction()
        );

        waitForStart();
        // Do Stuff code here

        // Go to firing position while spinning up flywheel
        Actions.runBlocking(new ParallelAction(
                launchTrajectory1.build(),
                bot.launcher.getReadyAction(launchVelocity),
                prepareBalls
        ));

        // Make sure flywheel is spun up, fire three times, stop flywheel
        Actions.runBlocking(fireThreeBalls);

        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                artifactTrajectory1.build()
        ));

        // go back to shooting pos and fire
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        backToShootingPos.build(),
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls
                        ),
                fireThreeBalls
        ));
    }
}
