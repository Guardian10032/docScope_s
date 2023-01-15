package multiThread;

import dataGenerator.*;

import java.sql.*;
import java.util.List;

import static Servlet.servletData.dbUrl;

/**
 * non-stop loop that run every 50ms ideally.
 * used to record ecg and resp signals
 */
public class TaskFast implements Runnable{
    List<generator_patient> patients;
    public Connection conn=null;

    /**
     * create the task
     * @param patients list containing patient simulators
     */
    public TaskFast(List<generator_patient> patients){
        this.patients=patients;
    }
    @Override
    public void run() {
        String fastOrder;
        List<List<Double>> temp;

        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
        } catch (SQLException ignored) {}

        while (true){
            //Fast task here: sampling frequency is 500Hz refresh about every 100ms due to delay
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
            for (generator_patient patient:patients) {
                fastOrder = "INSERT INTO "+patient.ref+"fast (ecg1,ecg2,resp) values (?,?,?);";
                temp = patient.outputValuesFast();// get the signals
                //insert values to the corresponding table
                for(int i=0;i<temp.get(0).size();i++) {
                    try {
                        PreparedStatement s = conn.prepareStatement(fastOrder);
                        s.setDouble(1,temp.get(0).get(i));
                        s.setDouble(2,temp.get(1).get(i));
                        s.setDouble(3,temp.get(2).get(i));
                        s.executeUpdate();
                        s.close();
                    } catch (SQLException ignored) {}
                }
            }
        }
    }
}
