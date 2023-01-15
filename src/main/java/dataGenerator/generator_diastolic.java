package dataGenerator;

import java.util.Random;

/**
 * a simulation of the machine measuring diastolic pressure
 */
public class generator_diastolic extends generator{
    /**
     * load two files based on status
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
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
