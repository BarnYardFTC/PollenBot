package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

public class PedroDrivetrain extends SubsystemBase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    public Follower follower;

    private double speedModifier = FAST_SPEED;

    private static final double SLOW_SPEED = 0.5;
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
        follower.update();
        follower.setTeleOpDrive(
                BarnRobot.getInstance().gamepadEx1.getLeftY() * speedModifier,
                -BarnRobot.getInstance().gamepadEx1.getLeftX() * speedModifier,
                -BarnRobot.getInstance().gamepadEx1.getRightX() * speedModifier,
                false
        );
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
}
