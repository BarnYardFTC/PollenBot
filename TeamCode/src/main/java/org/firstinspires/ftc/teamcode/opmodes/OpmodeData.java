package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpmodeData {
    //keep it lightweight, no need for 100 lines
    public enum AllianceColor { RED, BLUE }
    public static AllianceColor color;
    public static Pose2D initialPose2D;
    public static void init(AllianceColor alColor, Pose2D initPose){
        color = alColor;
        initialPose2D = initPose;
    }
}
