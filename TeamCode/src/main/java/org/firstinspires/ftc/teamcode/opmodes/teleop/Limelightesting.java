package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

@TeleOp(name = "Limelight Test Teleop", group = "test")
public class Limelightesting extends CommandOpMode {

    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
        robot.limelight.get().start();
        robot.limelight.get().pipelineSwitch(0);
    }

    @Override
    public void run() {
        super.run();
        robot.periodic();
        robot.limelight.update();
        robot.telemetry.addData("limelight has target", robot.limelight.hasValidTarget());
        robot.telemetry.addData("limelight tx", robot.limelight.getTx());
        robot.telemetry.addData("limelight ty", robot.limelight.getTy());
        robot.telemetry.addData("limelight ta", robot.limelight.getTa());
        robot.telemetry.addData("limelight botpose", robot.limelight.getBotpose());
    }

    @Override
    public void end() {
        OpmodeData.initialPose2D = robot.pinpoint.get().getPosition();
    }
}