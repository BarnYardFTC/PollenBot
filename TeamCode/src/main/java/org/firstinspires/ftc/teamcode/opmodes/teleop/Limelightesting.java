package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

@TeleOp(name = "Limelight Test Teleop", group = "test")
public class Limelightesting extends CommandOpMode {

    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
        robot.hardware.limelight.start();
        robot.hardware.limelight.pipelineSwitch(0);
    }

    @Override
    public void run() {
        super.run();
        robot.periodic();
        robot.hardware.limelight.update();
        robot.telemetry.addData("limelight has target", robot.hardware.limelight.hasValidTarget());
        robot.telemetry.addData("limelight tx", robot.hardware.limelight.getTx());
        robot.telemetry.addData("limelight ty", robot.hardware.limelight.getTy());
        robot.telemetry.addData("limelight ta", robot.hardware.limelight.getTa());
        robot.telemetry.addData("limelight botpose", robot.hardware.limelight.getBotpose());
    }

    @Override
    public void end() {
        OpmodeData.initialPose2D = robot.pinpoint.get().getPosition();
    }
}