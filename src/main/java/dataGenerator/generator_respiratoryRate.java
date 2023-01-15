package dataGenerator;

/**
 * a simulation of the machine measuring respiratory rate
 */
public class generator_respiratoryRate extends generatorRespiratoryRelate{
    /**
     * load two files based on status
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
    public generator_respiratoryRate(long initialTime,String status) {
        super(initialTime, status);
        interval=1000;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomRespiratoryGenerator();
        if (fileRespiratoryIndex<25) return "respiratory_normal1";
        else if (fileRespiratoryIndex<50) return "respiratory_normal2";
        else if (fileRespiratoryIndex<75) return "respiratory_high";
        else return "respiratory_low";
    }
}
