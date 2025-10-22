package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SmartController {
    // TODO: Write documentation
    public SmartButton a = new SmartButton();
    public SmartButton b = new SmartButton();
    public SmartButton x = new SmartButton();
    public SmartButton y = new SmartButton();

    public SmartButton dpadUp = new SmartButton();
    public SmartButton dpadDown = new SmartButton();
    public SmartButton dpadLeft = new SmartButton();
    public SmartButton dpadRight = new SmartButton();

    public SmartButton rightBumper = new SmartButton();
    public SmartButton leftBumper = new SmartButton();

    public SmartButton rightStickButton = new SmartButton();
    public SmartButton leftStickButton = new SmartButton();

    public SmartJoystick rightStick = new SmartJoystick();
    public SmartJoystick leftStick = new SmartJoystick();

    public SmartTrigger rightTrigger = new SmartTrigger();
    public SmartTrigger leftTrigger = new SmartTrigger();

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
