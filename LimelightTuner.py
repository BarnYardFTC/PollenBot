import limelight
import limelightresults
import json
import time

def limelightTuning():
    disc_limelight = limelight.discover_limelights(debug=False)
    if not disc_limelight:
        print("No limelights found")
    else:
        print("discovered limelights" + str(disc_limelight))

    llAddr = disc_limelight[0]
    ll = limelight.Limelight(llAddr)

    ll.enable_websocket()
    ll.pipeline_switch(1)

    time.sleep(1)

    base_pipeline = {
        "pipeline_type": "pipe_fiducial",
        "fiducial_type": "aprilClassic36h11"
    }

    bestExposure = 1000
    bestGain = 30
    bestBlackLevel = 12
    bestScore = 0.0


    testExposures = [1000, 800, 600, 500, 400, 300, 200, 100, 20]
    testGains = [30, 25, 20, 15, 13, 10, 8, 6, 5, 2, 1]
    testBlackLevels = [12, 10, 8, 6, 5, 3, 1]

    for exp in testExposures:
        for gain in testGains:
            for blk in testBlackLevels:
                pipeline_config = base_pipeline.copy()
                pipeline_config['exposure'] = exp
                pipeline_config['lcgain'] = gain
                pipeline_config['black_level'] = blk

                ll.update_pipeline(json.dumps(pipeline_config))
                time.sleep(0.1)
                results = ll.get_results()
                parsed_results = limelightresults.parse_results(results)

                for fiducial in parsed_results.fiducialResults:
                    if fiducial.fiducial_id == 22:
                        if exp > bestExposure: bestExposure = exp
                        if gain > bestGain: bestGain = gain
                        if blk < bestBlackLevel: bestBlackLevel = blk

                        print("I see goal!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        print(len(fiducial.points))

                print("Testing exp: " + str(exp) + "\ngain: " + str(gain) + "\nblack level: " + str(blk))

    finalPiplineJson = {
        'exposure': bestExposure,
        'lcgain': bestGain,
        'black_level': bestBlackLevel,
    }

    ll.update_pipeline(json.dumps(finalPiplineJson),flush=1)
    print("Best exp: " + str(bestExposure) + "\nBest gain: " + str(bestGain) + "\nBest black level: " + str(bestBlackLevel)  + "\nBest score " + str(bestScore))
    ll.disable_websocket()

    print(bestExposure)
    print(bestGain)
    print(bestBlackLevel)
    print(bestScore)

if __name__ == "__main__":
    limelightTuning()
