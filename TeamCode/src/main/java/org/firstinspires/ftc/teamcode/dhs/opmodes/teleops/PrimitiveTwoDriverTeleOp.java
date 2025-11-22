package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.PrimitiveDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

/*
    This TeleOp is based off of TwoDriverTeleOp but is missing many of the features intentionally.
     - The sort servo is not included
     - The flywheel modifier functionality is not included
     - FOD and its toggle are not included
     - Does not use SmartController
 */
@TeleOp(name="Primitive Drive (the sky is falling)",group="Main Programs")
public class PrimitiveTwoDriverTeleOp extends OpMode {
    PrimitiveDrive drivetrain;
    Spintake spintake;
    Launcher launcher;

    // Reverse motors controlled by controller 1
    double c1ReverseModifier = 1;
    // Reverse motors controlled by controller 2
    double c2ReverseModifier = 1;
    // Slow mode for driving (controller 2)
    double slowModeModifier = 1;

    @Override
    public void init() {
        launcher = new Launcher(hardwareMap);
        spintake = new Spintake(hardwareMap);
        drivetrain = new PrimitiveDrive(hardwareMap);
    }

    @Override
    public void loop() {
        // If Y pressed, reset FOD angle
        if (gamepad2.y) {
            drivetrain.resetImuOffset();
        }

        // If X is pressed, reverse spintake & flywheel (this comes in handy more than you'd think)
        c1ReverseModifier = (gamepad1.x) ? -1 : 1;
        c2ReverseModifier = (gamepad2.x) ? -1 : 1;

        // Set spintake, cycle & flywheel power based on corresponding triggers
        // C2 Left = Spintake, C1 Left = Cycle, C1 Right = Flywheel
        double spintakePower = (gamepad2.left_trigger > 0.5) ? 1 : 0;
        double cyclePower = (gamepad1.right_trigger > 0.5) ? 1 : 0;
        double launchPower = (gamepad1.left_trigger > 0.5) ? 1 : 0;

        spintake.setSpintakePower(spintakePower * c2ReverseModifier);
        spintake.setCyclePower(cyclePower * c1ReverseModifier);
        launcher.setFlywheelPower(launchPower * c1ReverseModifier);

        // Manage the Slow Mode modifier (does not affect turning, just driving)
        // If one of the bumpers is pressed, the bot will move at roughly 2/3 of normal speed
        // If both are pressed, the bot will move at roughly 1/3 of normal speed
        slowModeModifier = 1;
        if (gamepad2.right_bumper) slowModeModifier -= 0.3;
        if (gamepad2.left_bumper) slowModeModifier -= 0.3;

        drivetrain.rodDrive(
                gamepad2.right_stick_x,
                gamepad2.left_stick_x * slowModeModifier,
                -gamepad2.left_stick_y * slowModeModifier
        );
    }
}
