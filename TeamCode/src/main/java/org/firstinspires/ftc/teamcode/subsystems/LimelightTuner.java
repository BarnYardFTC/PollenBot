package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@TeleOp(name = "Limelight Tuner", group = "Tuners")
public class LimelightTuner extends LinearOpMode {

    private final BarnRobot robot = BarnRobot.getInstance();

    @Override
    public void runOpMode() {
        robot.limelight.pipelineSwitch(1);
        robot.limelight.start();

        waitForStart();

        if(opModeIsActive()){
            int bestExposure = 20;
            int bestGain = 0;
            int bestBlackLevel = 0;
            int bestDecimation = 1;
            double bestScore = -1.0;

            //TODO: Check for steps sizes on limelight local
            int[] testExposures = {};
            int[] testGains = {};
            int[] testBlackLevels = {};
            int[] testDecimations = {};

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

    }

    private void updateLimelightConfig(int exposure, int gain, int blackLevel, int decimation) {
        try {
            URL url = new URL("http://-/update-pipeline");   //TODO: Change limelight ip
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
            telemetry.addLine("Error");
        }
    }
}
