package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Test Teleop", group = "test")
public class TestTeleop extends CommandOpMode {
    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
    }

    @Override
    public void run() {
        robot.periodic();
    }
}
