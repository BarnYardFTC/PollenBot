package org.firstinspires.ftc.teamcode.general;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Scoop;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class BarnRobot {
    private static BarnRobot instance;

    public Telemetry telemetry;

    public GamepadEx gamepadEx1;

    public Hardware hardware;

    public Drivetrain drive;

    public Pinpoint pinpoint;

    public Intake intake;
    public Transfer transfer;

    public Scoop scoop;

    public static boolean isRobotInitialized = false;

    public static synchronized BarnRobot getInstance() {
        if (instance == null) {
            instance = new BarnRobot();
            isRobotInitialized = true;
        }
        return instance;
    }

    public void init(OpMode opMode){
        hardware = new Hardware(opMode.hardwareMap);
        gamepadEx1 = new GamepadEx(opMode.gamepad1);
        pinpoint = new Pinpoint();
        drive = new Drivetrain(opMode);
        intake = new Intake();
        transfer = new Transfer();
        scoop = new Scoop();
        telemetry = opMode.telemetry;
    }

    public void periodic() {
        telemetry.update();
        drive.follower.update();
    }
}
