package dataGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class generator_temperature extends generator{
    List<List<String>> partList=new ArrayList<>();
    generator_temperature() {
        initialTime=new Timestamp(System.currentTimeMillis()).getTime();
        previousTime=initialTime;
        partList.add(Arrays.asList("temperature1_high","temperature1_normal"));
        partList.add(Arrays.asList("temperature2_high","temperature2_normal"));
        partList.add(Arrays.asList("temperature3_normal1","temperature3_normal2"));
    }
    public String fileSelector(int part) {
        super.fileSelector();
        if (fileIndex<50) return partList.get(part).get(0);
        else return partList.get(part).get(1);
    }
}
