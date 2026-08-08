package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
// ega is great ega is evrything
public class PollenBase {
//    public double Cart = 3;
    static final Pose START_POSE = new Pose(62.530605647721366,133.70954444209715);
    static final Pose LEFT_MIDDLE_INTAKE_1_CURVE = new Pose(61.46142162818956, 35.849331713244226);
    static final Pose LEFT_MIDDLE_INTAKE_2_CURVE = new Pose(86.82563791008505, 23.210814094775206);
    static final Pose LEFT_MIDDLE_INTAKE_3_CURVE = new Pose(58.11300121506683, 16.161603888213854);
    static final Pose LEFT_MIDDLE_INTAKE_4_CURVE = new Pose(104.01883353584448, 33.52673147023086);
    static final Pose LEFT_MIDDLE_INTAKE_5_CURVE = new Pose(94.5987241798299, 51.807411907654924);
    static final Pose LEFT_MIDDLE_INTAKE = new Pose(93.99696233292833, 61.27399756986633);
    static final Pose OFFLOAD = new Pose(94.24104485807864,5.819753918113214,Math.toRadians(90));
    static final Pose RIGHT_INTAKE_1_CURVE = new Pose (130.1857698273343,20.96063918293202);
    static final Pose RIGHT_INTAKE_2_CURVE = new Pose (113.86985245187864,56.03475825948124);
    static final Pose RIGHT_INTAKE = new Pose(118.80364181967619,63.31233854252604,Math.toRadians(90));

    static PathChain leftMiddleIntake , offloadOne,rightIntake, offloadTwo;

    static void buildPathChains(Follower follower) {
         leftMiddleIntake= follower.pathBuilder()
                .addPath(new BezierCurve(START_POSE,
                        LEFT_MIDDLE_INTAKE_1_CURVE,
                        LEFT_MIDDLE_INTAKE_2_CURVE,
                        LEFT_MIDDLE_INTAKE_3_CURVE,
                        LEFT_MIDDLE_INTAKE_4_CURVE,
                        LEFT_MIDDLE_INTAKE_5_CURVE,
                        LEFT_MIDDLE_INTAKE))
                .setTangentHeadingInterpolation()
                .build();

        offloadOne = follower.pathBuilder()
                .addPath(new BezierLine(LEFT_MIDDLE_INTAKE,OFFLOAD))
                .setConstantHeadingInterpolation(OFFLOAD.getHeading())
                .build();

        rightIntake = follower.pathBuilder()
                .addPath(new BezierCurve(OFFLOAD,RIGHT_INTAKE_1_CURVE,RIGHT_INTAKE_2_CURVE,RIGHT_INTAKE))
                .setConstantHeadingInterpolation(OFFLOAD.getHeading())
                .build();

        offloadTwo = follower.pathBuilder()
                .addPath(new BezierLine(RIGHT_INTAKE, OFFLOAD))
                .setConstantHeadingInterpolation(RIGHT_INTAKE.getHeading())
                .build();
    }

}
