package org.firstinspires.ftc.teamcode.dhs.autos;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.*;

import javax.crypto.ExemptionMechanismException;

@Autonomous(name="Super Serious Autonomous")
public class SuperSeriousAutonomous extends LinearOpMode {


    public void runOpMode() {
        // Init code here
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);
        Spintake spintake = new Spintake(hardwareMap);

        Pose2d initialPose = new Pose2d(0,0, 0);
        MecanumDrive rrDrive = new MecanumDrive(hardwareMap, initialPose);

        // very simple spline
        TrajectoryActionBuilder start = rrDrive.actionBuilder(initialPose)
                        .splineTo(new Vector2d(48, 48), Math.PI / 2);

        waitForStart();
        // Do Stuff code here

        Actions.runBlocking(start.build());
    }
}
