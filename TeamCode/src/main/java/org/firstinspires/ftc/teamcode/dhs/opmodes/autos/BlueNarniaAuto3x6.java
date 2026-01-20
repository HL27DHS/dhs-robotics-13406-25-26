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

@Autonomous(name="Blue Narnia Auto 3+6",group="A - 3+6 Autos",preselectTeleOp="Ready Player Two")
public class BlueNarniaAuto3x6 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;

    int launchVelocity;

    double fireTimeMS;
    double fireDelayMS;

    double intakeY;

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
                new SleepAction(fireDelayMS / 1000),
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime(), // Second Launch
                new SleepAction(fireDelayMS / 1000), // small buffer in case extra time for rolling needed
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime((fireTimeMS+100)/1000), // Third Launch
                //new SleepAction(fireDelayMS / 1000), // small buffer in case extra time for rolling needed
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
        // INITIALIZATION
        Pose2d initialPose = new Pose2d(61.659440168245574,-15.60489324134166, Math.PI);

        bot = new Bot(hardwareMap, Alliance.BLUE, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.8);

        fireTimeMS = 350;
        fireDelayMS = 500;
        intakeY = -60;

        // PATHS & BUILDERS
        double launchHeading = bot.getAngleToFaceDepot(AngleUnit.RADIANS);
        Pose2d launchPose = new Pose2d(55, -12, launchHeading);

        telemetry.addData("heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        telemetry.update();

        Action launchTraj1 = rrDrive.actionBuilder(initialPose)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Vector2d lastRowStartPosition = new Vector2d(39.3, -30);
        Action artifactGrabTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(lastRowStartPosition, -Math.PI/2), -Math.PI/2)
                .lineToYConstantHeading(intakeY, new TranslationalVelConstraint(24))
                .build();

        Action launchTraj2 = rrDrive.actionBuilder(new Pose2d(36, intakeY, -Math.PI/2))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Vector2d secondRowStartPosition = new Vector2d(16.4, -30);
        Action artifactGrabTraj2 = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(secondRowStartPosition, -Math.PI/2),-Math.PI/2)
                .lineToYConstantHeading(intakeY, new TranslationalVelConstraint(24))
                .build();

        Action launchTraj3 = rrDrive.actionBuilder(new Pose2d(36, intakeY, -Math.PI/2))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Action leaveTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(40, -30, Math.PI/2), -Math.PI/2)
                .build();

        waitForStart();
        // RUNNING CODE

        // Get to launch position, spin up flywheel and ready artifacts
        Actions.runBlocking(
                new ParallelAction(
                        launchTraj1,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(false)
                )
        );

        // Fire the three pre-loaded balls
        Actions.runBlocking(fireThreeBalls(false));

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
                        prepareBalls(true)
                ),
                fireThreeBalls(true)
        ));


        // make your way to the artifacts and pick them up
        Actions.runBlocking(new SequentialAction(
                bot.spintake.getStartSpintakeAction(1),
                bot.launcher.getStartCycleAction(1),
                new ParallelAction(
                        artifactGrabTraj2,
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
                        launchTraj3,
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(true)
                ),
                fireThreeBalls(true)
        ));

        // get those sweet, succulent leave points
        Actions.runBlocking(leaveTraj);
    }
}
