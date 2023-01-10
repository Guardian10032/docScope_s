package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_respiratoryRate extends generatorRespiratoryRelate{
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
