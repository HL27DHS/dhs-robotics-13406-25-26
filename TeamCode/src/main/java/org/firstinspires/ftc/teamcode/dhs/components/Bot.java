package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    public Bot(HardwareMap hardwareMap, Team team) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);

        this.team = team;
    }

    public Pose2d getDepotPosition() {
        // TODO: Implement function that gets the Pose2d of the Depot
        if (team == Team.BLUE)
            return new Pose2d(-60,-60,0);
        if (team == Team.RED)
            return new Pose2d(-60,60,0);

        return new Pose2d(0,0,0);
    }

    public double getAngleToFaceDepot(AngleUnit unit) {
        Pose2d currentPosition = drivetrain.getDrive().localizer.getPose();
        Pose2d depotPosition = getDepotPosition();

        double deltaX = depotPosition.position.x - currentPosition.position.x;
        double deltaY = depotPosition.position.y - currentPosition.position.y;

        double radians = Math.atan2(deltaY, deltaX);

        if (unit == AngleUnit.DEGREES)
            return Math.toDegrees(radians);

        return radians;
    }

    public void setAllMotorsZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        drivetrain.getFrMotor().setZeroPowerBehavior(behavior);
        drivetrain.getFlMotor().setZeroPowerBehavior(behavior);
        drivetrain.getBrMotor().setZeroPowerBehavior(behavior);
        drivetrain.getBlMotor().setZeroPowerBehavior(behavior);
    }
}
