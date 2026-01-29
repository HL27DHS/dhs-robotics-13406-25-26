package org.firstinspires.ftc.teamcode.dhs.utils;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class PoseUtils {
    /**
     * Calculate the distance between two {@code Vector2d}s.
     * @param a The first vector
     * @param b The second vector
     * @return The distance between vectors {@code a} and {@code b}
     */
    public static double distance(Vector2d a, Vector2d b) {
        double leg1 = Math.pow(a.x - b.x, 2);
        double leg2 = Math.pow(a.y - b.y, 2);

        return Math.sqrt(leg1 + leg2);
    }

    /**
     * Calculate the distance between two {@code Pose2d}s
     * @param a The first pose
     * @param b The second pose
     * @return The distance between poses {@code a} and {@code b}
     */
    public static double distance(Pose2d a, Pose2d b) {
        return distance(a.position, b.position);
    }
}
