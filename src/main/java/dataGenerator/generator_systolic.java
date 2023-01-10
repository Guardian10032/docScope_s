package dataGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_systolic extends generator{
    public generator_systolic(long initialTime,String status) {
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
        if (fileIndex<25) return "systolic_normal1";
        else if (fileIndex<50) return "systolic_normal2";
        else if (fileIndex<75) return "systolic_low";
        else return "systolic_high";
    }
}
