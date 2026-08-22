package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import java.util.ArrayList;

@TeleOp(name = "Test Teleop", group = "test")
public class TestTeleop extends CommandOpMode {
    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
    }

    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();
    }
}
