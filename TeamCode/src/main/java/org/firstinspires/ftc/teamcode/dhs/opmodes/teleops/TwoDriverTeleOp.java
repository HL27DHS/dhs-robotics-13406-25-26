package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;

@TeleOp(name="Ready Player Two",group="Main Programs")
public class TwoDriverTeleOp extends OpMode {
    Drivetrain drivetrain;
    Spintake spintake;
    Launcher launcher;

    SmartController controller1 = new SmartController();
    SmartController controller2 = new SmartController();

    double reverseModifier = 1;
    double slowModeModifier = 1;

    @Override
    public void init() {
        launcher = new Launcher(hardwareMap);
        spintake = new Spintake(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
    }

    @Override
    public void loop() {
        // update SmartControllers
        controller1.think(gamepad1);
        controller2.think(gamepad2);

        // If Y pressed, reset FOD angle
        if (controller2.y.justPressed()) {
            drivetrain.resetImuOffset();
        }

        // If X is pressed, reverse spintake & flywheel (this comes in handy more than you'd think)
        reverseModifier = (controller1.x.isPressed()) ? -1 : 1;

        // Set spintake & flywheel power based on corresponding triggers
        // Left = Spintake, Right = Flywheel
        spintake.setSpintakePower(controller1.leftTrigger.getValue() * reverseModifier);
        launcher.setFlywheelPower(controller1.rightTrigger.getValue() * reverseModifier);

        // If B is pressed, open the sort chute, if it's not, close it
        // Holding B will keep the sort chute open, letting go will close the sort chute
        if (controller1.b.isPressed())
            spintake.openSort();
        else
            spintake.closeSort();

        // Manage the Slow Mode modifier (does not affect turning, just driving)
        // If one of the bumpers is pressed, the bot will move at roughly 2/3 of normal speed
        // If both are pressed, the bot will move at roughly 1/3 of normal speed
        slowModeModifier = 1;
        if (controller2.rightBumper.isPressed()) slowModeModifier -= 0.3;
        if (controller2.leftBumper.isPressed()) slowModeModifier -= 0.3;

        // Do Robot-Oriented Drive
        drivetrain.rodDrive(
                gamepad2.right_stick_x,
                gamepad2.left_stick_x*slowModeModifier,
                gamepad2.left_stick_y *slowModeModifier
        );
    }
}
