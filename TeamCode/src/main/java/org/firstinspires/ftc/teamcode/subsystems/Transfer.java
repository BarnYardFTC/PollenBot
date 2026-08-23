package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;


public class Transfer extends SubsystemBase {
    private final DcMotor transferTopMotor;
    private final DcMotor transferBottomMotor;

    public Transfer() {
        transferTopMotor = BarnRobot.getInstance().hardware.transferTopMotor;
        transferTopMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferTopMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        transferTopMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        transferBottomMotor = BarnRobot.getInstance().hardware.transferBottomMotor;
        transferBottomMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferBottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        transferBottomMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Command enableCommand(){
        return new ParallelCommandGroup(
                new InstantCommand(() -> transferTopMotor.setPower(1)),
                new InstantCommand(() -> transferBottomMotor.setPower(1)));
    }

    public Command disableCommand(){
        return new ParallelCommandGroup(
                new InstantCommand(() -> transferTopMotor.setPower(0)),
                new InstantCommand(() -> transferBottomMotor.setPower(0)));
    }
}
