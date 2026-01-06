package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.unstable;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.*;

@Disabled
@Autonomous(name="Super Serious Autonomous")
public class SuperSeriousAutonomous extends LinearOpMode {

    public void runOpMode() {
        // Init code here
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);
        Spintake spintake = new Spintake(hardwareMap);

        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-40,54, 0);
        MecanumDrive rrDrive = new MecanumDrive(hardwareMap, initialPose);

        // very simple spline
        TrajectoryActionBuilder start = rrDrive.actionBuilder(initialPose)
                        .splineTo(new Vector2d(0, 0), 3 * Math.PI / 2,
                                new TranslationalVelConstraint(30),
                                new ProfileAccelConstraint(-40, 50))
                        .splineTo(new Vector2d(60, 0), 0);


        waitForStart();
        // Do Stuff code here

        Actions.runBlocking(start.build());
    }
}
