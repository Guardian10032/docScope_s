
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class config implements ServletContextListener{

    private ExecutorService executorFast;
    private ExecutorService executorSlow;

    public void contextInitialized(ServletContextEvent event) {
        executorFast = Executors.newSingleThreadExecutor();
        executorFast.submit(new TaskFast()); // Task should implement Runnable.
        System.out.println("S");
        executorSlow = Executors.newSingleThreadExecutor();
        executorSlow.submit(new TaskSlow()); // Task should implement Runnable.
    }

    public void contextDestroyed(ServletContextEvent event) {
        executorFast.shutdown();
        executorSlow.shutdown();
    }
}