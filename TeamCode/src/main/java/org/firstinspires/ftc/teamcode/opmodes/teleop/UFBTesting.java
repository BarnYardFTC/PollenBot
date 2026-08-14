package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Fetus bot test", group = "test")
public class UFBTesting extends CommandOpMode { //Underdeveloped fetus testing because mechanics gave me robot with just drivetrain
    private static final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        robot.init(this);
        robot.drive.setDefaultCommand(robot.drive.driveCommand());
        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .toggleWhenActive(
                        robot.scoop.dumpCommand(),
                        robot.scoop.collectCommand()
                );
        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .toggleWhenActive(
                        robot.intake.enableCommand(),
                        robot.intake.disableCommand()
                );
        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .toggleWhenActive(
                        robot.transfer.enableCommand(),
                        robot.transfer.disableCommand()
                );
    }
}
