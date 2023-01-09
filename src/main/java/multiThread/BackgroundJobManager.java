package multiThread;

import dataGenerator.generator_patient;
import netRelated.netAction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class BackgroundJobManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    Thread task1;
    Thread task2;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        String table=
                "drop table if exists patientList;\n" +
                        "drop table if exists other;\n" +
                        "drop table if exists ecgRESP;\n" +
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("sleep fail");
        }
        generator_patient patient1=new generator_patient("patient1");
        List<generator_patient> patients=new ArrayList<>();
        patients.add(patient1);
        task1=new Thread(new TaskFast(patients));
        task1.start();
        task2=new Thread(new TaskSlow(patients));
        task2.start();
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
        task1.stop();
        task2.stop();
    }
}
