package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

public class Pinpoint {
    private final GoBildaPinpointDriver pinpoint;
    public Pinpoint() {
        pinpoint = BarnRobot.getInstance().hardware.pinpoint;
    }

    public void update(){
        pinpoint.update();
    }

    public void setPosition(){
        pinpoint.setPosition(OpmodeData.initialPose2D);
    }

    public GoBildaPinpointDriver get() {
        return pinpoint;
    }
}
