package org.firstinspires.ftc.teamcode.general;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Limelight1;

public class Hardware {

    private HardwareMap hwMap = hardwareMap;

    public static final String LEFT_FRONT_DRIVETRAIN_CONFIG_NAME = "leftFrontDrivetrain";
    public static final String RIGHT_FRONT_DRIVETRAIN_CONFIG_NAME = "rightFrontDrivetrain";
    public static final String LEFT_BACK_DRIVETRAIN_CONFIG_NAME = "leftBackDrivetrain";
    public static final String RIGHT_BACK_DRIVETRAIN_CONFIG_NAME = "rightBackDrivetrain";

    private static final String PINPOINT_CONFIG_NAME = "pinpoint";

    public static final String LIMELIGHT_CONFIG_NAME = "limelight";

    public Limelight1 limelight;

    public GoBildaPinpointDriver pinpoint;

    public DcMotor leftFrontDrivetrain;
    public DcMotor rightFrontDrivetrain;
    public DcMotor leftBackDrivetrain;
    public DcMotor rightBackDrivetrain;

    public Hardware(HardwareMap hwMap) {
        this.hwMap = hwMap;
        initMotors();
        initSensors();
    }

    private void initMotors() {
        leftFrontDrivetrain = hwMap.get(DcMotor.class, LEFT_FRONT_DRIVETRAIN_CONFIG_NAME);
        rightFrontDrivetrain = hwMap.get(DcMotor.class, RIGHT_FRONT_DRIVETRAIN_CONFIG_NAME);
        leftBackDrivetrain = hwMap.get(DcMotor.class, LEFT_BACK_DRIVETRAIN_CONFIG_NAME);
        rightBackDrivetrain = hwMap.get(DcMotor.class, RIGHT_BACK_DRIVETRAIN_CONFIG_NAME);
    }

    private void initSensors(){
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, PINPOINT_CONFIG_NAME);
        limelight = hwMap.get(Limelight3A.class, LIMELIGHT_CONFIG_NAME);
    }
}