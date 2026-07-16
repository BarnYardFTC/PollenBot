package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpmodeData {
    public enum AllianceColor { RED, BLUE }
    public static AllianceColor color;
    public static Pose2D initialPose2D;
    //public static Pose initialPose = new Pose(initialPose2D.getX(DistanceUnit.INCH), initialPose2D.getY(DistanceUnit.INCH), initialPose2D.getHeading(AngleUnit.RADIANS));

}
