package org.firstinspires.ftc.teamcode.dhs.opmodes.autos.noRR;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.dhs.components.PrimitiveDrive;

@Disabled
@Autonomous(name="Red Little Triangle Autonomous",group="Terrible Terrible Awful")
public class RedLittleTriAuto extends LinearOpMode {
    PrimitiveDrive drivetrain;

    public void runOpMode() {
        drivetrain = new PrimitiveDrive(hardwareMap);

        waitForStart();

        // Go right for 0.7 seconds
        drivetrain.rodDrive(0,1,0);
        sleep(700);

        // Stop the bot
        drivetrain.getFlMotor().setPower(0);
        drivetrain.getFrMotor().setPower(0);
        drivetrain.getBlMotor().setPower(0);
        drivetrain.getBrMotor().setPower(0);
    }
}
