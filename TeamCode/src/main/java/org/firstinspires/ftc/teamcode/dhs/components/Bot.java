package org.firstinspires.ftc.teamcode.dhs.components;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;

public class Bot {
    /**
     * Enum to store which team this bot is on
     */
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

    /**
     * Gets the position of the depot based on what team the bot thinks it's on
     * @return a {@code Pose2d} that represents where the team's depot is
     */
    public Pose2d getDepotPosition() {
        if (team == Team.BLUE)
            return new Pose2d(-60,-60,0);
        if (team == Team.RED)
            return new Pose2d(-60,60,0);

        return new Pose2d(0,0,0);
    }

    /**
     * Gets the bot's necessary heading in {@code AngleUnit} units to face the depot
     * @param unit the unit of angle measurement to use
     * @return the necessary heading to face the depot
     */
    public double getAngleToFaceDepot(AngleUnit unit) {
        return getAngleToFaceDepotAtPos(unit, drivetrain.getDrive().localizer.getPose());
    }

    /**
     * Get the bot's necessary heading in {@code AngleUnit} units to face the depot at a specified position
     * @param unit the unit of measurement to use
     * @param position the position from which the angle should be calculated
     * @return the necessary heading to face the depot at that position
     */
    public double getAngleToFaceDepotAtPos(AngleUnit unit, Pose2d position) {
        Pose2d depotPosition = getDepotPosition();

        double deltaX = depotPosition.position.x - position.position.x;
        double deltaY = depotPosition.position.y - position.position.y;

        double radians = Math.atan2(deltaY, deltaX);

        if (unit == AngleUnit.DEGREES)
            return Math.toDegrees(radians);

        return radians;
    }

    /**
     * Sets every drivetrain motor's zero power behavior to a designated behavior
     * @param behavior the desired zero power behavior
     */
    public void setAllMotorsZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        drivetrain.getFrMotor().setZeroPowerBehavior(behavior);
        drivetrain.getFlMotor().setZeroPowerBehavior(behavior);
        drivetrain.getBrMotor().setZeroPowerBehavior(behavior);
        drivetrain.getBlMotor().setZeroPowerBehavior(behavior);
    }
}
