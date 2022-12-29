import dataGenerator.generator_ecg1;

import java.sql.Timestamp;
import java.util.List;

public class Task implements Runnable{
    @Override
    public void run() {
        long timestamp=new Timestamp(System.currentTimeMillis()).getTime();
        new dataBase().setTimestamp(timestamp);
        generator_ecg1 ecg1Generator =new generator_ecg1(timestamp);
        while (true){
            try {
                Thread.sleep(5);
                List<Double> temp = ecg1Generator.outputValues();
                new dataBase().addData_ecg1(temp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
