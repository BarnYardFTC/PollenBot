package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;
import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class TeleopTemplate {
    private final BarnRobot robot = BarnRobot.getInstance();

    private final boolean BRAKE_MODE = true; //will stop robot after realising joistics (requires testing)
    public TeleopTemplate(OpMode opMode) {
        init(opMode);
    }

    private void init(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);
        //TODO: figure out the proper way of setting follower's starting position using OpmodeData
        robot.drive.follower.setStartingPose(new Pose(OpmodeData.initialPose2D.getX(DistanceUnit.INCH), OpmodeData.initialPose2D.getY(DistanceUnit.INCH), OpmodeData.initialPose2D.getHeading(AngleUnit.RADIANS)));
        robot.drive.follower.startTeleopDrive(BRAKE_MODE);
//        robot.hardware.setBrake();

        robot.drive.setDefaultCommand(robot.drive.driveFollowerCommand());

        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenActive(
                        robot.drive.setSlowModeCommand(),
                        robot.drive.setFastModeCommand()
                );
    }
}
