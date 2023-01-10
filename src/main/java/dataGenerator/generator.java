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
    int fileIndex;
    int interval;
    int randomLimit;
    public generator(long initialTime,String status){
        this.initialTime=initialTime;
        if(status=="normal"){
            randomLimit=49;
        }else randomLimit=99;
    }
    public abstract String fileSelector();
    public List<Double> outputValues(long currentTime){
        if (index2>size-1){
            initialTime=initialTime+interval*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }

        index1= (int) ceil((previousTime-initialTime)/interval);
        index2= (int) ceil((currentTime-initialTime)/interval);
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }
}
