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


    private final double SCOOP_COLLECT = 0.05;
    private final double SCOOP_DUMP = 0.6;

    public Scoop() {
        leftScoopServo = BarnRobot.getInstance().hardware.leftScoopServo;
        rightScoopServo = BarnRobot.getInstance().hardware.rightScoopServo;
        rightScoopServo.setDirection(Servo.Direction.FORWARD);
        leftScoopServo.setDirection(Servo.Direction.REVERSE);
    }

    public Command collectCommand() {
        return new InstantCommand(() -> {
            rightScoopServo.setPosition(SCOOP_COLLECT);
            leftScoopServo.setPosition(SCOOP_COLLECT);
        });
    }

    public Command dumpCommand() {
        return new InstantCommand(() -> {
            rightScoopServo.setPosition(SCOOP_DUMP);
            leftScoopServo.setPosition(SCOOP_DUMP);
        });
    }

    public Servo getLeftScoopServo() {
        return leftScoopServo;
    }

    public Servo getRightScoopServo() {
        return rightScoopServo;
    }
}
