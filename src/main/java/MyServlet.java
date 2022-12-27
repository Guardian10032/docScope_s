import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dataGenerator.*;
import netRelated.*;

@WebServlet(urlPatterns = {"/data"},loadOnStartup = 1)
public class MyServlet extends HttpServlet {
    ServletConfig config = null;
    Data data=new Data();
    List<Double> data_ecg1;
    long timestamp;
    String reference;
    Gson gson = new Gson();

    // init method
    public void init(ServletConfig sc) {
        config = sc;
        System.out.println("in init");
        timestamp=new Timestamp(System.currentTimeMillis()).getTime();
        generator ecg1Generator=new generator("12344",timestamp);
//        try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        while (true){
            try {
                Thread.sleep(10);
                List<Double> temp =ecg1Generator.outputValues();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        data_ecg1=ecg1Generator.ecg1Former;
//        data_ecg1 = data.loadFile("ecg1_normal1");
    }
@Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String ref=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        this.reference=gson.fromJson(ref,String.class);
        if(reference=="1234"){
            //data_ecg1 = data.loadFile("ecg1_noraml1");
            timestamp=new Timestamp(System.currentTimeMillis()).getTime();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);

        resp.setContentType("application/json");
        int index1= (int) ((((Long)reqPack1.startTime)-((Long)timestamp))/2);
        int index2= (int) ((((Long)reqPack1.endTime)-((Long)timestamp))/2);

        List<List<Double>> values=new ArrayList<>();
        values.add(data_ecg1.subList(index1,index2));
        responsePack respPack1 =new responsePack(reqPack1.type,values);
        String jsonString = gson.toJson(respPack1);
        resp.getWriter().write(jsonString);
    }
}