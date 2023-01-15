package dataGenerator;

/**
 * a simulation of the machine measuring ecg lead I signal
 */
public class generator_ecg1 extends generatorHeartRelate{
    /**
     * load two files based on status
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
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
