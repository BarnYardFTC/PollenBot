package org.firstinspires.ftc.teamcode.general;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

public class BarnRobot {
    private static BarnRobot instance;

    public Telemetry telemetry;

    public GamepadEx gamepadEx1;

    public Hardware hardware;

    public Drivetrain drive;

    public Pinpoint pinpoint;

    public Limelight limelight;

    public static boolean isRobotInitialized = false;

    public static synchronized BarnRobot getInstance() {
        if (instance == null) {
            instance = new BarnRobot();
            isRobotInitialized = true;
        }
        return instance;
    }

    public boolean sticksUsed() {
        return Math.abs(gamepadEx1.getLeftX()) > 0.05
                || Math.abs(gamepadEx1.getLeftY()) > 0.05
                || Math.abs(gamepadEx1.getRightX()) > 0.05;
    }

    public void init(OpMode opMode){
        hardware = new Hardware(opMode.hardwareMap);
        gamepadEx1 = new GamepadEx(opMode.gamepad1);
        drive = new Drivetrain(opMode);
        pinpoint = new Pinpoint();
        limelight = new Limelight();
        telemetry = opMode.telemetry;
    }

    public void periodic() {
        telemetry.update();
        pinpoint.update();
        drive.follower.update();
    }
}
