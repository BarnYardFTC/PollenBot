package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
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

public class Drivetrain extends SubsystemBase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    public Follower follower;

    private double speedModifier;

    private final double SLOW_SPEED = 0.3;
    private final double FAST_SPEED = 1.0;

    private Pose trackingPose = null;
    private PIDFController trackingPIDF;
    private PIDFController secondaryTrackingPIDF;


    public Drivetrain(OpMode opMode) {
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
        trackingPIDF = new PIDFController(Constants.followerConstants.coefficientsHeadingPIDF);
        secondaryTrackingPIDF = new PIDFController(Constants.followerConstants.coefficientsSecondaryHeadingPIDF);
    }

    private void initMotor(DcMotorSimple.Direction direction, DcMotor motor) {
        motor.setDirection(direction);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void driveFollower() {
        double x = BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier;
        double y = -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier;

        double turn;
        double stickTurn = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7;

        if (trackingPose != null && Math.abs(stickTurn) < 0.1) {
            Pose currentPose = follower.getPose();
            double targetAngle = Math.atan2(
                    trackingPose.getY() - currentPose.getY(),
                    trackingPose.getX() - currentPose.getX()
            );

            double headingError = MathFunctions.normalizeAngleSigned(targetAngle - currentPose.getHeading());

            follower.setHeadingPIDFCoefficients(new PIDFCoefficients(0, 0, 0, 0));

            if (Math.abs(headingError) < Constants.followerConstants.headingPIDFSwitch && Constants.followerConstants.useSecondaryHeadingPIDF) {
                secondaryTrackingPIDF.updateError(headingError);
                secondaryTrackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
                turn = secondaryTrackingPIDF.run();
            } else {
                trackingPIDF.updateError(headingError);
                trackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
                turn = trackingPIDF.run();
            }
        } else {
            if (trackingPose != null) {
                trackingPose = null;
                follower.setHeadingPIDFCoefficients(Constants.followerConstants.coefficientsHeadingPIDF);
            }
            turn = stickTurn;
        }

        if (!follower.getTeleopDrive() && (BarnRobot.getInstance().sticksUsed() || trackingPose != null)) {
            follower.startTeleopDrive(true);
        }
        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
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
