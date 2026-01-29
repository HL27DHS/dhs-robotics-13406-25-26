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
import org.firstinspires.ftc.teamcode.dhs.utils.PoseUtils;

public class Bot {
    public final Drivetrain drivetrain;
    public final Spintake spintake;
    public final Launcher launcher;
    public final ColorSensor colorSensor;

    // static, saved between OpModes
    public static Alliance alliance;

    public Bot(HardwareMap hardwareMap) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);
    }

    public Bot(HardwareMap hardwareMap, Alliance botAlliance) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);

        alliance = botAlliance;
    }

    public Bot(HardwareMap hardwareMap, Alliance botAlliance, Pose2d initialPose) {
        drivetrain = new Drivetrain(hardwareMap, initialPose);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);

        alliance = botAlliance;
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
     * Get the needed turning value to face this robot's depot. Can be passed into the
     * drivetrain's drive functions to automatically face the depot at all times
     * @return the amount of steering necessary to face the depot
     */
    public double getTurnValueToFaceDepot() {
        // if you would like to visualize this functioning function, see it graphed on desmos
        // https://www.desmos.com/calculator/9sw8llfrp4

        // the upper limit at which the function will give up and just say to give it full power
        double upper = Math.PI / 18;

        // the lower limit at which the function will give up and just say to give it no power
        double lower = Math.PI / 90;

        // the power the function is multiplied by, changes how it's curved
        double power = 3;

        double currentYaw = drivetrain.getYaw(AngleUnit.RADIANS);
        double neededYaw = getAngleToFaceDepot(AngleUnit.RADIANS);
        double difference = AngleUnit.normalizeRadians(neededYaw - currentYaw);

        // the way rotation is needed (1 or -1)
        int sign = (int) (difference / Math.abs(difference));

        if (Math.abs(difference) >= upper) return -sign;
        if (Math.abs(difference) <= lower) return 0;

        // function is split into two variables to be more digestible
        double multiplier = 1 / Math.pow(upper - lower, power);
        double poweredDiff = Math.pow(difference - lower, power);

        return -(multiplier * poweredDiff * sign);
    }

    /**
     * @return robot's current distance from the depot.
     */
    public double getDistanceFromDepot() {
        return PoseUtils.distance(drivetrain.getDrive().localizer.getPose(), getDepotPosition());
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
     * @param doSpintake whether or not to use the spintake to prepare the balls
     * @return an {@link com.acmerobotics.roadrunner.Action} for preparing artifacts to be ready for firing
     */
    public Action getPrepareArtifactsAction(boolean doSpintake) {
        return new Action() {
            boolean initialized = false;
            final boolean useSpintake = doSpintake;

            public boolean run(@NonNull TelemetryPacket packet) {
                // if on first pass and there's already an artifact, just give up
                if (!initialized && colorSensor.isArtifactInSensor())
                    return true;

                // if not initialized, make it happen
                if (!initialized) {
                    if (useSpintake) spintake.setSpintakePower(1);
                    launcher.setCyclePower(1);

                    initialized = true;
                }

                // if ball is present, stop everything and return yes
                if (colorSensor.isArtifactInSensor()) {
                    if (useSpintake) spintake.setSpintakePower(0);
                    launcher.setCyclePower(0);

                    return false;
                }

                return true;
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
