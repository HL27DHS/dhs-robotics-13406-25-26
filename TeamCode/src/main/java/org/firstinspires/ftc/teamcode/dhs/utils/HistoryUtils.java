package org.firstinspires.ftc.teamcode.dhs.utils;

public class HistoryUtils {
    /**
     * Gets the rates of change (num/ms) between each point in a History
     * @param history The history to find the slopes of
     * @return an array of slopes where result[i] is the slope between numbers[i] and numbers[i+1]
     * @param <T> The history's type
     */
    public static <T extends Number> double[] slopes(History<T> history) {
        T[] numbers = history.getPointsArray();
        Long[] times = history.getTimelineArray();
        double[] slopes = new double[times.length];

        for (int i = 0; i < slopes.length; i++) {
            double deltaY = numbers[i+1].doubleValue() - numbers[i].doubleValue();
            long deltaX = times[i];

            slopes[i] = deltaY / deltaX;
        }

        return slopes;
    }

    /**
     * Smooths the points of a (numerical) History
     * @param history the history to smooth
     * @param factor the EMA smoothing factor to use
     * @param <T> the type of object that the history keeps track of
     */
    public static <T extends Number> void emaSmooth(History<T> history, double factor) {
        // TODO: test this function, really sketchy casting
        T[] originalTypeNums = history.getPointsArray();
        double[] doubleNums = new double[originalTypeNums.length];

        // cast original type numbers into doubles for smoothing
        for (int i = 0; i < originalTypeNums.length; i++) {
            doubleNums[i] = (double) originalTypeNums[i];
        }

        // smooth numbers and box them
        Double[] doubleSmoothedNums = DataUtils.box(DataUtils.emaSmooth(doubleNums, factor));
        T[] originalTypeSmoothedNums = (T[]) new Object[doubleSmoothedNums.length];

        // cast doubles into original types for returning
        for (int i = 0; i < doubleSmoothedNums.length; i++) {
            originalTypeSmoothedNums[i] = (T) doubleSmoothedNums[i];
        }

        // reset history with new smoothed numbers
        history.setPointsArray(originalTypeSmoothedNums);
    }
}
