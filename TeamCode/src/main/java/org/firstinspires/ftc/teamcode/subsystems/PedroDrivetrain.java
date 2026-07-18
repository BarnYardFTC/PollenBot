package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

import java.util.Objects;

public class PedroDrivetrain extends SubsystemBase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    public Follower follower;

    private double speedModifier = FAST_SPEED;

    private static final double SLOW_SPEED = 0.3;
    private static final double FAST_SPEED = 1.0;

    public PedroDrivetrain(OpMode opMode) {
        leftFront = BarnRobot.getInstance().hardware.leftFrontDrivetrain;
        rightFront = BarnRobot.getInstance().hardware.rightFrontDrivetrain;
        leftBack = BarnRobot.getInstance().hardware.leftBackDrivetrain;
        rightBack = BarnRobot.getInstance().hardware.rightBackDrivetrain;
        initMotor(DcMotorSimple.Direction.REVERSE, leftFront);
        initMotor(DcMotorSimple.Direction.FORWARD, rightFront);
        initMotor(DcMotorSimple.Direction.REVERSE, leftBack);
        initMotor(DcMotorSimple.Direction.FORWARD, rightBack);
        follower = Constants.createFollower(opMode.hardwareMap);
    }

    private void initMotor(DcMotorSimple.Direction direction, DcMotor motor) {
        motor.setDirection(direction);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void driveFollower() {
        follower.setTeleOpDrive(
                BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier,
                -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier,
                -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7,
                false
        );
    }

    private void reset() {
        if (follower.isBusy()) {
            follower.breakFollowing();
        }
    }

    public RunCommand driveFollowerCommand() {
        reset();
        return new RunCommand(this::driveFollower, this);
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }

    private void alignTo(Pose pose) {
        Pose currentPose = follower.getPose();
        double targetHeading = Math.atan2(
                pose.getY() - currentPose.getY(),
                pose.getX() - currentPose.getX()
        );
        follower.holdPoint(new Pose(currentPose.getX(), currentPose.getY(), targetHeading));
    }

    public Command goTo(Pose pose) {
        reset();
        return new FollowPathCommand(
                follower,
                follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pose))
                        .setLinearHeadingInterpolation(follower.getHeading(), pose.getHeading())
                        .build()
        );
    }

    public Command hold() {
        reset();
        return new InstantCommand(() -> follower.holdPoint(follower.getPose()), this);
    }

    public Command face(Pose targetPose) {
        reset();
        return new InstantCommand(() ->alignTo(targetPose), this);
    }
}
