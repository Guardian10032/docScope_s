package multiThread;

import dataGenerator.generator_slow;
import dataGenerator.generator_systolic;
import dataGenerator.generator_temperature;
import netRelated.netAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TaskSlow implements Runnable{
    public static String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    @Override
    public void run() {

        String slowOrder="INSERT INTO other (temperature,systolic,diastolic) values (?,?,?);";
        generator_slow slowGenerator=new generator_slow();

        Connection conn=null;
        PreparedStatement s=null;
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep fail");
            }
            List<List<Double>> temp=slowGenerator.outputValues();

            try {
                conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            } catch (SQLException e) {
                System.out.println("connection fail in loop");
            }
            for(int i=0;i<temp.get(0).size();i++) {
                try {
                    s = conn.prepareStatement(slowOrder);
                    s.setDouble(1,temp.get(0).get(i));
                    s.setDouble(2,temp.get(1).get(i));
                    s.setDouble(3,temp.get(2).get(i));
                    s.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("execute fail in loop");
                }
            }
//            for(double data:temprature) {
//                try {
//                    s = conn.prepareStatement(temperatureOrder);
//                    s.setDouble(1,data);
//                    s.executeUpdate();
//                } catch (SQLException e) {
//                    System.out.println("execute fail in loop");
//                }
//            }
//            for(double data:systolic) {
//                try {
//                    s = conn.prepareStatement(systolicOrder);
//                    s.setDouble(1,data);
//                    s.executeUpdate();
//                } catch (SQLException e) {
//                    System.out.println("execute fail in loop");
//                }
//            }
            try {
                s.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("end connection fail in loop");
            }
        }
    }
}
