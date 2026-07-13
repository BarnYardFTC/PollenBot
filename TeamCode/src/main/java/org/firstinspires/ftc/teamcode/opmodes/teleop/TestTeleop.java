package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

@TeleOp(name = "Test Teleop", group = "test")
public class TestTeleop extends CommandOpMode {
    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        TeleopTemplate template = new TeleopTemplate(this);
    }

    @Override
    public void run() {
        robot.periodic();
    }
}
