package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.TriggerReader;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Fetus bot test", group = "test")
public class UFBTesting extends CommandOpMode { //Underdeveloped fetus testing because mechanics gave me robot with just drivetrain
    private static final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        robot.init(this);
        robot.drive.setDefaultCommand(robot.drive.driveCommand());
        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        robot.scoop.collectCommand()
                );
        new Trigger(() -> robot.gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(
                        robot.scoop.dumpCommand()
                );

        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(
                        () -> robot.scoop.getLeftScoopServo().setPosition(robot.scoop.getLeftScoopServo().getPosition() + 0.05)
                );
        new Trigger(() -> robot.gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(
                        () -> robot.scoop.getLeftScoopServo().setPosition(robot.scoop.getLeftScoopServo().getPosition() - 0.05)
                );

//        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
//                .toggleWhenActive(
//                        robot.scoop.dumpCommand(),
//                        robot.scoop.collectCommand()
//                );
//        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//                .toggleWhenActive(
//                        robot.intake.enableCommand(),
//                        robot.intake.disableCommand()
//                );
//        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
//                .toggleWhenActive(
//                        robot.transfer.enableCommand(),
//                        robot.transfer.disableCommand()
//                );
    }
}
