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

@Autonomous(name="Blue Depot Auto 3+6",group="A - 3+6 Autos",preselectTeleOp="Ready Player Two")
public class BlueDepotAuto3x6 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    int launchVelocity;
    double fireTimeMS;

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action launchWithTime() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                new SleepAction(fireTimeMS / 1000),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action launchWithTime(double seconds) {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                new SleepAction(seconds),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action launchWithSensor() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactLeaveAction(),
                new SleepAction(0.1),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action fireThreeBalls(boolean spintake) {
        return new SequentialAction(
                bot.launcher.getReadyAction(launchVelocity),
                launchWithTime(), // First Launch
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime(), // Second Launch
                new SleepAction(0.5), // small buffer in case extra time for rolling needed
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime((fireTimeMS+100)/1000), // Third Launch
                new SleepAction(0.5), // small buffer in case extra time for rolling needed
                bot.launcher.getUnreadyAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action prepareBalls(boolean spintake) {
        // If there's already a ball present, don't even do anything
        if (bot.colorSensor.isArtifactInSensor())
            return new SequentialAction();

        if (spintake)
            return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.spintake.getStartSpintakeAction(1),
                bot.colorSensor.getWaitForArtifactAction(),
                bot.spintake.getStopSpintakeAction(),
                bot.launcher.getStopCycleAction()
            );

        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactAction(),
                bot.launcher.getStopCycleAction()
        );
    }

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-39.5,-58.6, -Math.PI / 2);

        // Init code here
        bot = new Bot(hardwareMap, Alliance.BLUE, initialPose);

        rrDrive = bot.drivetrain.getDrive();

        double intakeY = -56;

        // first trajectory - move backward to prepare to shoot
        Vector2d launchPos = new Vector2d(-15, -15);
        double launchPrep1Heading = bot.getAngleToFaceDepotAtPos(AngleUnit.RADIANS, launchPos);
        Action launchTrajectory1 = rrDrive.actionBuilder(initialPose)
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading), 0)
                .build();

        Vector2d firstRowStartPosition = new Vector2d(-12, -36);
        Action artifactTrajectory1 = rrDrive.actionBuilder(new Pose2d(-15, -15, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(firstRowStartPosition, -Math.PI/2),-Math.PI/2)
                .lineToYConstantHeading(intakeY, new TranslationalVelConstraint(24))
                .build();




        Action backToShootingPos1 = rrDrive.actionBuilder(new Pose2d(firstRowStartPosition.x, intakeY, -Math.PI/2))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading),-Math.PI/3)
                .build();

        Vector2d secondRowStartPosition = new Vector2d(14, -36);
        Action artifactTrajectory2 = rrDrive.actionBuilder(new Pose2d(-15, -15, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(secondRowStartPosition, -Math.PI/2),-Math.PI/2)
                .lineToYConstantHeading(intakeY, new TranslationalVelConstraint(24))
                .build();

        Action backToShootingPos2 = rrDrive.actionBuilder(new Pose2d(secondRowStartPosition.x, intakeY, -Math.PI/2))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(launchPos, launchPrep1Heading),-Math.PI/3)
                .build();

        Action waiterWaiterMoreLeavePointsPlease = rrDrive.actionBuilder(new Pose2d(launchPos, launchPrep1Heading))
                .splineToLinearHeading(new Pose2d(-10, -45, Math.PI), 0)
                .build();

        launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.62);
        fireTimeMS = 500;

        waitForStart();
        // Do Stuff code here

        // Go to firing position while spinning up flywheel
        Actions.runBlocking(new ParallelAction(
                launchTrajectory1,
                bot.launcher.getReadyAction(launchVelocity),
                prepareBalls(false)
        ));

        // Make sure flywheel is spun up, fire three times, stop flywheel
        Actions.runBlocking(fireThreeBalls(false));

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
                        backToShootingPos1,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(true)
                        ),
                fireThreeBalls(true)
        ));

        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                bot.spintake.getStartSpintakeAction(1),
                bot.launcher.getStartCycleAction(1),
                new ParallelAction(
                        artifactTrajectory2,
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
                        backToShootingPos2,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(true)
                ),
                fireThreeBalls(true)
        ));

        // get those sweet, succulent leave points
        Actions.runBlocking(
                waiterWaiterMoreLeavePointsPlease
        );
    }
}
