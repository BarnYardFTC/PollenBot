package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

@TeleOp(name = "Test Teleop", group = "test")
public class TestTeleop extends CommandOpMode {
    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
        BarnRobot.getInstance().drive.follower.setStartingPose(new Pose(72, 72, 90));
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
        BarnRobot.getInstance().drive.displayPositionTelemetry();
    }

    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();
    }
}
