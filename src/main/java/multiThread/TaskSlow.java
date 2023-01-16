package multiThread;

import dataGenerator.generator_patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Servlet.servletData.*;

/**
 * non-stop loop that run every second ideally.
 * used to record body temperature, heart rate, systolic pressure, diastolic pressure and respiratory rate
 */
public class TaskSlow implements Runnable{
    List<generator_patient> patients;
    public Connection conn=null;
    /**
     * create the task
     * @param patients list containing patient simulators
     */
    public TaskSlow(List<generator_patient> patients){
        this.patients=patients;
    }
    @Override
    public void run() {

        String slowOrder;
        List<List<Double>> temp;
        List<averageCalculator> averages=new ArrayList<>(patients.size());
        for (int i=0;i<patients.size();i++){
            averages.add(new averageCalculator());
        }

        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
        } catch (SQLException ignored) {}

        while (true){
            //Fast task here: sampling frequency is 1Hz refresh about every second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            for (int t=0;t<patients.size();t++) {
                generator_patient patient=patients.get(t);
                slowOrder="INSERT INTO "+patient.ref+"slow (temperature,heart,systolic,diastolic,respiratory) values (?,?,?,?,?);";
                temp = patient.outputValuesSlow();//get the signals
                //insert values to the corresponding table
                for (int i = 0; i < temp.get(0).size(); i++) {
                    try {
                        PreparedStatement s = conn.prepareStatement(slowOrder);
                        s.setDouble(1, temp.get(0).get(i));
                        s.setDouble(2, temp.get(1).get(i));
                        s.setDouble(3, temp.get(2).get(i));
                        s.setDouble(4, temp.get(3).get(i));
                        s.setDouble(5, temp.get(4).get(i));
                        s.executeUpdate();
                        s.close();
                    } catch (SQLException ignored) {}
                }

                averages.get(t).update(temp);
                List<Double> average=averages.get(t).output();
                if (average!=null) {
                    String averageOrder = "INSERT INTO " + patient.ref + "slowAverage (temperature,heart,systolic,diastolic,respiratory) values (?,?,?,?,?);";
                    try {
                        PreparedStatement sAverage = conn.prepareStatement(averageOrder);
                        sAverage.setDouble(1, average.get(0));
                        sAverage.setDouble(2, average.get(1));
                        sAverage.setDouble(3, average.get(2));
                        sAverage.setDouble(4, average.get(3));
                        sAverage.setDouble(5, average.get(4));
                        sAverage.executeUpdate();
                        sAverage.close();
                    }catch (SQLException ignored) {}
                }
            }
        }
    }
}
