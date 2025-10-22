package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    public DcMotor flywheel;

    public Launcher(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotor.class, "flywheel");
        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    // Function to spin the flywheel of the bot at a certain speed
    public void setFlywheelPower(double power) {
        flywheel.setPower(power);
    }

    // TODO: Write functions to accurately launch things as needed
}
