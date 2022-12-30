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

@WebServlet(urlPatterns = {"/recorder"},loadOnStartup = 1)
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
        List<Long> timeList=new ArrayList<>();
        responsePack respPack=new responsePack();
        try {
            boolean findIndex=true;
            long time=(long) new dataBase().getTimeSet().lower(reqPack1.startTime);
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
//            if (timeList.size()==0){
//                respPack.setLastTime(timeList.get(timeList.size()-1));
//                System.out.println(respPack.lastTime);
//            }else {
//                respPack.setLastTime((long) new dataBase().getTimeSet().higher(timeList.get(timeList.size()-1)));
//            }
        } finally {
            List<Double> values=new ArrayList<>();
            if(timeList.size()!=0) {
                for (Long i : timeList) {
                    values.addAll(new dataBase().getData_ecg1(i));
                }
            }
            respPack.setValueList(values);
            String jsonString = gson.toJson(respPack);
            resp.getWriter().write(jsonString);
        }
    }
}
