package dataGenerator;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_diastolic extends generator{
    public generator_diastolic(long initialTime,String status) {
        super(initialTime,status);
        interval=1000;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        Random rand = new Random();
        fileIndex=rand.nextInt(randomLimit);
        if (fileIndex<25) return "diastolic_normal1";
        else if (fileIndex<50) return "diastolic_normal2";
        else if (fileIndex<75) return "diastolic_low";
        else return "diastolic_high";
    }
}
