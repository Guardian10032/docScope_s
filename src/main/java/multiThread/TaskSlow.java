package multiThread;

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
        String slowOrder = "INSERT INTO other (temperature) values (?);";
        generator_temperature temperatureGenerator =new generator_temperature(netAction.getInitialTime());
        Connection conn=null;
        PreparedStatement s=null;
        List<Double> temp;
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep fail");
            }
            temp = temperatureGenerator.outputValues(1);
            try {
                conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            } catch (SQLException e) {
                System.out.println("connection fail in loop");
            }
            for(double data:temp) {
                try {
                    s = conn.prepareStatement(slowOrder);
//                    s.setString(1,"'temperature'");
//                    s.setDouble(2, data);
                    s.setDouble(1,data);
                    s.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("execute fail in loop");
                }
            }
            try {
                s.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("end connection fail in loop");
            }
        }
    }
}
