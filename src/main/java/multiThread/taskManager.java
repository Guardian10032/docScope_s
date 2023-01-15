package multiThread;

import dataGenerator.generator_patient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static Servlet.servletData.*;

@WebListener
public class taskManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    TaskFast taskFast;
    TaskSlow taskSlow;
    Thread threadFast;
    Thread threadSlow;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        String patientListTable=
                "drop table if exists patientList;\n"+
                "create table patientList(\n" +
                "                            reference varchar(128) PRIMARY KEY,\n" +
                "                            initialTime bigint,\n" +
                "                            firstName varchar(128),\n" +
                "                            LastName varchar(128),\n" +
                "                            gender varchar(128),\n" +
                "                            yearBirth smallint,\n" +
                "                            temperatureHigh double precision,\n" +
                "                            temperatureLow double precision,\n" +
                "                            heartHigh double precision,\n" +
                "                            heartLow double precision,\n" +
                "                            systolicHigh double precision,\n" +
                "                            systolicLow double precision,\n" +
                "                            diastolicHigh double precision,\n" +
                "                            diastolicLow double precision,\n" +
                "                            respiratoryHigh double precision,\n" +
                "                            respiratoryLow double precision\n" +
                ");";
        try {
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            PreparedStatement s = conn.prepareStatement(patientListTable);
            s.executeUpdate();
            s.close();
            conn.close();
        } catch (SQLException ignored) {
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("sleep fail");
        }
        patients=new ArrayList<>();

        patients.add(new generator_patient("abnormal_patient","abnormal"));
        patients.add(new generator_patient("normal_patient","normal"));
        taskFast=new TaskFast(patients);
        taskSlow=new TaskSlow(patients);
        threadFast =new Thread(taskFast);
        threadSlow =new Thread(taskSlow);
        threadFast.start();
        threadSlow.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
//        scheduler.shutdownNow();
        try {
            taskFast.conn.close();
            taskSlow.conn.close();
        } catch (SQLException e) {
            System.out.println("end connection failed");
        }
        threadFast.stop();
        threadSlow.stop();
    }
}
