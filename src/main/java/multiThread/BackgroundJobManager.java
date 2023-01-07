//package multiThread;
//
//import multiThread.scheduleTask;
//import netRelated.netAction;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.Timestamp;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@WebListener
//public class BackgroundJobManager implements ServletContextListener {
//    private ScheduledExecutorService scheduler;
//    Thread task1;
//    Thread task2;
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            System.out.println("sleep fail");
//        }
//
//        task1=new Thread(new Task());
//        task1.start();
//        task2=new Thread(new TaskSlow());
//        task2.start();
//        long initialTime= netAction.getInitialTime();
//        Timestamp clock=new Timestamp(initialTime);
//        Timestamp initialDelay=new Timestamp(clock.getYear()+1900, clock.getMonth(),clock.getDate(),23,59,59,999999999);
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new scheduleTask(), initialDelay.getTime()-initialTime, 86400000, TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent event) {
//        scheduler.shutdownNow();
//        task1.stop();
//        task2.stop();
//    }
//}
