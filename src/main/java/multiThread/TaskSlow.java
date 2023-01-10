package multiThread;

import dataGenerator.generator_patient;
import dataGenerator.generator_systolic;
import dataGenerator.generator_temperature;
import netRelated.netAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Servlet.servletData.dbUrl;

public class TaskSlow implements Runnable{
    List<generator_patient> patients;
    public Connection conn=null;
    public TaskSlow(List<generator_patient> patients){
        this.patients=patients;
    }
    @Override
    public void run() {

        String slowOrder;
        List<List<Double>> temp;

        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
        } catch (SQLException e) {
            System.out.println("connection fail in slow loop");
        }

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep fail");
            }
            for (generator_patient patient:patients) {
                slowOrder="INSERT INTO "+patient.ref+"slow (temperature,heart,systolic,diastolic,respiratory) values (?,?,?,?,?);";
                temp = patient.outputValuesSlow();
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
                    } catch (SQLException e) {
                        System.out.println("execute fail in slow loop");
                    }
                }
            }
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                System.out.println("end connection fail in slow loop");
//            }
        }
    }
}
