package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@Configurable
public class Scoop extends SubsystemBase {
    private final Servo rightScoopServo;
    private final Servo leftScoopServo;

    public static double SCOOP_COLLECT = 0.05;
    public static double SCOOP_DUMP = 1;

    public Scoop() {
        leftScoopServo = BarnRobot.getInstance().hardware.leftScoopServo;
        rightScoopServo = BarnRobot.getInstance().hardware.rightScoopServo;

        rightScoopServo.setDirection(Servo.Direction.FORWARD);
        leftScoopServo.setDirection(Servo.Direction.REVERSE);

        setPosition(SCOOP_COLLECT);
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

    public double getRightPos() {
        return rightScoopServo.getPosition();
    }

    public double getLeftPos() {
        return leftScoopServo.getPosition();
    }
}