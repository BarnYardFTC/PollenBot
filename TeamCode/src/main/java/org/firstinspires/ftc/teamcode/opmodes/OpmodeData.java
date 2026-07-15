package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpmodeData {
    //keep it lightweight, no need for 100 lines
    public enum AllianceColor { RED, BLUE }
    public static AllianceColor color;
    public static Pose2D initialPose2D = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
    public static void init(AllianceColor alColor, Pose2D initPose){
        color = alColor;
        initialPose2D = initPose;
    }

    public static Pose2D simple2D(double x, double y, double turn) {
        return new Pose2D(DistanceUnit.INCH, x, y, AngleUnit.DEGREES, turn);
    }
}
