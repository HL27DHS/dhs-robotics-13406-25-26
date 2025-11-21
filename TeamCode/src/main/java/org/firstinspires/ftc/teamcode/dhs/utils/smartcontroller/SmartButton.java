package org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

/**
 * Class to manage buttons easier that adds the ability to check if a button was
 * pressed on this tick and released on this tick. Also retains the ability
 * to check if a button is currently pressed.
 * <p>
 * Adds functionality for double presses with a specific time window as well as
 * timed presses with a specific time window.
 *
 * @see SmartController
 * @see SmartJoystick
 * @see SmartTrigger
 */
public class SmartButton {
    // TODO: Write documentation
    private boolean previousValue;
    private boolean currentValue;

    // How many units of time it has been since this button has last been pressed
    private int ticksSinceLastPress;
    private final ElapsedTime timeSinceLastPress;

    // For how many units of time this button has been pressed
    private int ticksPressed;
    private final ElapsedTime timePressed;

    // TODO: Add functions to tell if this button was pressed for a certain time (long press, short press)
    //       Add functions to tell if this button was double-pressed with a certain time window
    //       Improve documentation (it can always be improved!)

    /**
     * Constructor, assigns default values and creates time classes
     */
    public SmartButton() {
        previousValue = false;
        currentValue = false;

        ticksSinceLastPress = 0;
        timeSinceLastPress = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        ticksPressed = 0;
        timePressed = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    /**
     * The function used to update the button's internal values. <br>
     * Run this at the start of every tick in an OpMode
     *
     * @param buttonValue the value of the button at the current tick in the OpMode,
     *                    used to keep track of when the button was and wasn't pressed
     */
    public void think(boolean buttonValue) {
        previousValue = currentValue;
        currentValue = buttonValue;

        // TODO: Make sure that this works properly and doesn't impede double-press functionality
        if (currentValue) {
            ticksSinceLastPress = 0; // reset the number of ticks from when this was last pressed
            timeSinceLastPress.reset(); // reset the timer for how long this hasn't been pressed
            ++ticksPressed; // increment the number of ticks this has been pressed
        } else {
            ticksPressed = 0; // reset the number of ticks this has been pressed for
            timePressed.reset(); // reset the timer for how long this has been pressed for
            ++ticksSinceLastPress; // increment the number of ticks since this was last pressed
        }
    }

    /**
     * Gets if this button is currently pressed
     *
     * @return {@code true} if this button is currently pressed, otherwise {@code false}
     */
    public boolean isPressed() {
        return currentValue;
    }

    /**
     * Gets if this button was pressed on this tick
     *
     * @return {@code true} if this button was pressed on this tick, otherwise {@code false}
     */
    public boolean justPressed() {
        return currentValue && !previousValue;
    }

    /**
     * Gets if this button was released on this tick
     *
     * @return {@code true} if this button was released on this tick, otherwise {@code false}
     */
    public boolean justReleased() {
        return !currentValue && previousValue;
    }

    /**
     * Gets if this button was pressed on the previous tick
     *
     * @return {@code true} if this button was pressed last tick, otherwise {@code false}
     */
    public boolean wasPressed() {
        return previousValue;
    }

    /**
     * Gets the number of ticks that this button has been pressed for.
     *
     * @return the amount of ticks that this button has been pressed for
     *
     * @see #getTimePressed(TimeUnit)
     */
    public int getTicksPressed() {
        return ticksPressed;
    }

    /**
     * Gets the amount of time that this button has been pressed for in specified units.
     *
     * @param unit the unit of time to return the value in
     * @return the amount of time in {@code unit} units that this button has been pressed for
     *
     * @see #getTicksPressed()
     */
    public long getTimePressed(TimeUnit unit) {
        return (ticksPressed > 0) ? timePressed.now(unit) : 0;
    }

    /**
     * Gets the number of ticks since this button was last pressed.
     *
     * @return the amount of ticks since this button was last pressed
     *
     * @see #getTimeSinceLastPress(TimeUnit)
     */
    public int getTicksSinceLastPress() {
        return ticksSinceLastPress;
    }

    /**
     * Gets the amount of time since this button was last pressed in specified units.
     *
     * @param unit the unit of time to return the value in
     * @return the amount of time in {@code unit} units since this button has been pressed
     *
     * @see #getTicksSinceLastPress()
     */
    public long getTimeSinceLastPress(TimeUnit unit) {
        return (ticksSinceLastPress > 0) ? timeSinceLastPress.now(unit) : 0;
    }
}
