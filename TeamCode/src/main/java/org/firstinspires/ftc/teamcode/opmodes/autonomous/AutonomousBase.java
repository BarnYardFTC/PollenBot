package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class AutonomousBase {
    static final Pose CENTER_POSE = new Pose(72.0, 72.0, Math.toRadians(90));
    static final Pose TARGET_POSE = new Pose(72.0, 82.0, Math.toRadians(90));

    static PathChain goTarget;

    static void buildPathChains(Follower follower) {
        goTarget = follower.pathBuilder()
                .addPath(new BezierLine(CENTER_POSE, TARGET_POSE))
                .setLinearHeadingInterpolation(CENTER_POSE.getHeading(), TARGET_POSE.getHeading())
                .build();
    }
}
