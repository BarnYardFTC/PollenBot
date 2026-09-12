package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

public class Drivetrain extends SubsystemBase {
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;

    public Follower follower;

    private double speedModifier;
    private final double SLOW_SPEED = 0.3;
    private final double FAST_SPEED = 1.0;

    private Pose trackingPose = null;
    private final PIDFController trackingPIDF;
    private final PIDFController secondaryTrackingPIDF;

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

    /*
     * CLASSIC DRIVE SECTION
     * This provides raw motor power control without Pedro Pathing.
     */

    /**
     * Drives the robot using raw mecanum motor power calculations.
     */
    private void drive() {
        GamepadEx gamepadEx = BarnRobot.getInstance().gamepadEx1;
        double lf = gamepadEx.getLeftY() - gamepadEx.getLeftX() + gamepadEx.getRightX();
        double rf = gamepadEx.getLeftY() + gamepadEx.getLeftX() - gamepadEx.getRightX();
        double lb = gamepadEx.getLeftY() + gamepadEx.getLeftX() + gamepadEx.getRightX();
        double rb = gamepadEx.getLeftY() - gamepadEx.getLeftX() - gamepadEx.getRightX();
        leftFront.setPower(lf * speedModifier);
        rightFront.setPower(rf * speedModifier);
        leftBack.setPower(lb * speedModifier);
        rightBack.setPower(rb * speedModifier);
    }

    /**
     * Returns a command that runs the classic raw power drive.
     */
    public RunCommand driveCommand() {
        return new RunCommand(this::drive, this);
    }

    /*
     * PEDRO PATHING DRIVE SECTION
     * This uses the Follower to drive and includes auto-alignment logic.
     */

    /**
     * Drives the robot using Pedro Pathing.
     * If a trackingPose is set, the robot will automatically rotate to face it.
     */
    private void driveFollower() {
        GamepadEx gp = BarnRobot.getInstance().gamepadEx1;
        double x = gp.getLeftY() * speedModifier;
        double y = -gp.getLeftX() * speedModifier;
        double stickTurn = -gp.getRightX() * speedModifier * 0.7;

        double turn;

        // Auto-align logic: calculate heading if tracking is active and driver isn't turning
        if (trackingPose != null && Math.abs(stickTurn) < 0.1) {
            turn = calculateAutoAlignTurn();
        } else {
            // Manual override: Clear tracking if the driver touches the right stick
            if (trackingPose != null) {
                trackingPose = null;
                // Restore original follower coefficients when manual control resumes
                follower.setHeadingPIDFCoefficients(Constants.followerConstants.coefficientsHeadingPIDF);
            }
            turn = stickTurn;
        }

        // Ensure follower is awake in Teleop mode if sticks are moved or tracking is active
        if (!follower.getTeleopDrive() && (BarnRobot.getInstance().sticksUsed() || trackingPose != null)) {
            follower.startTeleopDrive(true);
        }

        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
        }
    }

    /**
     * Helper to calculate the PID power needed to snap the robot to face the trackingPose.
     */
    private double calculateAutoAlignTurn() {
        Pose currentPose = follower.getPose();
        double targetAngle = Math.atan2(
                trackingPose.getY() - currentPose.getY(),
                trackingPose.getX() - currentPose.getX()
        );

        double headingError = MathFunctions.normalizeAngleSigned(targetAngle - currentPose.getHeading());

        // We zero out internal PID so it doesn't conflict with our manual control input
        follower.setHeadingPIDFCoefficients(new PIDFCoefficients(0, 0, 0, 0));

        // Switch between primary and aggressive secondary PID depending on error size
        if (Math.abs(headingError) < Constants.followerConstants.headingPIDFSwitch && Constants.followerConstants.useSecondaryHeadingPIDF) {
            secondaryTrackingPIDF.updateError(headingError);
            secondaryTrackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
            return secondaryTrackingPIDF.run();
        } else {
            trackingPIDF.updateError(headingError);
            trackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
            return trackingPIDF.run();
        }
    }

    /**
     * Returns a command that runs the Pedro Pathing drive with auto-alignment.
     */
    public RunCommand driveFollowerCommand() {
        return new RunCommand(this::driveFollower, this);
    }

    public Command setTrackingPoseCommand(Pose pose) {
        return new InstantCommand(() -> trackingPose = pose, this);
    }

    public Command clearTrackingPoseCommand() {
        return new InstantCommand(() -> trackingPose = null, this);
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }
}