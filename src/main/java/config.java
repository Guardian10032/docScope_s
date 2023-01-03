
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class config implements ServletContextListener{

    private ExecutorService executor;


    public void contextInitialized(ServletContextEvent event) {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(new Task()); // Task should implement Runnable.
    }

    public void contextDestroyed(ServletContextEvent event) {
        executor.shutdown();
    }
}