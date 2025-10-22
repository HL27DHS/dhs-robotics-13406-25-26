package org.firstinspires.ftc.teamcode.dhs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.smartcontroller.SmartController;

@TeleOp(name="Drive the robot harrison edition aka the better one")
public class DriveTeleOpHarrisonEdition extends OpMode {

    //defines what the objects drive train, spintake, and launcher are
    Drivetrain drivetrain;
    Spintake spintake;
    Launcher launcher;

    SmartController controller1 = new SmartController();

    boolean useFod = false;

    /* function used for testing
    public void reverseMotorDirection(DcMotor motor) {
        if (motor.getDirection() == DcMotorSimple.Direction.REVERSE)
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    */

    // initializing the drive teleop, constructs drivetrain, spintake, launcher
    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
    }
    //
    public void loop() {
        // TODO: Use newer Spintake & Launcher functions (setSpintakePower & setFlywheelPower)

        if (useFod)
            drivetrain.fodDrive(controller1.rightStick.getX(), controller1.leftStick.getX(), -controller1.leftStick.getY());
        else
            drivetrain.rodDrive(controller1.rightStick.getX(), controller1.leftStick.getX(), -controller1.leftStick.getY());

        spintake.setSpintakePower(controller1.leftTrigger.getValue());
        launcher.setFlywheelPower(controller1.rightTrigger.getValue());

        if (controller1.rightBumper.justPressed())
            useFod = !useFod;

        if (controller1.x.justPressed())
            drivetrain.imu.resetYaw();



        controller1.think(gamepad1);
    }
}