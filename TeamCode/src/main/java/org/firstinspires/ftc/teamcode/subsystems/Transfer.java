package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;


public class Transfer extends SubsystemBase {
    private final DcMotor transferMotor;

    public Transfer() {
        transferMotor = BarnRobot.getInstance().hardware.transfer;
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Command enableCommand(){
        return new InstantCommand(() -> transferMotor.setPower(1), this);
    }

    public Command disableCommand(){
        return new InstantCommand(() -> transferMotor.setPower(0), this);
    }
}
