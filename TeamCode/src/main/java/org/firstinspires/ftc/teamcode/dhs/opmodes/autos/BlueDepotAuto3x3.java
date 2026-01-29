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

@Autonomous(name="Blue Depot Auto 3+3",group="A - 3+3 Autos",preselectTeleOp="Ready Player Two")
public class BlueDepotAuto3x3 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    AutoUtils utils;

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-39.5,-58.6, -Math.PI / 2);

        // Init code here
        bot = new Bot(hardwareMap, Alliance.BLUE, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        utils.launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.62);
        utils.fireTimeMS = 500;

        // first trajectory - move backward to prepare to shoot
        Vector2d launchPos = new Vector2d(-15, -15);
        double launchPrep1Heading = bot.getAngleToFaceDepotAtPos(AngleUnit.RADIANS, launchPos);
        Action launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading), 0)
                .build();

        Vector2d firstRowStartPosition = new Vector2d(-12, -36);
        Action artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-15, -15, launchPrep1Heading))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(firstRowStartPosition, -Math.PI/2),-Math.PI/2)
                .lineToYConstantHeading(-56, new TranslationalVelConstraint(24))
                .build();

        Action backToShootingPos = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, -50, -Math.PI/2))
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading),-Math.PI/3)
                .build();

        Action waiterWaiterMoreLeavePointsPlease = rrDrive.actionBuilder(new Pose2d(launchPos, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(-10, -45, Math.PI), 0)
                .build();

        waitForStart();
        // Do Stuff code here

        // Go to firing position while spinning up flywheel
        Actions.runBlocking(new ParallelAction(
                launchTrajectory1,
                bot.launcher.getReadyAction(utils.launchVelocity),
                utils.prepareBalls(false)
        ));

        // Make sure flywheel is spun up, fire three times, stop flywheel
        Actions.runBlocking(utils.fireThreeBalls(false));

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
                new SleepAction(0.5),
                bot.spintake.getStopSpintakeAction()
        ));

        // go back to shooting pos and fire
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        backToShootingPos,
                        bot.launcher.getReadyAction(utils.launchVelocity),
                        utils.prepareBalls(true)
                ),
                utils.fireThreeBalls(true)
        ));

        // get those sweet, succulent leave points
        Actions.runBlocking(
                waiterWaiterMoreLeavePointsPlease
        );
    }
}
