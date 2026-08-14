package org.firstinspires.ftc.teamcode.general;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    private HardwareMap hwMap = hardwareMap;

    public static final String LEFT_FRONT_DRIVETRAIN_CONFIG_NAME = "leftFrontDrivetrain";
    public static final String RIGHT_FRONT_DRIVETRAIN_CONFIG_NAME = "rightFrontDrivetrain";
    public static final String LEFT_BACK_DRIVETRAIN_CONFIG_NAME = "leftBackDrivetrain";
    public static final String RIGHT_BACK_DRIVETRAIN_CONFIG_NAME = "rightBackDrivetrain";

    private static final String PINPOINT_CONFIG_NAME = "pinpoint";

    public static final String LIMELIGHT_CONFIG_NAME = "limelight";

    public static final String INTAKE_CONFIG_NAME = "intakeMotor";
    public static final String TRANSFER_CONFIG_NAME = "transferMotor";


    public static final String LEFT_SCOOP_CONFIG_NAME = "leftScoopServo";
    public static final String RIGHT_SCOOP_CONFIG_NAME = "rightScoopServo";

    public Limelight3A limelight;

    public GoBildaPinpointDriver pinpoint;

    public DcMotor leftFrontDrivetrain;
    public DcMotor rightFrontDrivetrain;
    public DcMotor leftBackDrivetrain;
    public DcMotor rightBackDrivetrain;

    public DcMotor intake;
    public DcMotor transfer;

    public Servo leftScoopServo;
    public Servo rightScoopServo;

    public Hardware(HardwareMap hwMap) {
        this.hwMap = hwMap;
        initMotors();
        initServos();
//        initSensors();
    }

    private void initMotors() {
        leftFrontDrivetrain = hwMap.get(DcMotor.class, LEFT_FRONT_DRIVETRAIN_CONFIG_NAME);
        rightFrontDrivetrain = hwMap.get(DcMotor.class, RIGHT_FRONT_DRIVETRAIN_CONFIG_NAME);
        leftBackDrivetrain = hwMap.get(DcMotor.class, LEFT_BACK_DRIVETRAIN_CONFIG_NAME);
        rightBackDrivetrain = hwMap.get(DcMotor.class, RIGHT_BACK_DRIVETRAIN_CONFIG_NAME);

        intake = hwMap.get(DcMotor.class, INTAKE_CONFIG_NAME);
        transfer = hwMap.get(DcMotor.class, TRANSFER_CONFIG_NAME);
    }

    public void initServos() {
        leftScoopServo = hwMap.get(Servo.class, LEFT_SCOOP_CONFIG_NAME);
        rightScoopServo = hwMap.get(Servo.class, RIGHT_SCOOP_CONFIG_NAME);
    }

    private void initSensors(){
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, PINPOINT_CONFIG_NAME);
        limelight = hwMap.get(Limelight3A.class, LIMELIGHT_CONFIG_NAME);
    }
}