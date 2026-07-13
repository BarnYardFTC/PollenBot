package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class Pinpoint {
    private final GoBildaPinpointDriver pinpoint;
    public Pinpoint() {
        pinpoint = BarnRobot.getInstance().hardware.pinpoint;
        pinpoint.resetPosAndIMU();
    }

    public void update(){
        pinpoint.update();
    }
}
