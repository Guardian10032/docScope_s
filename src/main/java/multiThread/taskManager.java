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

import static Servlet.servletData.dbUrl;

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
        List<generator_patient> patients=new ArrayList<>();

        patients.add(new generator_patient("alpha","abnormal"));
        patients.add(new generator_patient("beta","normal"));
        taskFast=new TaskFast(patients);
        taskSlow=new TaskSlow(patients);
        threadFast =new Thread(taskFast);
        threadSlow =new Thread(taskSlow);
        threadFast.start();
        threadSlow.start();
//        long initialTime= netAction.getInitialTime();
//        Timestamp clock=new Timestamp(initialTime);
//        Timestamp initialDelay=new Timestamp(clock.getYear()+1900, clock.getMonth(),clock.getDate(),23,59,59,999999999);
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new scheduleTask(), initialDelay.getTime()-initialTime, 86400000, TimeUnit.MILLISECONDS);
//
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
