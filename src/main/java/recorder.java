import com.google.gson.Gson;
import dataGenerator.Data;
import dataGenerator.generator_ecg1;
import netRelated.requestPack;
import netRelated.responsePack;

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

import static java.lang.Math.ceil;

@WebServlet(urlPatterns = {"/recorder"},loadOnStartup = 2)
public class recorder extends HttpServlet{
    ServletConfig config = null;
    Gson gson = new Gson();

    // init method
//    public void init(ServletConfig sc) {
//        config = sc;
//        System.out.println("in init");
//    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);

        resp.setContentType("application/json");
        boolean findIndex=true;
        List<Long> timeList=new ArrayList<>();
        long time;
        try {
            time=(long) new dataBase().getTimeSet().lower(reqPack1.startTime);
            if (time< reqPack1.endTime){
                timeList.add(time);
            }
            else findIndex=false;
            while(findIndex){
                time=(long) new dataBase().getTimeSet().lower(timeList.get(timeList.size()-1));
                if (time< reqPack1.endTime){
                    timeList.add(time);
                }else findIndex=false;
            }
        }catch (NullPointerException e){
            System.out.println("exist");
        } finally {
            System.out.println("finally");
            List<Double> values=new ArrayList<>();
            if(timeList.size()!=0) {
                for (Long i : timeList) {
                    values.addAll(new dataBase().getData_ecg1(i));
                }
            }
            System.out.println(timeList);
            String jsonString = gson.toJson(new responsePack(values));
            resp.getWriter().write(jsonString);
            System.out.println("in post");
        }
    }

//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);
//
//        resp.setContentType("application/json");
//        long timestamp=new dataBase().getTimestamp();
//        int index1= (int) ceil((((Long)reqPack1.startTime)-((Long)timestamp))/2);
//        int index2= (int) ceil((((Long)reqPack1.endTime)-((Long)timestamp))/2);
//        List<Double> values=new ArrayList<>();
//        int temp=-1;
//        for(List<Double> i :new dataBase().getData_ecg1()){
//            temp+=i.size();
//            if
//            values.addAll(i);
//        }
//        responsePack respPack1 =new responsePack(values.subList(index1,index2),reqPack1.endTime);
//
//        String jsonString = gson.toJson(respPack1);
//        resp.getWriter().write(jsonString);
//        System.out.println("in post");
//    }
}
