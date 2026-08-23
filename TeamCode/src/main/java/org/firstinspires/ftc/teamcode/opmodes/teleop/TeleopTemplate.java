package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static com.seattlesolvers.solverslib.gamepad.GamepadExExtKt.toggleWhenActive;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

public class TeleopTemplate {

    private static ArrayList<String> binds = new ArrayList<>();
    private static final BarnRobot robot = BarnRobot.getInstance();

    public static void apply(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);
        robot.drive.setDefaultCommand(robot.drive.drivePollenCommand());

        // Binds
        toggleBind(GamepadKeys.Button.B, "Change speed", robot.drive.setSlowModeCommand(),  robot.drive.setFastModeCommand());
        toggleBind(GamepadKeys.Button.Y, "Scoop", robot.scoop.dumpCommand(),  robot.scoop.collectCommand());
        toggleBind(GamepadKeys.Button.X, "Go to scoring pose", robot.drive.goToCommand(new Pose(72, 12, 90)), robot.drive.goToCommand(new Pose(72, 12, 90)));
        triggerBind(GamepadKeys.Trigger.RIGHT_TRIGGER, "Intake, transfer", new SequentialCommandGroup(robot.intake.enableCommand(), robot.transfer.enableCommand()), new ParallelCommandGroup(robot.intake.disableCommand(), robot.transfer.disableCommand()));
    }

    public static void toggleBind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );
        binds.add(button.toString() + ": " + description);
    }

    public static void triggerBind(GamepadKeys.Trigger trigger, String description, Command command, Command offCommand) {
        new Trigger(() -> robot.gamepadEx1.getTrigger(trigger) > 0.5)
                .whenActive(
                        command
                )
                .whenInactive(
                        offCommand
                );
        binds.add(trigger.toString() + ": " + description);
    }

    public static void periodic(){
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
    }

    public static void end() {
        binds.clear();
    }
}




