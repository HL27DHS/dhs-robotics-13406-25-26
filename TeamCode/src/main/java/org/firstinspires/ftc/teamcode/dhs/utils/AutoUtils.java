package org.firstinspires.ftc.teamcode.dhs.utils;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.dhs.components.Bot;

public class AutoUtils {
    private final Bot bot;

    public int launchVelocity = 0;

    public double fireTimeMS = 0;
    public double fireDelayMS = 0;

    public AutoUtils(Bot bot) {
        this.bot = bot;
    }

    public Action launchWithTime() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                new SleepAction(fireTimeMS / 1000),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action launchWithTime(double seconds) {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                new SleepAction(seconds),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action launchWithSensor() {
        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactLeaveAction(),
                new SleepAction(0.1),
                bot.launcher.getStopCycleAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action fireThreeBalls(boolean spintake) {
        return new SequentialAction(
                bot.launcher.getReadyAction(launchVelocity),
                launchWithTime(), // First Launch
                new SleepAction(fireDelayMS / 1000),
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime(), // Second Launch
                new SleepAction(fireDelayMS / 1000), // small buffer in case extra time for rolling needed
                new ParallelAction( // spin up and prepare balls
                        bot.launcher.getReadyAction(launchVelocity),
                        prepareBalls(spintake)
                ),
                launchWithTime((fireTimeMS+500)/1000), // Third Launch
                //new SleepAction(fireDelayMS / 1000), // small buffer in case extra time for rolling needed
                bot.launcher.getUnreadyAction()
        );
    }

    // TODO: Port to Bot class or Launcher class (with real implementation)
    public Action prepareBalls(boolean spintake) {
        // If there's already a ball present, don't even do anything
        if (bot.colorSensor.isArtifactInSensor())
            return new SequentialAction();

        if (spintake)
            return new SequentialAction(
                    bot.launcher.getStartCycleAction(1),
                    bot.spintake.getStartSpintakeAction(1),
                    bot.colorSensor.getWaitForArtifactAction(),
                    bot.spintake.getStopSpintakeAction(),
                    bot.launcher.getStopCycleAction()
            );

        return new SequentialAction(
                bot.launcher.getStartCycleAction(1),
                bot.colorSensor.getWaitForArtifactAction(),
                bot.launcher.getStopCycleAction()
        );
    }
}
