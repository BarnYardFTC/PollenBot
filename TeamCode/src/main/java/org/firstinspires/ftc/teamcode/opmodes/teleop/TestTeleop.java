package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

@TeleOp(name = "Test Teleop", group = "test")
public class TestTeleop extends CommandOpMode {
    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        TeleopTemplate.apply(this);

    }

    @Override
    public void run() {
        super.run();
        robot.periodic();

        robot.telemetry.addData("im gay", robot.pinpoint.get().getPosition().getX(DistanceUnit.INCH));
        robot.telemetry.addData("im lesbian", robot.pinpoint.get().getPosition().getY(DistanceUnit.INCH));
        robot.telemetry.addData("im transformer", robot.pinpoint.get().getPosition().getHeading(AngleUnit.DEGREES));

        robot.telemetry.addData("im pixdor", OpmodeData.initialPose2D.getX(DistanceUnit.INCH));
        robot.telemetry.addData("im piydor", OpmodeData.initialPose2D.getY(DistanceUnit.INCH));
        robot.telemetry.addData("im pddor", OpmodeData.initialPose2D.getHeading(AngleUnit.DEGREES));
    }


}
