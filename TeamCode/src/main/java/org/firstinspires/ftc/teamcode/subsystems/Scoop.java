package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class Scoop extends SubsystemBase {
    private final Servo rightScoopServo;
    private final Servo leftScoopServo;

    // Constants should be static final and typically uppercase
    private static final double SCOOP_COLLECT = 0.05;
    private static final double SCOOP_DUMP = 0.6;

    public Scoop() {
        leftScoopServo = BarnRobot.getInstance().hardware.leftScoopServo;
        rightScoopServo = BarnRobot.getInstance().hardware.rightScoopServo;

        rightScoopServo.setDirection(Servo.Direction.FORWARD);
        leftScoopServo.setDirection(Servo.Direction.REVERSE);
    }

    public void setPosition(double position) {
        rightScoopServo.setPosition(position);
        leftScoopServo.setPosition(position);
    }

    public Command collectCommand() {
        return new InstantCommand(() -> setPosition(SCOOP_COLLECT), this);
    }

    public Command dumpCommand() {
        return new InstantCommand(() -> setPosition(SCOOP_DUMP), this);
    }

    public Servo getLeftScoopServo() {
        return leftScoopServo;
    }

    public Servo getRightScoopServo() {
        return rightScoopServo;
    }
}