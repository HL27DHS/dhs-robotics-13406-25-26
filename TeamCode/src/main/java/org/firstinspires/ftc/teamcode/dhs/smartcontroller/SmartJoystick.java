package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

public class SmartJoystick {
    // TODO: Look at mapping (figure out what it is)
    // TODO: Write documentation
    private double joyStickY;
    private double joyStickX;

    // The lowest stick value (x & y) that will be accepted
    public double deadzone = 0;

    // Returns the X joystick value with the deadzone applied
    public double getX() {
        return (Math.abs(joyStickX) > deadzone) ? joyStickX : 0;
    }

    // Returns the Y joystick value with the deadzone applied
    public double getY() {
        return (Math.abs(joyStickY) > deadzone) ? joyStickY : 0;
    }

    // Returns the pure X joystick value without the deadzone
    public double getPureX() {
        return joyStickX;
    }

    // Returns the pure Y joystick value without the deadzone
    public double getPureY() {
        return joyStickY;
    }

    public void think(double xValue, double yValue) {

        joyStickX = xValue;
        joyStickY = yValue;
    }

    /*
    // Stick button functionality
    // Commented out, use ___StickButton inside of SmartController instead

    private boolean currentClickValue;
    private boolean previousClickValue;

    public boolean isPressed() {
        return currentClickValue;
    }

    public boolean justPressed() {
        return currentClickValue && !previousClickValue;
    }

    public boolean justReleased() {
        return !currentClickValue && previousClickValue;
    }
    */
}
