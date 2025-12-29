package org.firstinspires.ftc.teamcode.dhs.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.PrimitiveDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;

@Autonomous(name="Blue Big Triangle Shooty Auto",group="Terrible Terrible Awful")
public class BlueBigShootyAuto extends LinearOpMode {
    PrimitiveDrive drivetrain;
    Launcher launcher;
    Spintake spintake;

    public void runOpMode() {
        // initialize the stuff
        drivetrain = new PrimitiveDrive(hardwareMap);
        launcher = new Launcher(hardwareMap);
        spintake = new Spintake(hardwareMap);

        // Wait for start
        waitForStart();

        // Go back for 1 seconds
        drivetrain.rodDrive(0, 0, -0.8);
        sleep(1000);
        drivetrain.rodDrive(0,0,0);

        // Prepare to shoot first ball
        launcher.setFlywheelPower(1);
        sleep(2000);

        // Spin the cycler for 1/4th of a second to fire the first ball
        launcher.setCyclePower(1);
        sleep(250);
        launcher.setCyclePower(0);

        // Wait two seconds for the flywheel to reach speed
        sleep(2000);

        // Spin the cycler for 1/4th of a second to fire the second ball
        launcher.setCyclePower(1);
        sleep(250);
        launcher.setCyclePower(0);

        // Wait two seconds for the flywheel to reach speed
        sleep(2000);

        // Spin the cycler for 1/4th of a second to fire the third ball
        launcher.setCyclePower(1);
        sleep(250);
        launcher.setCyclePower(0);

        // Stop flywheel, it doesn't need to spin any more.
        launcher.setFlywheelPower(0);

        // Move left for 0.5 seconds to get out of the way
        drivetrain.rodDrive(0,-1,0);
        sleep(500);

        // Stop all motors
        spintake.setSpintakePower(0);
        launcher.setCyclePower(0);
        launcher.setFlywheelPower(0);

        drivetrain.getFlMotor().setPower(0);
        drivetrain.getFrMotor().setPower(0);
        drivetrain.getBlMotor().setPower(0);
        drivetrain.getBrMotor().setPower(0);
    }
}
