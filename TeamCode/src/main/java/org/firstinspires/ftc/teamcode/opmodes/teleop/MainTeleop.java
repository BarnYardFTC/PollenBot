package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Main Teleop", group = "main")
public class MainTeleop extends CommandOpMode {
    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
        BarnRobot.getInstance().telemetry.addData("Field oriented: ", BarnRobot.getInstance().drive.getFieldOriented());
    }

    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();
    }
}
