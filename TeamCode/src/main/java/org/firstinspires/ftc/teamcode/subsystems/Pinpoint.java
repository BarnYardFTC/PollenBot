package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.opmodes.OpmodeData;

public class Pinpoint {
    private final GoBildaPinpointDriver pinpoint;
    public Pinpoint() {
        pinpoint = BarnRobot.getInstance().hardware.pinpoint;
        pinpoint.resetPosAndIMU();
    }

    public void update(){
        pinpoint.update();
        OpmodeData.initialPose2D = pinpoint.getPosition();
    }

    public void setPosition(){
        pinpoint.setPosition(OpmodeData.initialPose2D);
    }

    public GoBildaPinpointDriver get() {
        return pinpoint;
    }
}
