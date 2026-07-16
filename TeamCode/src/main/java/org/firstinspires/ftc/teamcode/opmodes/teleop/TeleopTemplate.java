package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

public class TeleopTemplate {
    private static final BarnRobot robot = BarnRobot.getInstance();
    public static void apply(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);
        OpmodeData.initialPose2D = new Pose2D(DistanceUnit.INCH, 50, 50, AngleUnit.DEGREES, 0);
        robot.drive.follower.setStartingPose(new Pose(OpmodeData.initialPose2D.getX(DistanceUnit.INCH), OpmodeData.initialPose2D.getY(DistanceUnit.INCH), OpmodeData.initialPose2D.getHeading(AngleUnit.RADIANS)));
        robot.drive.follower.startTeleopDrive(true);
        robot.pinpoint.setPosition();

        robot.drive.setDefaultCommand(robot.drive.driveFollowerCommand());

        robot.gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenActive(
                        robot.drive.setSlowModeCommand(),
                        robot.drive.setFastModeCommand()
                );
    }
}
