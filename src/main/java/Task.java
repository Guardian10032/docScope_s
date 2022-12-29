import dataGenerator.generator_ecg1;

import java.sql.Timestamp;
import java.util.List;

public class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("in task");
        long timestamp=new Timestamp(System.currentTimeMillis()).getTime();
        new dataBase().setTimestamp(timestamp);
        generator_ecg1 ecg1Generator =new generator_ecg1(timestamp);
        while (true){
            try {
                Thread.sleep(10);
                List<Double> temp = ecg1Generator.outputValues(500);
                long time=new Timestamp(System.currentTimeMillis()).getTime();
                new dataBase().addData_ecg1(time,temp);
//                System.out.println(new dataBase().getData_ecg1(time));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
