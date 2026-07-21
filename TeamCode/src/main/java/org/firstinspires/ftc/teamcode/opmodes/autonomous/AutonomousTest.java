package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import static org.firstinspires.ftc.teamcode.opmodes.autonomous.AutonomousBase.*;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

@Autonomous(name = "Autonomous Test", group = "Autonomous")
public class AutonomousTest extends CommandOpMode {
    BarnRobot robot;

    @Override
    public void initialize() {
        robot = BarnRobot.getInstance();
        robot.init(this);
        robot.drive.follower.setStartingPose(CENTER_POSE);
        AutonomousBase.buildPathChains(robot.drive.follower);
        schedule(autoRoutine());
    }

    @Override
    public void run() {
        robot.periodic();
        super.run();
    }

    private Command autoRoutine() {
        return new SequentialCommandGroup(
            new FollowPathCommand(robot.drive.follower, goTarget),
            new InstantCommand(this::requestOpModeStop)
        );
    }
}
