package dataGenerator;

import java.util.Random;

/**
 * a simulation of the machine measuring systolic pressure
 */
public class generator_systolic extends generator{
    /**
     * load two files based on status
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
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
