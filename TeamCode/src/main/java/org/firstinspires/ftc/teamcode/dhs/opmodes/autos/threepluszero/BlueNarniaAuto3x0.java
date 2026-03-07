package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.threepluszero;

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

@Autonomous(name="Blue Narnia 3+0 Auto",group="C - 3+0 Autos")
public class BlueNarniaAuto3x0 extends LinearOpMode {
    Bot bot;
    MecanumDrive rrDrive;
    AutoUtils utils;

    double intakeY;

    public void runOpMode() {
        // prev initialPose (61.659440168245574, -15.60489324134166, Math.PI)
        // INITIALIZATION
        Pose2d initialPose = new Pose2d(63,-12, Math.PI);

        bot = new Bot(hardwareMap, Alliance.BLUE, initialPose);
        rrDrive = bot.drivetrain.getDrive();

        utils = new AutoUtils(bot);

        utils.launchVelocity = (int) (bot.launcher.getFlywheelMaxVelocity() * 0.75);

        utils.fireTimeMS = 350;
        utils.fireDelayMS = 500;

        // PATHS & BUILDERS
        double launchHeading = bot.getAngleToFaceDepot(AngleUnit.RADIANS);
        Pose2d launchPose = new Pose2d(60, -20, launchHeading);

        telemetry.addData("heading",bot.getAngleToFaceDepot(AngleUnit.RADIANS));
        telemetry.update();

        Action launchTraj1 = rrDrive.actionBuilder(initialPose)
                .splineToLinearHeading(launchPose, 0)
                .build();

        Action leaveTraj = rrDrive.actionBuilder(launchPose)
                .splineToLinearHeading(new Pose2d(59, -50, Math.PI/2), -Math.PI/2)
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

        // get those sweet, succulent leave points
        Actions.runBlocking(leaveTraj);
    }
}
