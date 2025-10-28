package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

public class SmartButton {
    // TODO: Write documentation
    private boolean previousValue;
    private boolean currentValue;

    // How many units of time it has been since this button has last been pressed
    private int ticksSinceLastPress;
    private double secondsSinceLastPress;

    // For how many units of time this button has been pressed
    private int ticksPressed;
    private double secondsPressed;

    // TODO: Add functionality to the ticksSinceLastPress, secondsSinceLastPress, ticksPressed, and secondsPressed variables.

    public void think(boolean buttonValue) {
        previousValue = currentValue;
        currentValue = buttonValue;
    }

    public boolean isPressed() {
        return currentValue;
    }

    public boolean justPressed() {
        return currentValue && !previousValue;
    }

    public boolean justReleased() {
        return !currentValue && previousValue;
    }
}
