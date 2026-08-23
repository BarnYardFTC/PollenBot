package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
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

    private boolean fieldOriented = true;

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

    private void drivePollen() {
        double x = BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier;
        double y = -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier;
        double turn = -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier * 0.7;

        if (!follower.getTeleopDrive()) {
            follower.startTeleopDrive(true);
        }
        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
        }
    }

    private void hybridDrive() {
        if (fieldOriented) {
            drivePollen();
        }
        else {
            drive();
        }
    }

    private void changeFieldoriented() {
        fieldOriented = !fieldOriented;
        BarnRobot.getInstance().pinpoint.get().resetPosAndIMU();
    }

    public boolean getFieldOriented() {
        return fieldOriented;
    }

    public RunCommand driveCommand() {
        return new RunCommand(this::drive, this);
    }

    public RunCommand drivePollenCommand() {
        return new RunCommand(this::drivePollen, this);
    }

    public RunCommand hybridDriveCommand() {
        return new RunCommand(this::hybridDrive, this);
    }

    public Command changeFieldOrientedCommand() {
        return new InstantCommand(this::changeFieldoriented, this);
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }
}
