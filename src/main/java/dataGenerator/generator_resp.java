package dataGenerator;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_resp extends generatorRespiratoryRelate{
    public generator_resp(long initialTime,String status) {
        super(initialTime,status);
        interval=2;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomRespiratoryGenerator();
        if (fileRespiratoryIndex<25) return "resp_normal1";
        else if (fileRespiratoryIndex<50) return "resp_normal2";
        else if (fileRespiratoryIndex<75) return "resp_high";
        else return "resp_low";
    }
}
