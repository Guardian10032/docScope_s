package dataGenerator;

import java.sql.Timestamp;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_ecg1 extends generator{
    int fs;
    public generator_ecg1(long initialTime) {
        super(initialTime);
    }
    public String fileSelector() {
        super.fileSelector();
        if (fileIndex<40) return "ecg1_normal1";
        else if (fileIndex<80) return "ecg1_normal2";
        else if (fileIndex<90) return "ecg1_high";
        else return "ecg1_low";
    }
    public List<Double> outputValues(int fs){
        super.outputValues(fs);
        return Former.subList(index1,index2);
    }

}
