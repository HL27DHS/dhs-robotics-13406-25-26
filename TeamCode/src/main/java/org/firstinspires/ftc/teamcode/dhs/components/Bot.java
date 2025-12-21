package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;

public class Bot {
    public enum Team {
        RED,
        BLUE
    }

    public final Drivetrain drivetrain;
    public final Spintake spintake;
    public final Launcher launcher;

    public Team team;

    public Bot(HardwareMap hardwareMap) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
    }

    public Pose2d getDepotPosition() {
        // TODO: Implement function that gets the Pose2d of the Depot
        if (team == Team.BLUE)
            return new Pose2d(0,0,0);
        if (team == Team.RED)
            return new Pose2d(0,0,0);

        return new Pose2d(0,0,0);
    }

    public double getAngleToFaceDepot(AngleUnit unit) {
        // TODO: Implement function that gets the bot's needed heading to face the depot
        return 0;
    }
}
