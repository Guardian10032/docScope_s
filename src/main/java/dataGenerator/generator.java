package dataGenerator;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator {
    Data data=new Data();
    public List<Double> ecg1Former=new ArrayList<>();
    public List<Double> ecg1Latter;
    private int size;
    private long previousTime;
    private long initialTime;
    private int index1=0;
    private int index2=0;

    public generator(String reference,long initialTime) {
        this.initialTime=initialTime;
        previousTime=initialTime;
//        ecg1Former=data.loadFile("ecg1_normal1");
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);ecg1Former.add(1.0);
        ecg1Latter=data.loadFile("ecg1_normal2");
        size=ecg1Former.size();
        ecg1Former.addAll(ecg1Latter);
        System.out.println("1");
    }
    private String fileSelector() {
        Random rand = new Random();
        int fileIndex=rand.nextInt(99);
        if (fileIndex<40) return "ecg1_normal1";
        else if (fileIndex<80) return "ecg1_normal2";
        else if (fileIndex<90) return "ecg1_high";
        else return "ecg1_low";
    }
    public List<Double> outputValues(){
        if (index2>size-1){
            initialTime=initialTime+2*size;
            ecg1Former.subList(0,size-1).clear();
            size=ecg1Former.size();
            ecg1Latter=data.loadFile(fileSelector());
            ecg1Former.addAll(ecg1Latter);
            System.out.println("1");
        }
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        index1= (int) ceil((((Long)previousTime)-((Long)initialTime))/2);
        index2= (int) ceil((((Long)currentTime)-((Long)initialTime))/2);
        previousTime=currentTime;
        System.out.println(ecg1Former.subList(index1,index2));
        return ecg1Former.subList(index1,index2);
    }

}
