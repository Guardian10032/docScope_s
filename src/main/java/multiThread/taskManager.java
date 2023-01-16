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

import static Servlet.servletData.*;

/**
 * start when the servlet is created,
 * create patientList table in database,
 * manage the fast task and slow task
 */
@WebListener
public class taskManager implements ServletContextListener {
    TaskFast taskFast;
    TaskSlow taskSlow;
    Thread threadFast;
    Thread threadSlow;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception ignored) {}
        //create patientList table to store information of patients
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
        } catch (InterruptedException ignored) {}
        //create objects as the simulators of patients
        patients=new ArrayList<>();
        //add patient simulators to the list
        patients.add(new generator_patient("abnormal_1","abnormal"));
        patients.add(new generator_patient("abnormal_2","abnormal"));
        patients.add(new generator_patient("normal_1","normal"));
        patients.add(new generator_patient("normal_2","normal"));
        //put the patients to tasks that keep running
        taskFast=new TaskFast(patients);
        taskSlow=new TaskSlow(patients);
        threadFast =new Thread(taskFast);
        threadSlow =new Thread(taskSlow);
        threadFast.start();
        threadSlow.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //close the connections and stop the thread when servlet is destroyed
        try {
            taskFast.conn.close();
            taskSlow.conn.close();
        } catch (SQLException ignored) {}
        threadFast.stop();
        threadSlow.stop();
    }
}
