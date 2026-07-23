package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.content.pm.LauncherApps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@TeleOp(name = "Limelight Tuner", group = "tune")
public class LimelightTuner extends CommandOpMode {

    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void initialize() {
        robot.init(this);
        robot.limelight.pipelineSwitch(1);
        robot.limelight.start();
    }

    @Override
    public void run(){
        super.run();
        robot.periodic();

        sleep(2000);

        int bestExposure = 20;
        int bestGain = 0;
        int bestBlackLevel = 0;
        int bestDecimation = 1;
        double bestScore = -1.0;

        int[] testExposures = {20, 100, 200, 300, 400, 500, 600, 800, 1000};
        int[] testGains = {1, 2, 5, 6, 8 ,10};
        int[] testBlackLevels = {1, 3, 5 ,6, 8,10, 12, 15, 18, 20};
        int[] testDecimations = {1, 2};

        for (int exp : testExposures) {
            for (int gain : testGains) {
                for (int dec : testDecimations) {
                    for(int blk : testBlackLevels){
                        updateLimelightConfig(exp, gain, blk, dec);

                        sleep(300);
                        double targetArea = robot.limelight.getTa();

                        if (targetArea > bestScore && targetArea < 70.0) {
                            bestScore = targetArea;
                            bestExposure = exp;
                            bestGain = gain;
                            bestBlackLevel = blk;
                            bestDecimation = dec;
                        }

                        telemetry.addData("Testing Exposure", exp);
                        telemetry.addData("Testing Gain", gain);
                        telemetry.addData("Testing Decimation", dec);
                        telemetry.addData("Testing Black Level", blk);
                        telemetry.addData("Best Score Yet", bestScore);
                        telemetry.update();
                    }

                }
            }
        }

        updateLimelightConfig(bestExposure, bestGain, bestBlackLevel ,bestDecimation);

        while (opModeIsActive()) {
            telemetry.clearAll();
            telemetry.addData("Locked Exposure", bestExposure);
            telemetry.addData("Locked Gain", bestGain);
            telemetry.addData("Locked Decimation", bestDecimation);
            telemetry.addData("Locked Black Level", bestBlackLevel);
            telemetry.addData("Final Target Area (ta)", bestScore);
            telemetry.update();
        }
    }



    private void updateLimelightConfig(int exposure, int gain, int blackLevel, int decimation) {
        try {
            URL url = new URL("http://172.28.0.1:5807/update-pipeline");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonPayload = "{\"exposure\": " + exposure + ", \"gain\": " + gain + ", \"blacklevel\": " + blackLevel + ", \"decimation\": " + decimation + "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            con.getResponseCode();
        } catch (Exception e) {
            telemetry.clearAll();
            telemetry.addData("Error", e);
        }
    }
}
