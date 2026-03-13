package org.firstinspires.ftc.teamcode.dhs.utils;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.canvas.CanvasOp;
import com.acmerobotics.roadrunner.Vector2d;

import java.util.List;

/**
 * Includes various utility functions for drawing on {@link Canvas} instances
 */
public class CanvasUtils {
    /**
     * The radius of the point drawn by {@link CanvasUtils#drawAimPoint(Canvas, Vector2d)}
     */
    public static final double AIM_POINT_RADIUS = 0.5;

    /**
     * Draws a line on the canvas starting at start pos and continuing infinitely towards an angle
     * @param canvas the canvas to draw on
     * @param startPos the position to start the line at
     * @param angle the angle the line go towards (radians)
     */
    public static void drawAimLine(Canvas canvas, Vector2d startPos, double angle) {
        Vector2d endPt = new Vector2d(1000, 0);
        endPt = PoseUtils.rotateVector(endPt, angle);

        canvas.strokeLine(startPos.x, startPos.y, endPt.x, endPt.y);
    }

    /**
     * Draws a point on the canvas
     * @param canvas the canvas to draw on
     * @param aimPos the position of the point on the canvas
     */
    public static void drawAimPoint(Canvas canvas, Vector2d aimPos) {
        canvas.fillCircle(aimPos.x, aimPos.y, AIM_POINT_RADIUS);
    }

    /**
     * Draws a representation of the bot on the canvas
     * @param canvas the canvas to draw on
     * @param botPos the position of the bot on the canvas
     */
    public static void drawBot(Canvas canvas, Vector2d botPos) {
        // TODO: Implement
    }

    /**
     * Draws an arrow on the canvas
     * @param canvas the canvas to draw on
     * @param startPos the starting point of the arrow
     * @param endPos the end point of the arrow
     * @param headSize the length of the head segments
     */
    public static void drawArrow(Canvas canvas, Vector2d startPos, Vector2d endPos, double headSize) {
        // TODO: Implement
    }
}
