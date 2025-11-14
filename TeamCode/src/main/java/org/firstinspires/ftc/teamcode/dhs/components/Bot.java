package org.firstinspires.ftc.teamcode.dhs.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.dhs.components.Drivetrain;
import org.firstinspires.ftc.teamcode.dhs.components.Spintake;
import org.firstinspires.ftc.teamcode.dhs.components.Launcher;

public class Bot {
    public final Drivetrain drivetrain;
    public final Spintake spintake;
    public final Launcher launcher;

    public Bot(HardwareMap hardwareMap) {
        drivetrain = new Drivetrain(hardwareMap);
        spintake = new Spintake(hardwareMap);
        launcher = new Launcher(hardwareMap);
    }
}
