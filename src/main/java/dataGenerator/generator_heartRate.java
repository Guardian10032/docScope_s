package dataGenerator;

/**
 * a simulation of the machine measuring hear rate signal
 */
public class generator_heartRate extends generatorHeartRelate{
    /**
     * load two files based on status
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
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
