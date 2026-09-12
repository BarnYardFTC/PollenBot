package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
        robot.drive.setDefaultCommand(robot.drive.driveCommand());
        TeleopTemplate.toggleBind(GamepadKeys.Button.X, "Transfer", robot.transfer.enableCommand(), robot.transfer.disableCommand());
        TeleopTemplate.toggleBind(GamepadKeys.Button.B, "Intake", robot.intake.enableCommand(), robot.intake.disableCommand());
    }

    @Override
    public void run() {
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
        super.run();
    }
}
