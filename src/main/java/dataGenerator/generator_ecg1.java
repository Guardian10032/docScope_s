package dataGenerator;

import java.sql.Timestamp;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_ecg1 extends generatorHeartRelate{
    public generator_ecg1(long initialTime,String status) {
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
        if (fileHeartIndex<25) return "ecg1_normal1";
        else if (fileHeartIndex<50) return "ecg1_normal2";
        else if (fileHeartIndex<75) return "ecg1_high";
        else return "ecg1_low";
    }

}
