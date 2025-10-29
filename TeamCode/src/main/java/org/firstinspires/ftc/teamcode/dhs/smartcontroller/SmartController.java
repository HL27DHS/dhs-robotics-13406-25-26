package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Class that combines "Smart" classes to make a controller class that is easier to work with
 *
 * @see Gamepad
 * @see SmartButton
 * @see SmartJoystick
 * @see SmartTrigger
 */
public class SmartController {
    // TODO: Improve documentation (it can always be improved!)

    /** The A button on the controller */
    public SmartButton a = new SmartButton();
    /** The B button on the controller */
    public SmartButton b = new SmartButton();
    /** The X button on the controller */
    public SmartButton x = new SmartButton();
    /** The Y button on the controller */
    public SmartButton y = new SmartButton();

    /** The up button on the controller's D-Pad */
    public SmartButton dpadUp = new SmartButton();
    /** The down button on the controller's D-Pad */
    public SmartButton dpadDown = new SmartButton();
    /** The left button on the controller's D-Pad */
    public SmartButton dpadLeft = new SmartButton();
    /** The right button on the controller's D-Pad */
    public SmartButton dpadRight = new SmartButton();

    /** The controller's right bumper */
    public SmartButton rightBumper = new SmartButton();
    /** The controller's left bumper */
    public SmartButton leftBumper = new SmartButton();

    /** The right stick click button on the controller */
    public SmartButton rightStickButton = new SmartButton();
    /** The left stick click button on the controller */
    public SmartButton leftStickButton = new SmartButton();

    /** The left stick on the controller */
    public SmartJoystick rightStick = new SmartJoystick();
    /** The right stick on a controller */
    public SmartJoystick leftStick = new SmartJoystick();

    /** The right trigger on the controller */
    public SmartTrigger rightTrigger = new SmartTrigger();
    /** The left trigger on the controller */
    public SmartTrigger leftTrigger = new SmartTrigger();

    /**
     * Function to update the object's internal values. <br>
     * Run this at the start of every tick of an OpMode
     *
     * @param gamepad a {@code Gamepad} for the controller to pull it's values from
     */
    public void think(Gamepad gamepad) {
        a.think(gamepad.a);
        b.think(gamepad.b);
        x.think(gamepad.x);
        y.think(gamepad.y);

        dpadRight.think(gamepad.dpad_right);
        dpadLeft.think(gamepad.dpad_left);
        dpadUp.think(gamepad.dpad_up);
        dpadDown.think(gamepad.dpad_down);

        rightBumper.think(gamepad.right_bumper);
        leftBumper.think(gamepad.left_bumper);

        rightStickButton.think(gamepad.right_stick_button);
        leftStickButton.think(gamepad.left_stick_button);

        rightStick.think(gamepad.right_stick_x, gamepad.right_stick_y);
        leftStick.think(gamepad.left_stick_x, gamepad.left_stick_y);

        rightTrigger.think(gamepad.right_trigger);
        leftTrigger.think(gamepad.left_trigger);
    }
}
