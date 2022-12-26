import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import netRelated.*;

import static java.lang.Math.floor;

@WebServlet(urlPatterns = {"/data"},loadOnStartup = 1)
public class MyServlet extends HttpServlet {
    ServletConfig config = null;
    Data data_ecg1;
    Timestamp timestamp;

    // init method
    public void init(ServletConfig sc)
    {
        config = sc;
        System.out.println("in init");
        data_ecg1 = new Data("ecg1_noraml1",1);
        timestamp=new Timestamp(System.currentTimeMillis());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);

        resp.setContentType("application/json");
        int index1= (int) ((((Long)reqPack1.startTime)-((Long)timestamp.getTime()))/2+1);
        int index2= (int) ((((Long)reqPack1.endTime)-((Long)timestamp.getTime()))/2);

        List<List<Double>> values=new ArrayList<>();
        values.add(data_ecg1.values.subList(index1,index2));
        responsePack respPack1 =new responsePack(reqPack1.type,values);
        String jsonString = gson.toJson(respPack1);
        resp.getWriter().write(jsonString);
    }
}