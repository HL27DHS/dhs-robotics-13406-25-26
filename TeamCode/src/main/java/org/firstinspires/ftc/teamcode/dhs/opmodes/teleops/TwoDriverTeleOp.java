package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartUtils;

@TeleOp(name="Ready Player Two",group="Main Programs")
public class TwoDriverTeleOp extends OpMode {
    Drivetrain drivetrain;
    Spintake spintake;
    Launcher launcher;

    SmartController controller1 = new SmartController();
    SmartController controller2 = new SmartController();

    // Reverse motors controlled by controller 1
    double c1ReverseModifier = 1;
    // Reverse motors controlled by controller 2
    double c2ReverseModifier = 1;
    // Slow mode for driving (controller 2)
    double slowModeModifier = 1;

    // to FOD or to not FOD, that is the question
    boolean useFod = false;

    // Disable/enable FOD toggle
    final boolean allowToggleFod = true;

    // The range of the flywheel modifier up/down from the base
    // This is subtracted from full power, so having the modifier not pressed will
    // have it run at this value subtracted from full power (aka. don't set it to something silly like 1)
    final double launchModifierRange = 0.2;

    @Override
    public void init() {
        launcher = new Launcher(hardwareMap);
        spintake = new Spintake(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);

        // Left stick deadzone is 0.2 so you can't accidentally slow down or speed up flywheel
        controller1.leftStick.deadzone = 0.2;
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
        c1ReverseModifier = (controller1.x.isPressed()) ? -1 : 1;
        c2ReverseModifier = (controller2.x.isPressed()) ? -1 : 1;

        // Modifies how fast the flywheel will spin based on left stick position or dpad buttons
        double launchModifier = -controller1.leftStick.getY() * launchModifierRange;

        // Change launchModifier using dpad
        if (controller1.dpadUp.isPressed()) launchModifier += launchModifierRange;
        if (controller1.dpadDown.isPressed()) launchModifier -= launchModifierRange;

        // Clamp launchModifier to be within range
        launchModifier = Math.min(Math.max(launchModifier, -launchModifierRange), launchModifierRange);

        // Set spintake, cycle & flywheel power based on corresponding triggers
        // C2 Left = Spintake, C1 Left = Cycle, C1 Right = Flywheel
        double spintakePower = (controller2.leftTrigger.getValue() > 0.5) ? 1 : 0;
        double cyclePower = (controller1.rightTrigger.getValue() > 0.5) ? 1 : 0;
        double launchPower = (controller1.leftTrigger.getValue() > 0.5) ? (1 - launchModifierRange) + launchModifier : 0;

        spintake.setSpintakePower(spintakePower * c2ReverseModifier);
        spintake.setCyclePower(cyclePower * c1ReverseModifier);
        launcher.setFlywheelPower(launchPower * c1ReverseModifier);

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

        // If both DPad Up and DPad Left are pressed, toggle FOD
        // Uses SmartUtils.combo to debounce buttons so that the combo is doable
        if (SmartUtils.combo(controller2.dpadUp, controller2.dpadLeft).justPressed() && allowToggleFod) {
            useFod = !useFod;
        }

        // Do Robot-Oriented or Field-Oriented Drive
        if (useFod)
            drivetrain.fodDrive(
                    controller2.rightStick.getX(),
                    controller2.leftStick.getX() * slowModeModifier,
                    -controller2.leftStick.getY() * slowModeModifier
            );
        else
            drivetrain.rodDrive(
                    controller2.rightStick.getX(),
                    controller2.leftStick.getX() * slowModeModifier,
                    -controller2.leftStick.getY() * slowModeModifier
            );
    }
}
