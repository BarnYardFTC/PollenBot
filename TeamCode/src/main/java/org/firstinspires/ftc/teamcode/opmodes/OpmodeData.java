package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpmodeData {
    public enum AllianceColor { RED, BLUE }
    public static AllianceColor color;
    public static Pose2D pose2DSetter = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
    public static Pose2D initialPose2D;
//    public static Pose initialPose = new Pose(pose2DSetter.getX(DistanceUnit.INCH), pose2DSetter.getY(DistanceUnit.INCH), pose2DSetter.getHeading(AngleUnit.RADIANS));

    public static void updatePose(Pose2D pose2D){
        initialPose2D = pose2D;
    }
}
