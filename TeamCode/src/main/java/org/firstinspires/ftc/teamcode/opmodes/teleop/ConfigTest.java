package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

@TeleOp(name = "ConfigTest", group = "test")
public class ConfigTest extends CommandOpMode {
    private static final BarnRobot robot = BarnRobot.getInstance();

    private ArrayList<String> binds = new ArrayList<>();

    @Override
    public void initialize() {
        robot.init(this);
        bind(GamepadKeys.Button.Y, "Scoop", robot.scoop.collectCommand(), robot.scoop.dumpCommand());
        bind(GamepadKeys.Button.X, "Transfer", robot.transfer.enableCommand(), robot.transfer.disableCommand());
        bind(GamepadKeys.Button.B, "Intake", robot.intake.enableCommand(), robot.intake.disableCommand());
    }

    @Override
    public void run() {
        TeleopTemplate.periodic();
        robot.telemetry.addLine("right scoop servo: " +  robot.scoop.getRightPos() + " left scoop servo: " + robot.scoop.getLeftPos());
        super.run();
    }

    //TODO: implement in template
    private void bind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );
        binds.add(button.toString() + ": " + description);
    }
}
