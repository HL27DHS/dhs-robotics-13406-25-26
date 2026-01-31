package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.unstable;

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

@Autonomous(name="UNSTABLE Red Depot Auto 3+3",group="D - Unstable Autos",preselectTeleOp="Ready Player Two")
public class UnstableRedDepotAuto3x3 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    AutoUtils utils;

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(39.5,58.6, Math.PI / 2);

        // Init code here
        bot = new Bot(hardwareMap, Alliance.RED, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        utils = new AutoUtils(bot);

        utils.launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.63);
        utils.fireDelayMS += 100;

        // first trajectory - move backward to prepare to shoot
        Vector2d launchPos = new Vector2d(-15, 20);
        double launchPrep1Heading = bot.getAngleToFaceDepotAtPos(AngleUnit.RADIANS, launchPos);
        Action launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading), 0)
                .build();

        Vector2d firstRowStartPosition = new Vector2d(-8, 30);
        Action artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-15, -15, launchPrep1Heading))
                .setTangent(-3 * Math.PI / 2)
                .splineToLinearHeading(new Pose2d(firstRowStartPosition, Math.PI/2),Math.PI/2)
                .lineToYConstantHeading(56, new TranslationalVelConstraint(16))
                .build();

        Action backToShootingPos = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, 50, Math.PI/2))
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading),-Math.PI/3)
                .build();

        Action waiterWaiterMoreLeavePointsPlease = rrDrive.actionBuilder(new Pose2d(launchPos, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(-10, 45, Math.PI), 0)
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
                bot.launcher.getStartCycleAction(0.5),
                new ParallelAction(
                        artifactTrajectory1,
                        new SequentialAction(
                                bot.colorSensor.getWaitForArtifactAction()
                                //bot.launcher.getStopCycleAction()
                        )
                ),
                new SleepAction(0.5),
                bot.launcher.getStopCycleAction(),
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
