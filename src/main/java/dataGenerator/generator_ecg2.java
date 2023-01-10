package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_ecg2 extends generatorHeartRelate{
    public generator_ecg2(long initialTime,String status) {
        super(initialTime,status);
        interval=2;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomHeartGenerator();
        if (fileHeartIndex<25) return "ecg2_normal1";
        else if (fileHeartIndex<50) return "ecg2_normal2";
        else if (fileHeartIndex<75) return "ecg2_high";
        else return "ecg2_low";
    }
}
