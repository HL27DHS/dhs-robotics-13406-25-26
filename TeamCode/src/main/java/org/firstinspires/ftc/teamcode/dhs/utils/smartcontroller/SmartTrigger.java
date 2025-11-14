package org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller;

/**
 * Class that allows for easier interactions with triggers, adding deadzone functionality
 *
 * @see SmartController
 * @see SmartButton
 * @see SmartJoystick
 */
public class SmartTrigger {
    // TODO: Think about button emulation and maybe implement it? (trigger can act as button)
    // TODO: Improve documentation (it can always be improved!)
    private double value;

    // The lowest trigger value that will be accepted
    public double deadzone;

    /**
     * Gets the amount the trigger is pulled with the deadzone applied
     *
     * @return the amount the trigger is pulled, {@code 0} if less than the deadzone value
     * @see #getPureValue()
     */
    public double getValue() {
        return (Math.abs(value) > deadzone) ? value : 0;
    }

    /**
     * Gets the raw amount the trigger is pulled
     *
     * @return the amount the trigger is pulled, no deadzone applied
     * @see #getValue()
     */
    public double getPureValue() {
        return value;
    }

    /**
     * Function to update the object's internal values
     * Run this at the start of every tick of an OpMode
     *
     * @param triggerValue the current value of the trigger
     */
    public void think(double triggerValue) {
        value = triggerValue;
    }
}
