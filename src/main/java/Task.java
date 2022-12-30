import dataGenerator.generator_ecg1;

import java.sql.Timestamp;
import java.util.List;

public class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("in task");
        generator_ecg1 ecg1Generator =new generator_ecg1();
        while (true){
            try {
                Thread.sleep(5);
                List<Double> temp = ecg1Generator.outputValues(500);
                long time=new Timestamp(System.currentTimeMillis()).getTime();
                new dataBase().addData_ecg1(time,temp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
