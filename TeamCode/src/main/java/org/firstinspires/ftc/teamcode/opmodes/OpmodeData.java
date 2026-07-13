package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpmodeData {
    //keep it lightweight, no need for 100 lines
    public enum AllianceColor { RED, BLUE }
    public AllianceColor color;
    public Pose2D initialPose2D;
    public OpmodeData(AllianceColor color, Pose2D initialPose2D) {
        this.color = color;
        this.initialPose2D = initialPose2D;
    }
}
