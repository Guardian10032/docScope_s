import Servlet.dataBase;
import dataGenerator.generator_temperature;

import java.sql.Timestamp;
import java.util.List;

public class TaskSlow implements Runnable{

    @Override
    public void run() {
        System.out.println("in taskSlow");
        generator_temperature temperature=new generator_temperature();
        while (true) {
            try {
                Thread.sleep(1000);
                List<Double> temp = temperature.outputValues(500);
                long time = new Timestamp(System.currentTimeMillis()).getTime();
                new dataBase().addData_temperature(time, temp);
                System.out.println("S");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
