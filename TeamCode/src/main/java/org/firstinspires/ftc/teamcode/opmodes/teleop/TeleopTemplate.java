package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
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
        robot.drive.setDefaultCommand(robot.drive.hybridDriveCommand());

        // Binds
        toggleBind(GamepadKeys.Button.B, "Change speed", robot.drive.setSlowModeCommand(),  robot.drive.setFastModeCommand());
        toggleBind(GamepadKeys.Button.Y, "Scoop", robot.scoop.dumpCommand(),  robot.scoop.collectCommand());
        toggleBind(GamepadKeys.Button.X, "Intake, transfer", new SequentialCommandGroup(robot.intake.enableCommand(), robot.transfer.enableCommand()), new SequentialCommandGroup(robot.intake.disableCommand(), robot.transfer.disableCommand()));
        toggleBind(GamepadKeys.Button.DPAD_UP, "Change drivetrain type", robot.drive.changeFieldOrientedCommand(), robot.drive.changeFieldOrientedCommand());
    }

    public static void toggleBind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );
        binds.add(button.toString() + ": " + description);
    }

    public static void periodic(){
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
    }

    public static void end() {
        binds.clear();
    }
}




