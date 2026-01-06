package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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

@Autonomous(name="Red Depot Auto",preselectTeleOp="Ready Player Two")
public class RedDepotAuto extends LinearOpMode {
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
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-39.5,58.6, Math.PI / 2);

        // Init code here
        bot = new Bot(hardwareMap, Alliance.RED, initialPose);

        rrDrive = bot.drivetrain.getDrive();

        // first trajectory - move backward to prepare to shoot
        double launchPrep1Heading = Math.toRadians(112);
        Action launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .lineToYLinearHeading(15, launchPrep1Heading)
                .build();

        Vector2d firstRowStartPosition = new Vector2d(-6, 36);
        Action artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-39.5, 15, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(firstRowStartPosition, Math.PI/2),Math.PI/2)
                .lineToYConstantHeading(56, new TranslationalVelConstraint(24))
                .build();

        Action backToShootingPos = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, 50, Math.PI/2))
                .splineToLinearHeading(new Pose2d(-39.5, 15, launchPrep1Heading),Math.PI/3)
                .build();

        launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.65);

        waitForStart();
        // Do Stuff code here

        // Go to firing position while spinning up flywheel
        Actions.runBlocking(new ParallelAction(
                launchTrajectory1,
                bot.launcher.getReadyAction(launchVelocity),
                prepareBalls()
        ));

        // Make sure flywheel is spun up, fire three times, stop flywheel
        Actions.runBlocking(fireThreeBalls());

        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                bot.spintake.getStartSpintakeAction(1),
                bot.launcher.getStartCycleAction(1),
                new ParallelAction(
                        artifactTrajectory1,
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
                        backToShootingPos,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls()
                        ),
                fireThreeBalls()
        ));
    }
}
