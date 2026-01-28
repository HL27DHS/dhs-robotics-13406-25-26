package org.firstinspires.ftc.teamcode.dhs.utils;

public class DataUtils {
    /**
     * Calculate an array containing the change between each element in {@code array}, where
     * {@code result[i]} is the difference between {@code array[i+1]} and {@code array[i]}
     * @param array the input array of which each element's change will be calculated
     * @return the result of the calculation
     */
    public static double[] change(double[] array) {
        double[] result = new double[array.length - 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = array[i+1] - array[i];
        }

        return result;
    }

    /**
     * Calculate an array containing the change between each element in {@code array}, where
     * {@code result[i]} is the difference between {@code array[i+1]} and {@code array[i]}
     * @param array the input array of which each element's change will be calculated
     * @return the result of the calculation
     */
    public static int[] change(int[] array) {
        int[] result = new int[array.length - 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = array[i+1] - array[i];
        }

        return result;
    }

    /**
     * Calculate the sum of every element in an array
     * @param array the array whose elements' sum will be calculated
     * @return the sum of all elements in the array
     */
    public static double sum(double[] array) {
        double sum = 0;

        for (double num : array)
            sum += num;

        return sum;
    }

    /**
     * Calculate the sum of every element in an array
     * @param array the array whose elements' sum will be calculated
     * @return the sum of all elements in the array
     */
    public static int sum(int[] array) {
        int sum = 0;

        for (int num : array)
            sum += num;

        return sum;
    }

    /**
     * Calculate the average of every element in an array
     * @param array the array whose elements' average will be calculated
     * @return the average of all elements in the array
     */
    public static double average(int[] array) {
        return (double) sum(array) / array.length;
    }

    /**
     * Calculate the average of every element in an array
     * @param array the array whose elements' average will be calculated
     * @return the average of all elements in the array
     */
    public static double average(double[] array) {
        return sum(array) / array.length;
    }

    /**
     * Calculate the lowest number in an array
     * @param array the array to check
     * @return the lowest number in the array
     */
    public static int min(int[] array) {
        if (array.length < 1) return 0;

        int min = array[0];

        for (int num : array)
            min = Math.min(num, min);

        return min;
    }

    /**
     * Calculate the lowest number in an array
     * @param array the array to check
     * @return the lowest number in the array
     */
    public static double min(double[] array) {
        if (array.length < 1) return 0;

        double min = array[0];

        for (double num : array)
            min = Math.min(num, min);

        return min;
    }

    /**
     * Calculate the highest number in an array
     * @param array the array to check
     * @return the highest number in the array
     */
    public static int max(int[] array) {
        if (array.length < 1) return 0;

        int max = array[0];

        for (int num : array)
            max = Math.max(num, max);

        return max;
    }

    /**
     * Calculate the highest number in an array
     * @param array the array to check
     * @return the highest number in the array
     */
    public static double max(double[] array) {
        if (array.length < 1) return 0;

        double max = array[0];

        for (double num : array)
            max = Math.max(num, max);

        return max;
    }

    /**
     * "Unbox" reference type list into primitive type list
     * @param array reference type list to be unboxed
     * @return unboxed primitive type list
     */
    public static int[] unbox(Integer[] array) {
        int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = array[i];

        return result;
    }

    /**
     * "Unbox" reference type list into primitive type list
     * @param array reference type list to be unboxed
     * @return unboxed primitive type list
     */
    public static double[] unbox(Double[] array) {
        double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = array[i];

        return result;
    }

    /**
     * "Box" primitive type list into reference type list
     * @param array primitive type list to be boxed
     * @return boxed reference type list
     */
    public static Integer[] box(int[] array) {
        Integer[] result = new Integer[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = array[i];

        return result;
    }

    /**
     * "Box" primitive type list into reference type list
     * @param array primitive type list to be boxed
     * @return boxed reference type list
     */
    public static Double[] box(double[] array) {
        Double[] result = new Double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = array[i];

        return result;
    }

    /**
     * Checks if the difference between two numbers is within a given threshold
     * @param a First number to check the distance between
     * @param b Second number to check the distance between
     * @param threshold the maximum allowed difference between the two numbers, should be positive
     * @return if the distance between {@code a} and {@code b} is within {@code threshold}
     */
    public static boolean threshold(double a, double b, double threshold) {
        return (Math.abs(a - b) <= threshold);
    }

    /**
     * Calculate the set of slopes between each number in a set of numbers
     * {@code result[i]} is the slope between {@code array[i+1]} and {@code array[i]}
     * @param array The list of numbers to calculate the slopes of
     * @return A list containing the slopes between each number
     */
    public static double[] slopes(int[] array) {
        double[] result = new double[array.length - 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = (double) array[i+1] / array[i];
        }

        return result;
    }

    /**
     * Calculate the set of slopes between each number in a set of numbers
     * {@code result[i]} is the slope between {@code array[i+1]} and {@code array[i]}
     * @param array The list of numbers to calculate the slopes of
     * @return A list containing the slopes between each number
     */
    public static double[] slopes(double[] array) {
        double[] result = new double[array.length - 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = array[i+1] / array[i];
        }

        return result;
    }

    /**
     * Smooth a set of numbers using the EMA smoothing algorithm
     * @param array a list of numbers to smooth
     * @param factor the EMA smoothing "factor"
     * @return a smoothed version of the provided data set
     */
    public static int[] emaSmooth(int[] array, double factor) {
        // TODO: Implement
        return new int[0];
    }

    /**
     * Smooth a set of numbers using the EMA smoothing algorithm
     * @param array a list of numbers to smooth
     * @param factor the EMA smoothing "factor"
     * @return a smoothed version of the provided data set
     */
    public static double[] emaSmooth(double[] array, double factor) {
        // TODO: Implement
        return new double[0];
    }
}