package org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller;

/**
 * Utility class for smart input classes.
 */
public class SmartUtils {
    private SmartUtils() {}

    /**
     * Function that allows for the checking if multiple buttons are pressed at a time.
     * Put simply, this function "merges" all of the {@code SmartButton}s into one.
     *
     * @param buttons A list of {@code SmartButton}s to make a button combo out of
     * @return A {@code SmartButton} that is a combination of the buttons.
     */
    public static SmartButton combo(SmartButton ... buttons) {
        SmartButton result = new SmartButton();

        // Previous combo value
        boolean past = false;

        // Loop through each button, effectively a giant AND statement for each button
        for (SmartButton button : buttons) {
            past = button.wasPressed();
            if (!past) break;
        }

        // Put the past in the result SmartButton
        result.think(past);

        // Current combo value
        boolean present = false;

        // Loop through each button again, effectively a giant AND statement for each button
        for (SmartButton button : buttons) {
            present = button.isPressed();
            if (!present) break;
        }

        // Put the present in the result SmartButton (shifts the past to where it should be)
        result.think(present);

        return result;
    }
}
