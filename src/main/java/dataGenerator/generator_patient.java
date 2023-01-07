package dataGenerator;

import netRelated.netAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class generator_patient {
    public generator_temperature temperatureGenerator;
    public generator_systolic systolicGenerator;
    public generator_diastolic diastolicGenerator;
    public generator_ecg1 ecg1Generator;
    public generator_heart heartGenerator;
    public generator_patient(String ref){
        Random rand = new Random();
        int heartIndex=rand.nextInt(99);
        long initialTime= netAction.getInitialTime(ref);
        temperatureGenerator =new generator_temperature(initialTime);
        systolicGenerator =new generator_systolic(initialTime);
        diastolicGenerator =new generator_diastolic(initialTime);
        ecg1Generator =new generator_ecg1(initialTime,heartIndex);
        heartGenerator=new generator_heart(initialTime,heartIndex);

    }
    public List<List<Double>> outputValuesSlow(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();
        output.add(temperatureGenerator.outputValues(currentTime));
        output.add(systolicGenerator.outputValues(currentTime));
        output.add(diastolicGenerator.outputValues(currentTime));
        output.add(heartGenerator.outputValues(currentTime));
        return output;
    }
    public List<List<Double>> outputValuesFast(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();

        output.add(systolicGenerator.outputValues(currentTime));
        output.add(diastolicGenerator.outputValues(currentTime));
        return output;
    }
}
