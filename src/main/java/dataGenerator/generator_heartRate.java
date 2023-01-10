package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_heartRate extends generatorHeartRelate{
    public generator_heartRate(long initialTime,String status) {
        super(initialTime,status);
        interval=1000;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomHeartGenerator();
        if (fileHeartIndex<25) return "heart_normal1";
        else if (fileHeartIndex<50) return "heart_normal2";
        else if (fileHeartIndex<75) return "heart_high";
        else return "heart_low";
    }
}
