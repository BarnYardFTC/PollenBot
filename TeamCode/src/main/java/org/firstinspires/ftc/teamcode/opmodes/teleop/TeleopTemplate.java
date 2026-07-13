package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;
import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class TeleopTemplate {
    private final BarnRobot robot = BarnRobot.getInstance();
    public TeleopTemplate(OpMode opMode, OpmodeData opmodeData) {
        init(opMode, opmodeData);
    }

    private void init(OpMode opMode, OpmodeData opmodeData) {
        PhotonCore.enable();
        robot.init(opMode, opmodeData);
        //TODO: figure out the proper way of setting follower's starting position using OpmodeData
        robot.drive.follower.startTeleopDrive();
        robot.hardware.setBrake();

        robot.drive.setDefaultCommand(robot.drive.driveFollowerCommand());

        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenActive(
                        robot.drive.setSlowModeCommand(),
                        robot.drive.setFastModeCommand()
                );
    }
}
