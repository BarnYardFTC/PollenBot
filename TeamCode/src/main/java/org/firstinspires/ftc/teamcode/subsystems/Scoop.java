//package org.firstinspires.ftc.teamcode.subsystems;
//
//import com.qualcomm.robotcore.hardware.Servo;
//import com.seattlesolvers.solverslib.command.Command;
//import com.seattlesolvers.solverslib.command.InstantCommand;
//import com.seattlesolvers.solverslib.command.SubsystemBase;
//
//import org.firstinspires.ftc.teamcode.general.BarnRobot;
//
//public class Scoop extends SubsystemBase {
//    private final Servo scoopServo;
//
//    private final double SCOOP_COLLECT = 0.0;
//    private final double SCOOP_DUMP = 1.0;
//
//    public Scoop() {
//        scoopServo = BarnRobot.getInstance().hardware.scoop;
//        scoopServo.setDirection(Servo.Direction.FORWARD);
//        scoopServo.setPosition(SCOOP_COLLECT);
//    }
//
//    public Command collectCommand() {
//        return new InstantCommand(() -> scoopServo.setPosition(SCOOP_COLLECT));
//    }
//
//    public Command dumpCommand() {
//        return new InstantCommand(() -> scoopServo.setPosition(SCOOP_DUMP));
//    }
//}
