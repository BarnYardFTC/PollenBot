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

        if(OpmodeData.initialPose2D != null){
            robot.pinpoint.get().setPosition(OpmodeData.initialPose2D);
        } else OpmodeData.initialPose2D = robot.pinpoint.get().getPosition();

    }

    @Override
    public void run() {
        super.run();
        robot.periodic();

        OpmodeData.initialPose2D = robot.pinpoint.get().getPosition();

        robot.telemetry.addData("Pinpoint X", robot.pinpoint.get().getPosition().getX(DistanceUnit.INCH));
        robot.telemetry.addData("Pinpoint Y", robot.pinpoint.get().getPosition().getY(DistanceUnit.INCH));
        robot.telemetry.addData("Pinpoint Heading", robot.pinpoint.get().getPosition().getHeading(AngleUnit.DEGREES));

        robot.telemetry.addData("Data X", OpmodeData.initialPose2D.getX(DistanceUnit.INCH));
        robot.telemetry.addData("Data Y", OpmodeData.initialPose2D.getY(DistanceUnit.INCH));
        robot.telemetry.addData("Data Heading", OpmodeData.initialPose2D.getHeading(AngleUnit.DEGREES));
    }
}
