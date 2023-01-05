package multiThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class config implements ServletContextListener{

    private ExecutorService executor;
    private ExecutorService executor2;


    public void contextInitialized(ServletContextEvent event) {
//        Thread task1=new Thread(new multiThread.Task());
//        task1.start();
//        Thread task2=new Thread(new multiThread.TaskSlow());
//        task2.start();
//        executor = Executors.newSingleThreadExecutor();
//        executor.submit(new multiThread.Task()); // multiThread.Task should implement Runnable.
//        executor2.submit(new multiThread.TaskSlow());
    }

    public void contextDestroyed(ServletContextEvent event) {
        executor.shutdown();
    }
}