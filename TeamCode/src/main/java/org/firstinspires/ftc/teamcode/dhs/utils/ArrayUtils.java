package org.firstinspires.ftc.teamcode.dhs.utils;

public class ArrayUtils {
    private ArrayUtils() {}

    private static <T> T[] shiftPositive(T[] array) {
        // TODO: Implement
        return array;
    }

    private static <T> T[] shiftNegative(T[] array) {
        // TODO: Implement
        return array;
    }

    public static <T> T[] shift(T[] array, int shifts) {
        // Don't bother if array isn't long enough for a shift
        if (array.length <= 1) return array;

        T[] result = array.clone();

        T firstElement = array[0];

        // TODO: Finish implementing
        return array;
    }
}
