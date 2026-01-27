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
}
