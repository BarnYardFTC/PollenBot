package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;
import org.firstinspires.ftc.teamcode.subsystems.PedroDrivetrain;

public class BarnRobot {
    private static BarnRobot instance;

    public Telemetry telemetry;

    public GamepadEx gamepadEx1;

    public OpmodeData opmodeData;

    public Hardware hardware;

    public PedroDrivetrain drive;

    public static boolean isRobotInitialized = false;

    public static synchronized BarnRobot getInstance() {
        if (instance == null) {
            instance = new BarnRobot();
            isRobotInitialized = true;
        }
        return instance;
    }

    public void init(OpMode opMode, OpmodeData opmodeData){
        hardware = new Hardware(opMode.hardwareMap);
        gamepadEx1 = new GamepadEx(opMode.gamepad1);
        drive = new PedroDrivetrain(opMode);
        this.opmodeData = opmodeData;
    }

    public void periodic() {
        telemetry.update();
        //pinpoint.update();
    }
}
