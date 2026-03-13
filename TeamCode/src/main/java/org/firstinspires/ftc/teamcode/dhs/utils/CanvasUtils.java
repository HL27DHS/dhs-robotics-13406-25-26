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
     * The radius of the circle drawn by {@link CanvasUtils#drawBot(Canvas, Vector2d)}
     */
    public static final double BOT_RADIUS = 8;

    /**
     * Draws a line on the canvas starting at start pos and continuing infinitely towards an angle
     * @param canvas the canvas to draw on
     * @param startPos the position to start the line at
     * @param angle the angle the line go towards (radians)
     */
    public static void drawAimLine(Canvas canvas, Vector2d startPos, double angle) {
        Vector2d endPt = new Vector2d(startPos.x + 1000, startPos.y + 0);
        endPt = PoseUtils.rotateVector(endPt, startPos, angle);

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
     * Draws a representation of the bot on the canvas (note: this does not show heading)
     * @param canvas the canvas to draw on
     * @param botPos the position of the bot on the canvas
     */
    public static void drawBot(Canvas canvas, Vector2d botPos) {
        canvas.strokeCircle(botPos.x, botPos.y, BOT_RADIUS);
    }

    /**
     * Draws a representation of the bot on the canvas, including a little line for heading
     * @param canvas the canvas to draw on
     * @param botPos the position of the bot on the canvas
     * @param heading the bot's heading (radians)
     */
    public static void drawBot(Canvas canvas, Vector2d botPos, double heading) {
        // Draw bot circle
        drawBot(canvas, botPos);

        // Calculate where the end point should be
        Vector2d endPoint = new Vector2d(
                botPos.x + BOT_RADIUS,
                botPos.y
        );
        endPoint = PoseUtils.rotateVector(endPoint, botPos, heading);

        // Draw bot heading line
        canvas.strokeLine(botPos.x, botPos.y, endPoint.x, endPoint.y);
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
