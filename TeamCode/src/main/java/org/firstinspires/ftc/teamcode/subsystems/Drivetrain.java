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
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;
import org.firstinspires.ftc.teamcode.general.Hardware;

import java.util.function.BooleanSupplier;

public class Drivetrain extends SubsystemBase {
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;

    public Follower follower;

    private double speedModifier;
    private double turnPower = 0;

    private final double SLOW_SPEED = 0.3;
    private final double FAST_SPEED = 1.0;

    private Pose trackingPose = null;
//    private final PIDFController trackingPIDF;
//    private final PIDFController secondaryTrackingPIDF;


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
//        trackingPIDF = new PIDFController(Constants.followerConstants.coefficientsHeadingPIDF);
//        secondaryTrackingPIDF = new PIDFController(Constants.followerConstants.coefficientsSecondaryHeadingPIDF);
    }

    private void initMotor(DcMotorSimple.Direction direction, DcMotor motor) {
        motor.setDirection(direction);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

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

    public double getSpeedModifier() {
        return speedModifier;
    }

    public RunCommand driveCommand() {
        return new RunCommand(this::drive, this);
    }

//    private void driveFollower() {
//        double x = BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier;
//        double y = -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier;
//
//        double turn;
//        double stickTurn = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7;
//
//        if (trackingPose != null && Math.abs(stickTurn) < 0.1) {
//            Pose currentPose = follower.getPose();
//            double targetAngle = Math.atan2(
//                    trackingPose.getY() - currentPose.getY(),
//                    trackingPose.getX() - currentPose.getX()
//            );
//
//            double headingError = MathFunctions.normalizeAngleSigned(targetAngle - currentPose.getHeading());
//
//            follower.setHeadingPIDFCoefficients(new PIDFCoefficients(0, 0, 0, 0));
//
//            if (Math.abs(headingError) < Constants.followerConstants.headingPIDFSwitch && Constants.followerConstants.useSecondaryHeadingPIDF) {
//                secondaryTrackingPIDF.updateError(headingError);
//                secondaryTrackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
//                turn = secondaryTrackingPIDF.run();
//            } else {
//                trackingPIDF.updateError(headingError);
//                trackingPIDF.updateFeedForwardInput(MathFunctions.getTurnDirection(currentPose.getHeading(), targetAngle));
//                turn = trackingPIDF.run();
//            }
//        } else {
//            if (trackingPose != null) {
//                trackingPose = null;
//                follower.setHeadingPIDFCoefficients(Constants.followerConstants.coefficientsHeadingPIDF);
//            }
//            turn = stickTurn;
//        }
//
//        if (!follower.getTeleopDrive() && (BarnRobot.getInstance().sticksUsed() || trackingPose != null)) {
//            follower.startTeleopDrive(true);
//        }
//        try {
//            follower.setTeleOpDrive(x, y, turn, false);
//        } catch (Exception e) {
//            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
//        }
//    }

    private void drivePollen() {
        double x = BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier;
        double y = -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier;
        double turn = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7;

        if (!follower.getTeleopDrive() && BarnRobot.getInstance().sticksUsed()) {
            follower.startTeleopDrive(true);
        }
        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
        }
    }

    public void displayPositionTelemetry() {
        BarnRobot.getInstance().telemetry.addData("pos x: ", follower.getPose().getX());
        BarnRobot.getInstance().telemetry.addData("pos y: ", follower.getPose().getY());
        BarnRobot.getInstance().telemetry.addData("turn: ", Math.toDegrees(follower.getPose().getHeading()));
    }

//    private void driveAutoAlignment() {
//        double x = BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier;
//        double y = -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier;
//
//        if (!follower.getTeleopDrive() && BarnRobot.getInstance().sticksUsed()) {
//            follower.startTeleopDrive(true);
//        }
//        try {
//            follower.setTeleOpDrive(x, y, turnPower, false);
//        } catch (Exception e) {
//            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
//        }
//    }
//
//    private void face(Pose pose) {
//        Pose currentPose = follower.getPose();
//        double targetHeading = Math.atan2(
//                pose.getY() - currentPose.getY(),
//                pose.getX() - currentPose.getX()
//        );
//        follower.holdPoint(new Pose(currentPose.getX(), currentPose.getY(), targetHeading));
//    }
//
//    private void straighten() {
//        Pose currentPose = follower.getPose();
//        follower.holdPoint(new Pose(currentPose.getX(), currentPose.getY(), Math.toRadians(90)));
//    }

//    public RunCommand driveFollowerCommand() {
//        return new RunCommand(this::driveFollower, this);
//    }
//
    public RunCommand drivePollenCommand() {
        return new RunCommand(this::drivePollen, this);
    }
//
//    public RunCommand driveAutoAlignCommand() {
//        return new RunCommand(this::driveAutoAlignment, this);
//    }
//
//    public Command setTurnPower(double power){
//        return new InstantCommand(() -> turnPower = power);
//    }

//    public Command setAlign() {
//        return new ConditionalCommand(
//                setTurnPower(0.325),
//
//                new RunCommand(
//                        () -> {
//                            double tx = BarnRobot.getInstance().limelight.getTx();
//                            turnPower = -tx * 0.02;
//                        },
//                        this
//                ),
//
//                () -> BarnRobot.getInstance().limelight.getTx() == 0
//        );
//    }

//    public Command setNormal(){
//        return new InstantCommand(() -> turnPower = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7);
//    }
//
    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }
//
    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }
//
    public Command goToCommand(Pose pose) {
        return new FollowPathCommand(
                follower,
                follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pose))
                        .setLinearHeadingInterpolation(follower.getHeading(), pose.getHeading())
                        .build()
        );
    }
//
//    public Command holdCommand() {
//        return new InstantCommand(() -> follower.holdPoint(follower.getPose()), this);
//    }
//
//    public Command straightenCommand() {
//        return new InstantCommand(this::straighten, this);
//    }
//
//    public Command faceCommand(Pose targetPose) {
//        return new InstantCommand(() -> face(targetPose), this);
//    }
//
//    public Command setTrackingPoseCommand(Pose pose) {
//        return new InstantCommand(() -> trackingPose = pose, this);
//    }
//
//    public Command clearTrackingPoseCommand() {
//        return new InstantCommand(() -> trackingPose = null, this);
//    }


//    public void checkTx() {
//        if(follower.getPose()==null){
//            return;}
//        double x = BarnRobot.getInstance().limelight.getTx();
//        follower.setHeadingPIDFCoefficients(Constants.followerConstants.coefficientsHeadingPIDF);
//        if (!follower.getTeleopDrive()) {
//            follower.startTeleopDrive(true);}
//        if(x==0){
//            if(follower.getHeading()<3 && follower.getHeading()>0){
//            follower.setTeleOpDrive(0,0,-0.450,false);}
//            else{
//                follower.setTeleOpDrive(0,0,0.450,false);
//            }
//        }
//        else {
//            double turnPower = -x * 0.02;
//            follower.setTeleOpDrive(0, 0, turnPower, false);
//        }
//
//
//    }

//    public RunCommand limelightAutoAlign(){
//        return new RunCommand(() -> checkTx());
//    }

}
