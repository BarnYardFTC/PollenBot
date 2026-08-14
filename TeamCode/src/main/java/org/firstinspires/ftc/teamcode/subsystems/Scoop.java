package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class Scoop extends SubsystemBase {
    private final Servo leftServo;
    private final Servo rightServo;

    private final double SCOOP_COLLECT = 0.2;
    private final double SCOOP_DUMP = 0.4;

    public Scoop() {
        leftServo = BarnRobot.getInstance().hardware.leftScoopServo;
        leftServo.setDirection(Servo.Direction.FORWARD);
        leftServo.setPosition(SCOOP_COLLECT);
        rightServo = BarnRobot.getInstance().hardware.rightScoopServo;
        rightServo.setDirection(Servo.Direction.FORWARD);
        rightServo.setPosition(SCOOP_COLLECT);
    }

    private void collect() {
        leftServo.setPosition(SCOOP_COLLECT);
        rightServo.setPosition(SCOOP_COLLECT);
    }

    private void dump() {
        leftServo.setPosition(SCOOP_DUMP);
        rightServo.setPosition(SCOOP_DUMP);
    }

    public void debugPlus() {
        leftServo.setPosition(leftServo.getPosition() + 0.01);
        rightServo.setPosition(rightServo.getPosition() + 0.01);
    }

    public void debugMinus() {
        leftServo.setPosition(leftServo.getPosition() - 0.01);
        rightServo.setPosition(rightServo.getPosition() - 0.01);
    }

    public Command collectCommand() {
//        return new InstantCommand(this::collect, this);
        return new InstantCommand(this::debugPlus, this);
    }

    public Command dumpCommand() {
        return new InstantCommand(this::debugMinus, this);
    }

}
