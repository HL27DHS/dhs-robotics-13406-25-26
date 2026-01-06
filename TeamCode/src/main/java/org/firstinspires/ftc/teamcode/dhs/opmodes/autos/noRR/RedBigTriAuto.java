package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.noRR;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.dhs.components.PrimitiveDrive;

@Disabled
@Autonomous(name="Red Big Triangle Auto",group="Terrible Terrible Awful")
public class RedBigTriAuto extends LinearOpMode {
    PrimitiveDrive drivetrain;

    public void runOpMode() {
        drivetrain = new PrimitiveDrive(hardwareMap);

        waitForStart();

        // Go forward for 1 second
        drivetrain.rodDrive(0,0,1);
        sleep(1000);

        // Go left for 0.5 seconds
        drivetrain.rodDrive(0,-1,0);
        sleep(500);

        // Stop the bot
        drivetrain.getFlMotor().setPower(0);
        drivetrain.getFrMotor().setPower(0);
        drivetrain.getBlMotor().setPower(0);
        drivetrain.getBrMotor().setPower(0);
    }
}
