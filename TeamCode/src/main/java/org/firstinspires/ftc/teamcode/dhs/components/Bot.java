package org.firstinspires.ftc.teamcode.dhs.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;

public class Bot {
    public final Drivetrain drivetrain;
    public final Spintake spintake;
    public final Launcher launcher;
    public final ColorSensor colorSensor;

    public Alliance alliance;

    public Bot(HardwareMap hardwareMap) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);
    }

    public Bot(HardwareMap hardwareMap, Alliance alliance) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);

        this.alliance = alliance;
    }

    public Bot(HardwareMap hardwareMap, Alliance alliance, Pose2d initialPose) {
        drivetrain = new Drivetrain(hardwareMap, initialPose);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);

        this.alliance = alliance;
    }

    /**
     * Gets the position of the depot based on what team the bot thinks it's on
     * @return a {@code Pose2d} that represents where the team's depot is
     */
    public Pose2d getDepotPosition() {
        if (alliance == Alliance.BLUE)
            return new Pose2d(-60,-68,0);
        if (alliance == Alliance.RED)
            return new Pose2d(-60,60,0);

        return new Pose2d(0,0,0);
    }

    /**
     * Gets the bot's necessary heading in {@code AngleUnit} units to face the depot
     * @param unit the unit of angle measurement to use
     * @return the necessary heading to face the depot
     */
    public double getAngleToFaceDepot(AngleUnit unit) {
        return getAngleToFaceDepotAtPos(unit, drivetrain.getDrive().localizer.getPose().position);
    }

    /**
     * Get the bot's necessary heading in {@code AngleUnit} units to face the depot at a specified position
     * @param unit the unit of measurement to use
     * @param position the position from which the angle should be calculated
     * @return the necessary heading to face the depot at that position
     */
    public double getAngleToFaceDepotAtPos(AngleUnit unit, Vector2d position) {
        Pose2d depotPosition = getDepotPosition();

        double deltaX = depotPosition.position.x - position.x;
        double deltaY = depotPosition.position.y - position.y;

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

    /**
     * @return an {@link com.acmerobotics.roadrunner.Action} for preparing artifacts to be ready for firing
     */
    public Action getPrepareArtifactsAction() {
        return new Action() {
            public boolean run(@NonNull TelemetryPacket packet) {
                // TODO: Implement PrepareArtifacts, similar to prepareBalls in current autos
                return false;
            }
        };
    }

    // IDEA: spin the cycler slow enough so that the flywheel has time to recover speed
    //       before the next ball fires?
    /**
     * @param vel the desired flywheel velocity to fire the artifacts at
     * @return an {@link com.acmerobotics.roadrunner.Action} for firing three artifacts
     */
    public Action getFireThriceAction(double vel) {
        return new Action() {
            final double velocity;

            // instance initializer
            { this.velocity = vel; }

            public boolean run(@NonNull TelemetryPacket packet) {
                // TODO: Implement FireThrice, similar to fireThreeTimes in current autos
                return false;
            }
        };
    }
}
