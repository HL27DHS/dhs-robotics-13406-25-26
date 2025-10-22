package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

public class SmartButton {
    // TODO: Write documentation
    private boolean previousValue;
    private boolean currentValue;

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
