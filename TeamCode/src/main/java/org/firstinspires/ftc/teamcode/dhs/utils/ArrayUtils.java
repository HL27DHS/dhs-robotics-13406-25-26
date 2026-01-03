package org.firstinspires.ftc.teamcode.dhs.utils;

public class ArrayUtils {
    /**
     * Calculate an array containing the change between each element in {@code array}, where
     * {@code result[i]} is the difference between {@code array[i+1]} and {@code array[i]}
     * @param array the input array of which each element's chance will be calculated
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
     * @param array the input array of which each element's chance will be calculated
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
}
