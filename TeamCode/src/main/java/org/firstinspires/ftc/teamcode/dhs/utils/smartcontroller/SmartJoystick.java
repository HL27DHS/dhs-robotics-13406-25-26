package org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller;

/**
 * Class that allows for easier interaction with joysticks, combining X and Y values into
 * one class to read them from and adding deadzone functionality
 *
 * @see SmartController
 * @see SmartButton
 * @see SmartTrigger
 */
public class SmartJoystick {
    // TODO: Look at mapping (figure out what it is)
    //       Improve documentation (it can always be improved!)
    //       Add function to get joystick angle from origin
    private double joyStickY;
    private double joyStickX;

    // The lowest stick value (x & y) that will be accepted
    public double deadzone = 0;

    /**
     * Gets the X joystick value with the deadzone applied
     *
     * @return Joystick X value, {@code 0} if the value is within the deadzone
     * @see #getPureX()
     */
    public double getX() {
        return (Math.abs(joyStickX) > deadzone) ? joyStickX : 0;
    }

    /**
     * Gets the Y joystick value with the deadzone applied
     *
     * @return Joystick Y value, {@code 0} if the value is within the deadzone
     * @see #getPureY()
     */
    public double getY() {
        return (Math.abs(joyStickY) > deadzone) ? joyStickY : 0;
    }

    /**
     * Gets the raw joystick X value
     *
     * @return the joystick X value, no deadzone applied
     * @see #getX()
     */
    public double getPureX() {
        return joyStickX;
    }

    /**
     * Gets the raw joystick Y value
     *
     * @return the joystick Y value, no deadzone applied
     * @see #getY()
     */
    public double getPureY() {
        return joyStickY;
    }

    /**
     * Function to update the object's internal values. <br>
     * Run this at the start of every tick of an OpMode
     *
     * @param xValue the joystick's X value
     * @param yValue the joystick's Y value
     */
    public void think(double xValue, double yValue) {

        joyStickX = xValue;
        joyStickY = yValue;
    }
}
