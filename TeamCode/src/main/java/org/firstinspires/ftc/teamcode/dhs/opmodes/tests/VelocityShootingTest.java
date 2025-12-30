package org.firstinspires.ftc.teamcode.dhs.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.PrimitiveDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartUtils;

// This OpMode is a modified clone of OneDriverTeleOp (12/30)
@TeleOp(name="Velocity Shooting Test",group="Testing Programs")
public class VelocityShootingTest extends OpMode {
    PrimitiveDrive drivetrain;
    Spintake spintake;
    Launcher launcher;

    SmartController controller1 = new SmartController();
    SmartController controller2 = new SmartController();

    int reverseModifier = 1;
    double slowModeModifier = 1;

    // to FOD or to not FOD, that is the question
    boolean useFod;

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
        drivetrain = new PrimitiveDrive(hardwareMap);
    }

    @Override
    public void loop() {
        // update SmartControllers
        controller1.think(gamepad1);
        controller2.think(gamepad2);

        // If Y pressed, reset FOD angle
        if (controller1.y.justPressed()) {
            drivetrain.resetImuOffset();
        }

        // If X is pressed, reverse spintake & flywheel (this comes in handy more than you'd think)
        reverseModifier = (controller1.x.isPressed()) ? -1 : 1;

        // Modifies how fast the flywheel will spin based on dpad buttons
        double launchModifier = 0;

        // Change launchModifier using dpad
        if (controller1.dpadUp.isPressed()) launchModifier += launchModifierRange;
        if (controller1.dpadDown.isPressed()) launchModifier -= launchModifierRange;

        // The maximum velocity the launcher can have
        int launchMaxV = launcher.getFlywheelMaxVelocity();

        // Set spintake, cycle & flywheel power based on corresponding triggers
        // A = Spintake, Left Trigger = Cycle, Right Trigger = Flywheel
        double spintakePower = (controller1.a.isPressed()) ? 1 : 0;
        double cyclePower = (controller1.rightTrigger.getValue() > 0.5) ? 1 : 0;
        double launchVelocity = (controller1.leftTrigger.getValue() > 0.5)
                ? (launchMaxV - launchMaxV * launchModifierRange) + launchModifier * launchMaxV : 0;

        spintake.setSpintakePower(spintakePower * reverseModifier);
        launcher.setCyclePower(cyclePower * reverseModifier);
        launcher.setFlywheelVelocity((int) launchVelocity * reverseModifier);

        // If B is pressed, open the sort chute, if it's not, close it
        // Holding B will keep the sort chute open, letting go will close the sort chute
        if (controller1.b.isPressed())
            spintake.openSort();
        else
            spintake.closeSort();

        // If both DPad Up and DPad Left are pressed, toggle FOD
        // Uses SmartUtils.combo to debounce buttons so that the combo is doable
        if (SmartUtils.combo(controller1.dpadUp, controller1.dpadLeft).justPressed() && allowToggleFod) {
            useFod = !useFod;
        }

        // Do Robot-Oriented or Field-Oriented Drive
        if (useFod)
            drivetrain.fodDrive(
                    controller1.rightStick.getX(),
                    controller1.leftStick.getX() * slowModeModifier,
                    -controller1.leftStick.getY() * slowModeModifier
            );
        else
            drivetrain.rodDrive(
                    controller1.rightStick.getX(),
                    controller1.leftStick.getX() * slowModeModifier,
                    -controller1.leftStick.getY() * slowModeModifier
            );

        telemetry.addData("flywheelV",launcher.getFlywheelVelocity());
        telemetry.update();
    }
}
