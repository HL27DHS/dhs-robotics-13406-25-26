package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;

@Autonomous(name="Blue Depot Leave Points", group="B - Leave Pts", preselectTeleOp="Ready Player Two")
public class BlueDepotLeavePts extends LinearOpMode {
    Bot bot;

    public void runOpMode() {
        // TODO: Figure out starting position for this autonomous
        Pose2d initialPos = new Pose2d(0,0,0);
        double leaveX = -36;

        bot = new Bot(hardwareMap, Alliance.BLUE, initialPos);
        MecanumDrive rrDrive = bot.drivetrain.getDrive();

        Action leaveTrajectory = rrDrive.actionBuilder(initialPos)
                .lineToXConstantHeading(leaveX)
                .build();

        waitForStart();

        Actions.runBlocking(leaveTrajectory);
    }
}
