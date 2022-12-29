//import com.google.gson.Gson;
//import netRelated.requestPack;
//import netRelated.responsePack;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static java.lang.Math.ceil;
//
//@WebServlet(urlPatterns = {"/realTime"},loadOnStartup = 1)
//public class realTime extends HttpServlet {
//    ServletConfig config = null;
//    Gson gson = new Gson();
//
//    // init method
////    public void init(ServletConfig sc) {
////        config = sc;
////        System.out.println("in init");
////    }
//
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);
//        resp.setContentType("application/json");
//        boolean findIndex=true;
//        List<Long> timeList=new ArrayList<>();
//        try{
//            long time=(long) new dataBase().getTimeSet().ceiling(reqPack1.startTime);
//            if (time< reqPack1.endTime){
//                timeList.add(time);
//            }
//            else {
//                findIndex=false;
//            }
//        } catch (NullPointerException e) {
//            findIndex=false;
////            throw new RuntimeException(e);
//        }
//        long time;
//        while(findIndex) {
//            try{
//                time=(long) new dataBase().getTimeSet().ceiling(timeList.get(timeList.size()-1));
//            } catch (NullPointerException e) {
//                findIndex=false;
////                throw new RuntimeException(e);
//            }
//            if (time< reqPack1.endTime){
//                timeList.add(time);
//            }
//            else findIndex=false;
//        }
//
//        List<Double> values=new ArrayList<>();
//        if(timeList.size()!=0) {
//            for (Long i : timeList) {
//                values.addAll(new dataBase().getData_ecg1(i));
//            }
//        }
//        String jsonString = gson.toJson(new responsePack(values));
//        resp.getWriter().write(jsonString);
//        System.out.println("in post");
//    }
////    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
////        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);
////
////        resp.setContentType("application/json");
//////        long timestamp=new dataBase().getTimestamp();
//////        int index1= (int) ceil((((Long)reqPack1.startTime)-((Long)timestamp))/2);
//////        int index2= (int) ceil((((Long)reqPack1.endTime)-((Long)timestamp))/2);
////
//////        List<List<Double>> values=new ArrayList<>();
//////        values.add(new dataBase().getData_ecg1().subList(index1,index2));
//////        responsePack respPack1 =new responsePack(reqPack1.type,values);
//////        responsePack respPack1 =new responsePack(new dataBase().getData_ecg1().subList(index1,index2));
////        responsePack respPack1 =new responsePack(new dataBase().getData_ecg1().get(new dataBase().getSize()-1));
////        String jsonString = gson.toJson(respPack1);
////        resp.getWriter().write(jsonString);
////        System.out.println("in post");
////    }
//}
