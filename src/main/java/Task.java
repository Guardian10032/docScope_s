import dataGenerator.*;
import netRelated.netAction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Task implements Runnable{
    public static String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    @Override
    public void run() {
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        String table="drop table ecgRESP;\n" +
                "drop table patientlist;\n" +
                "drop table other;\n" +
                "create table ecgRESP (\n" +
                "                         id serial primary key,\n" +
                "                         ecg1 double precision,\n" +
                "                         ecg2 double precision,\n" +
                "                         RESP double precision\n" +
                ");\n" +
                "create table patientList(\n" +
                "                            reference varchar(128) PRIMARY KEY,\n" +
                "                            initialTime bigint,\n" +
                "                            firstName varchar(128),\n" +
                "                            LastName varchar(128),\n" +
                "                            gender boolean,\n" +
                "                            yearBirth smallint,\n" +
                "                            temperatureHigh smallint,\n" +
                "                            temperatureLow smallint,\n" +
                "                            heartHigh smallint,\n" +
                "                            heartLow smallint,\n" +
                "                            systolicHigh smallint,\n" +
                "                            systolicLow smallint,\n" +
                "                            diastolicHigh smallint,\n" +
                "                            diastolicLow smallint,\n" +
                "                            respiratoryHigh smallint,\n" +
                "                            respiratoryLow smallint\n" +
                ");\n" +
                "create table other(\n" +
                "                      id serial primary key,\n" +
                "                      temperature double precision,\n" +
                "                      heart smallint,\n" +
                "                      systolic smallint,\n" +
                "                      diastolic smallint,\n" +
                "                      respiratory smallint\n" +
                ");";
        netAction.databaseUpdate(table);


        String ref = "'chuqiaoShen_30'";
        long initialTime=new Timestamp(System.currentTimeMillis()).getTime();
        String patientOrder="INSERT INTO patientlist (reference,initialTime) values ("
                +ref
                + ","
                +initialTime
                +");";
        netAction.databaseUpdate(patientOrder);
        String fastOrder = "INSERT INTO ecgresp (ecg1) values (?);";
        String slowOrder = "INSERT INTO other (temperature) values (?);";
        generator_ecg1 ecg1Generator =new generator_ecg1(netAction.getInitialTime());
        generator_temperature temperatureGenerator =new generator_temperature(netAction.getInitialTime());
        Connection conn=null;
        PreparedStatement s=null;
        List<Double> temp;


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("sleep fail");
        }
        while (true){
            //Slow task here: sampling frequency is 1 Hz refresh about 10 times of slow task
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


            for (int i=0;i<20;i++){
                //Fast task here: sampling frequency is 500Hz refresh about every 100ms
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("sleep fail");
                }
                temp = ecg1Generator.outputValues(500);
                System.out.println(temp.size());
                try {
                    conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
                } catch (SQLException e) {
                    System.out.println("connection fail in loop");
                }
                for(double data:temp) {
                    try {
                        s = conn.prepareStatement(fastOrder);
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
}
