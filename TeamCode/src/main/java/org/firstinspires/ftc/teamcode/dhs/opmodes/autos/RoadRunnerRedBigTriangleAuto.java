package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.components.Team;

@Autonomous(name="RR Red Big Tri Auto")
public class RoadRunnerRedBigTriangleAuto extends LinearOpMode {

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-39.5,58.6, Math.PI / 2);

        // Init code here
        Bot bot = new Bot(hardwareMap, Team.RED);

        MecanumDrive rrDrive = bot.drivetrain.getDrive();

        // first trajectory - move backward to prepare to shoot
        double launchPrep1Heading = bot.getAngleToFaceDepotAtPos(AngleUnit.DEGREES,new Pose2d(-39.5, 15, 0));
        TrajectoryActionBuilder launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .lineToYLinearHeading(15, launchPrep1Heading);

        Vector2d firstRowStartPosition = new Vector2d(-12, 36);
        TrajectoryActionBuilder artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-39.5, 15, 0))
                .splineToConstantHeading(firstRowStartPosition,Math.PI/2);

        TrajectoryActionBuilder grabArtifacts1 = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition, Math.PI/2))
                .lineToYConstantHeading(50, new TranslationalVelConstraint(20));

        TrajectoryActionBuilder backToShootingPos = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, 50, Math.PI/2))
                .splineToLinearHeading(new Pose2d(-39.5, 15, 0), launchPrep1Heading);

        int launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.8);

        waitForStart();
        // Do Stuff code here

        // Go to firing position while spinning up flywheel
        Actions.runBlocking(new ParallelAction(
                launchTrajectory1.build()/*,
                bot.launcher.getReadyAction(launchVelocity)*/
        ));
        /*
        // Make sure flywheel is spun up, fire three times, stop flywheel
        Actions.runBlocking(new SequentialAction(
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getUnreadyAction()
        ));*/

        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                artifactTrajectory1.build(),
                bot.spintake.getStartSpintakeAction(0.8),
                bot.launcher.getStartCycleAction(0.4),
                grabArtifacts1.build(),
                bot.spintake.getStopSpintakeAction(),
                bot.launcher.getStopCycleAction()
        ));

        // go back to shooting pos and fire
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        backToShootingPos.build()/*,
                        bot.launcher.getReadyAction(launchVelocity)*/
                        )/*,
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getReadyAction(launchVelocity),
                bot.launcher.getLaunchAction(),
                bot.launcher.getUnreadyAction()*/
        ));
    }
}
