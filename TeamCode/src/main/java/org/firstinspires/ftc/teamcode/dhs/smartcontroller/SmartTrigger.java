package org.firstinspires.ftc.teamcode.dhs.smartcontroller;

public class SmartTrigger {
    // TODO: Think about button emulation and maybe implement it? (trigger can act as button)
    // TODO: Write documentation
    private double value;

    // The lowest trigger value that will be accepted
    public double deadzone;

    // Gets the value of the trigger with the deadzone applied
    public double getValue() {
        return (Math.abs(value) > deadzone) ? value : 0;
    }

    // Gets the value of the trigger without the deadzone applied
    public double getPureValue() {
        return value;
    }

    public void think(double triggerValue) {
        value = triggerValue;
    }
}
