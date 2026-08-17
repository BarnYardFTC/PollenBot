package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static com.seattlesolvers.solverslib.gamepad.GamepadExExtKt.toggleWhenActive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

public class TeleopTemplate {

    public static ArrayList<String> binds = new ArrayList<>();
    private static final BarnRobot robot = BarnRobot.getInstance();

    public static void apply(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);
        robot.drive.setDefaultCommand(robot.drive.driveCommand());

        /* Binds: */
        toggleBind(GamepadKeys.Button.B, "Set Speed: ",  robot.drive.setSlowModeCommand(),  robot.drive.setFastModeCommand());

    }

    public static void toggleBind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );
        binds.add(button.toString() + " " + description);
    }

    public static void periodic(){
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
    }
}




