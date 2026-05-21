package org.firstinspires.ftc.teamcode.dhs.opmodes.teleops;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Bot;
import org.firstinspires.ftc.teamcode.dhs.game.Alliance;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartController;
import org.firstinspires.ftc.teamcode.dhs.utils.smartcontroller.SmartUtils;

@TeleOp(name="Ballin'",group="0A - Outreach OpModes")
public class BallerTeleOp extends OpMode {
    Bot bot;

    final Alliance robotTeam = Alliance.BLUE;
    final Pose2d robotStartPose = new Pose2d(0,0,Math.PI);

    SmartController driver;
    SmartController manipulator;

    // If the shutdown is active, don't let the bot do anything
    boolean shutdownActive = false;

    // Whether or not the bot will use Field Oriented Drive
    boolean useFod = false;

    public void drive(double fwd, double strafe, double turn) {
        if (useFod)
            bot.drivetrain.fodDrive(turn, strafe, fwd);
        else
            bot.drivetrain.rodDrive(turn, strafe, fwd);
    }

    /**
     * Simplifies a double by rounding it into steps
     * <p> Example: input: 0.15, steps: 5 -> 0.2
     * @param input The double to simplify
     * @param steps The number of equally-spread steps it will simplify it into
     * @return The simplified double
     */
    public double simplifyAnalog(double input, double steps) {
        return Math.round(input * steps) / steps;
    }

    public void init() {
        bot = new Bot(hardwareMap, robotTeam, robotStartPose);
        driver = new SmartController();
        manipulator = new SmartController();
    }

    public void loop() {
        // Manage drive controllers
        driver.think(gamepad1);
        manipulator.think(gamepad2);

        // Update bot pose estimate (for auto aim)
        PoseVelocity2d vel = bot.drivetrain.getDrive().updatePoseEstimate();

        // Declare variables
        double driveFwd;
        double driveStrafe;
        double driveTurn;

        int flywheelVel;
        double spintakePower;
        double cyclePower;

        int manipulatorReverseModifier;
        double driveSlowmodeModifier = 1;

        // Toggle shutdown (DPAD DOWN + B on either controller)
        if (SmartUtils.combo(driver.b, driver.dpadDown).justPressed()
         || SmartUtils.combo(manipulator.b, manipulator.dpadDown).justPressed())
            shutdownActive = !shutdownActive;

        // Toggle team (DPAD DOWN + X on either controller)
        if (SmartUtils.combo(driver.x, driver.dpadDown).justPressed()
         || SmartUtils.combo(manipulator.x, manipulator.dpadDown).justPressed())
            bot.swapAlliance();

        // Reset pose (DPAD DOWN + Y on either controller)
        if (SmartUtils.combo(driver.y, driver.dpadDown).justPressed()
         || SmartUtils.combo(manipulator.y, manipulator.dpadDown).justPressed())
            bot.resetPose(new Pose2d(0,0,0));

        // Post telemetry
        telemetry.addData("Robot team",bot.getAlliance());
        telemetry.update();

        // If override is active, stop everything
        if (shutdownActive) {
            bot.stopEverything();
            return;
        }

        // ----- DRIVER CONTROLS ----------------------------------------------------- //

        // Reset FOD yaw (Y on driver controller)
        if (driver.y.isPressed()) bot.drivetrain.resetImuOffset();
        // Toggle FOD (X on driver controller)
        if (driver.x.isPressed()) useFod = !useFod;

        // Drive X/Y (LS on driver controller)
        driveFwd = -driver.leftStick.getY();
        driveStrafe = driver.leftStick.getX();

        // Turn (RS on driver controller)
        driveTurn = driver.rightStick.getX();

        // Slow mode (LB & RB on driver controller)
        driveSlowmodeModifier -= (driver.leftBumper.isPressed()) ? 0.3 : 0;
        driveSlowmodeModifier -= (driver.rightBumper.isPressed()) ? 0.3 : 0;

        // Auto aim (A on driver controller)
        if (driver.a.isPressed())
            driveTurn = bot.getTurnValueToFaceDepot();

        // ----- MANIPULATOR CONTROLS ------------------------------------------------ //

        // Flywheel speed (LT on manipulator controller)
        flywheelVel = (int) (bot.launcher.getFlywheelMaxVelocity()
                * simplifyAnalog(manipulator.leftTrigger.getValue(), 5));

        // Auto aim (A on manipulator controller)
        if (manipulator.a.isPressed())
            flywheelVel = bot.getRecommendedFlywheelVelocity();

        // Transfer speed (RT on manipulator controller)
        cyclePower = simplifyAnalog(manipulator.rightTrigger.getValue(), 5);

        // Spintake (X on manipulator controller)
        spintakePower = (manipulator.x.isPressed()) ? 0.8 : 0;

        // Drop chute (Y on manipulator controller)
        if (manipulator.y.isPressed())
            bot.spintake.openSort();
        else
            bot.spintake.closeSort();

        // Reverse modifier (B on manipulator controller)
        manipulatorReverseModifier = (manipulator.b.isPressed()) ? -1 : 1;

        // ----- SETTING POWERS ------------------------------------------------------ //

        // Handle driver stuff
        driveFwd *= driveSlowmodeModifier;
        driveStrafe *= driveSlowmodeModifier;
        drive(driveFwd, driveStrafe, driveTurn);

        // Handle manipulator stuff
        flywheelVel *= manipulatorReverseModifier;
        bot.launcher.setFlywheelVelocity(flywheelVel);
        cyclePower *= manipulatorReverseModifier;
        bot.launcher.setCyclePower(cyclePower);
        spintakePower *= manipulatorReverseModifier;
        bot.spintake.setSpintakePower(spintakePower);
    }
}
