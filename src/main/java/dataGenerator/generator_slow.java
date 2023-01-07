package dataGenerator;

import netRelated.netAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class generator_slow {
    public generator_temperature temperatureGenerator;
    public generator_systolic systolicGenerator;
    public generator_diastolic diastolicGenerator;
    public generator_slow(){
        long initialTime= netAction.getInitialTime();
        temperatureGenerator =new generator_temperature(initialTime);
        systolicGenerator =new generator_systolic(initialTime);
        diastolicGenerator =new generator_diastolic(initialTime);
    }
    public List<List<Double>> outputValues(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();
        output.add(temperatureGenerator.outputValues(currentTime));
        output.add(systolicGenerator.outputValues(currentTime));
        output.add(diastolicGenerator.outputValues(currentTime));
        return output;
    }
}
