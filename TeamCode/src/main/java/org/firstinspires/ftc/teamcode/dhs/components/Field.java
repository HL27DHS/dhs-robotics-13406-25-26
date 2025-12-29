package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;

public class Field {
    // TODO: Fill this class with functions and data regarding the field and positions of stuff on it
    public static final double TILE_WIDTH = 24.5;
    public static final double FIELD_WIDTH = 144;

    private static final Pose2d redDepotPosition = new Pose2d(-60,60,0);
    private static final Pose2d blueDepotPosition = new Pose2d(-60,-60,0);

    public static Pose2d getBlueDepotPosition() { return blueDepotPosition; }
    public static Pose2d getRedDepotPosition() { return redDepotPosition; }

    public static Pose2d getDepotPosition(Team team) {
        if (team == Team.RED)
            return getRedDepotPosition();
        else
            return getBlueDepotPosition();
    }

    public static Pose2d getArtifactRowPosition(Team team, int row) {
        // TODO: Implement function to get position of a specific row of artifacts
        return new Pose2d(0,0,0);
    }

    private Field() {}
}
