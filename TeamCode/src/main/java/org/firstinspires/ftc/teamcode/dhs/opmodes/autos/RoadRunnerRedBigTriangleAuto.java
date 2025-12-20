package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;

@Autonomous(name="RR Red Big Tri Auto")
public class RoadRunnerRedBigTriangleAuto extends LinearOpMode {

    public void runOpMode() {
        // "somethin' eleven" - James Fonseca 2025
        Pose2d initialPose = new Pose2d(-65.25,36.5, Math.PI / 2);

        // Init code here
        Drivetrain drivetrain = new Drivetrain(hardwareMap, initialPose);
        Launcher launcher = new Launcher(hardwareMap);
        Spintake spintake = new Spintake(hardwareMap);

        MecanumDrive rrDrive = drivetrain.getDrive();

        // first trajectory - move backward to prepare to shoot
        TrajectoryActionBuilder launchPrep1 = rrDrive.actionBuilder(initialPose)
                .lineToYConstantHeading(12);


        waitForStart();
        // Do Stuff code here

        Actions.runBlocking(launchPrep1.build());
    }
}
