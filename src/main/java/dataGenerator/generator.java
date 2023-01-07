package dataGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

abstract class generator {
    Data data=new Data();
    List<Double> Former;
    List<Double> Latter;
    int size;
    long previousTime;
    long initialTime;
    int index1=0;
    int index2=0;
    String folder;
    int fileIndex;
    public generator(long initialTime){
        this.initialTime=initialTime;
    }
}
