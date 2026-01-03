package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class Field {
    // TODO: Fill this class with functions and data regarding the field and positions of stuff on it
    public static final double TILE_WIDTH = 24.5;
    public static final double FIELD_WIDTH = 144;

    private static final Vector2d redDepotPosition = new Vector2d(-60,60);
    private static final Vector2d blueDepotPosition = new Vector2d(-60,-60);

    // TODO: Figure out positions of red and blue base
    private static final Vector2d redBasePosition = new Vector2d(0,0);
    private static final Vector2d blueBasePosition = new Vector2d(0,0);

    public static Vector2d getBlueDepotPosition() { return blueDepotPosition; }
    public static Vector2d getRedDepotPosition() { return redDepotPosition; }

    public static Vector2d getDepotPosition(Alliance alliance) {
        if (alliance == Alliance.RED)
            return getRedDepotPosition();
        else
            return getBlueDepotPosition();
    }

    public static Pose2d getArtifactRowPosition(Alliance alliance, int row) {
        // TODO: Implement function to get position of a specific row of artifacts
        return new Pose2d(0,0,0);
    }

    private Field() {}
}
