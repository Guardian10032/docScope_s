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
    generator(long initialTime){
        this.initialTime=initialTime;
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    String fileSelector(){
        Random rand = new Random();
        fileIndex=rand.nextInt(99);
        return null;
    }
    List<Double> outputValues(int fs){
        if (index2>size-1){
            initialTime=initialTime+(1000/fs)*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        index1= (int) ceil((((Long)previousTime)-((Long)initialTime))/(1000/fs));
        index2= (int) ceil((((Long)currentTime)-((Long)initialTime))/(1000/fs));
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }
}
