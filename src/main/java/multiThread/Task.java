package multiThread;

import dataGenerator.*;
import netRelated.netAction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Task implements Runnable{
    public static String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    @Override
    public void run() {
        String ecg1Order = "INSERT INTO ecgresp (ecg1) values (?);";

        generator_ecg1 ecg1Generator =new generator_ecg1(netAction.getInitialTime());

        Connection conn=null;
        PreparedStatement s=null;
        List<Double> temp;

        while (true){
            //Fast task here: sampling frequency is 500Hz refresh about every 100ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("sleep fail");
            }
            temp = ecg1Generator.outputValues();
//                System.out.println(temp.size());
            try {
                conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            } catch (SQLException e) {
                System.out.println("connection fail in loop");
            }
            for(double data:temp) {
                try {
                    s = conn.prepareStatement(ecg1Order);
//                        s.setString(1,"ecg1");
//                        s.setDouble(2, data);
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
