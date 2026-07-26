package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
// ega is great ega is evrything
public class PollenBase {

    static final Pose FIRST_POSE = new Pose(70.7861482381531, 134.36998784933172, Math.toRadians(-95));
    static final Pose FIRST_TO_SECOND_1_CURVE = new Pose(61.46142162818956, 35.849331713244226);
    static final Pose FIRST_TO_SECOND_2_CURVE = new Pose(86.82563791008505, 23.210814094775206);
    static final Pose FIRST_TO_SECOND_3_CURVE = new Pose(58.11300121506683, 16.161603888213854);
    static final Pose FIRST_TO_SECOND_4_CURVE = new Pose(104.01883353584448, 33.52673147023086);
    static final Pose FIRST_TO_SECOND_5_CURVE = new Pose(94.5987241798299, 51.807411907654924);
    static final Pose SECOND_POSE = new Pose(93.99696233292833, 61.27399756986633, Math.toRadians(180));

    static final Pose THIRD_POSE = new Pose(94.24104485807864,5.819753918113214,90);
    static final Pose THIRD_TO_FOURTH_1_CURVE = new Pose (130.1857698273343,20.96063918293202);
    static final Pose THIRD_TO_FOURTH_2_CURVE = new Pose (113.86985245187864,56.03475825948124);
    static final Pose FOURTH_POSE = new Pose(118.80364181967619,63.31233854252604,90);
    static final Pose FIFTH_POSE = new Pose(94.12505323284077,6.068858911578701,90);
    static PathChain firstToSecond, secondToThird;
    static PathChain thirdToFourth;
    static PathChain fourthToFifth;
    static void buildPathChains(Follower follower) {
        firstToSecond = follower.pathBuilder()
                .addPath(new BezierCurve(FIRST_POSE,
                        FIRST_TO_SECOND_1_CURVE,
                        FIRST_TO_SECOND_2_CURVE,
                        FIRST_TO_SECOND_3_CURVE,
                        FIRST_TO_SECOND_4_CURVE,
                        FIRST_TO_SECOND_5_CURVE,
                        SECOND_POSE))
                .setTangentHeadingInterpolation()
                .build();
        secondToThird = follower.pathBuilder()
                .addPath(new BezierLine(SECOND_POSE,THIRD_POSE))                .setConstantHeadingInterpolation(SECOND_POSE.getHeading())
                .build();
        thirdToFourth = follower.pathBuilder()
                .addPath(new BezierCurve(THIRD_POSE,THIRD_TO_FOURTH_1_CURVE,THIRD_TO_FOURTH_2_CURVE,FOURTH_POSE))
                .setConstantHeadingInterpolation(THIRD_POSE.getHeading())
                .build();
        fourthToFifth = follower.pathBuilder()
                .addPath(new BezierLine(FOURTH_POSE, FIFTH_POSE))
                .setConstantHeadingInterpolation(FOURTH_POSE.getHeading())
                .build();
    }

}
