package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.leave;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;

@Disabled
@Autonomous(name="Red Narnia Leave Points", group="B - Leave Pts", preselectTeleOp="Ready Player Two")
public class RedNarniaLeavePts extends LinearOpMode {
    Bot bot;

    public void runOpMode() {
        // TODO: Figure out starting position for this autonomous
        Pose2d initialPos = new Pose2d(0,0,0);
        double leaveY = 36;

        bot = new Bot(hardwareMap, Alliance.RED, initialPos);
        MecanumDrive rrDrive = bot.drivetrain.getDrive();

        Action leaveTrajectory = rrDrive.actionBuilder(initialPos)
                .lineToYConstantHeading(leaveY)
                .build();

        waitForStart();

        Actions.runBlocking(leaveTrajectory);
    }
}
