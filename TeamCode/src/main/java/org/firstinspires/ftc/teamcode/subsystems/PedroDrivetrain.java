package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
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

public class PedroDrivetrain extends SubsystemBase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    public Follower follower;

    private double speedModifier;

    private final double SLOW_SPEED = 0.3;
    private final double FAST_SPEED = 1.0;

    private Pose trackingPose = null;


    public PedroDrivetrain(OpMode opMode) {
        speedModifier = FAST_SPEED;
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
        double stickTurn = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7;
        double turn = stickTurn;

        if (trackingPose != null) {
            Pose currentPose = follower.getPose();

            double targetHeading = Math.atan2(
                    trackingPose.getY() - currentPose.getY(),
                    trackingPose.getX() - currentPose.getX()
            );

            double headingError = targetHeading - currentPose.getHeading();

            while (headingError > Math.PI) headingError -= 2 * Math.PI;
            while (headingError < -Math.PI) headingError += 2 * Math.PI;

            turn = headingError * 2.0;
        }

        if (!follower.getTeleopDrive() && BarnRobot.getInstance().sticksUsed()) {
            follower.startTeleopDrive(true);
        }
        try {
            follower.setTeleOpDrive(
                    BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier,
                    -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier,
                    turn,
                    false
            );
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop drive", e);
        }
    }

    private void face(Pose pose) {
        Pose currentPose = follower.getPose();
        double targetHeading = Math.atan2(
                pose.getY() - currentPose.getY(),
                pose.getX() - currentPose.getX()
        );
        follower.holdPoint(new Pose(currentPose.getX(), currentPose.getY(), targetHeading));
    }

    public boolean isTracking() {
        return trackingPose != null || follower.isBusy() && !follower.getTeleopDrive();
    }

    public RunCommand driveFollowerCommand() {
        return new RunCommand(this::driveFollower, this);
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }

    public Command goToCommand(Pose pose) {
        return new FollowPathCommand(
                follower,
                follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pose))
                        .setLinearHeadingInterpolation(follower.getHeading(), pose.getHeading())
                        .build()
        );
    }

    public Command holdCommand() {
        return new InstantCommand(() -> follower.holdPoint(follower.getPose()), this);
    }

    public Command faceCommand(Pose targetPose) {
        return new InstantCommand(() -> face(targetPose), this);
    }

    public Command setTrackingPoseCommand(Pose pose) {
        return new InstantCommand(() -> trackingPose = pose, this);
    }

    public Command clearTrackingPoseCommand() {
        return new InstantCommand(() -> trackingPose = null, this);
    }
}
