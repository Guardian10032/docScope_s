package Servlet;

import com.google.gson.Gson;
import netRelated.netAction;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.*;

import static Servlet.servletData.*;

@WebServlet(urlPatterns = {"/data"},loadOnStartup = 1)
public class data extends HttpServlet{

    Gson gson = new Gson();
    ServletConfig sc;
//    public void init(ServletConfig sc) {
//        this.sc = sc;
//        System.out.println("init done");
//    }

//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        requestPack reqPack =gson.fromJson(reqBody,requestPack.class);
//
//        resp.setContentType("application/json");
//        List<Double> values=new ArrayList<>();
//        responsePack respPack=new responsePack();
//        long initialTime=0;
//
//        Connection conn=null;
//        PreparedStatement s=null;
//
//        String orderTime="select initialtime from patientlist where reference='chuqiaoShen_30'";
//        try {
//            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
//            s = conn.prepareStatement(orderTime);
//            ResultSet resultSet = s.executeQuery();
//            while(resultSet.next()){
//                initialTime=resultSet.getLong(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("execute fail in time");
//        }
//        try {
//            s.close();
//        } catch (SQLException e) {
//            System.out.println("end statement fail in time");
//        }
////        finally {
////            try {
////                s.close();
////                conn.close();
////            } catch (SQLException e) {
////                System.out.println("end connection fail");
////            }
////        }
//        String orderEcg1="select ecg1 from ecgresp where id>=? and id<?";
////        try {
////            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
////        }catch (SQLException e) {
////            System.out.println("connection fail in value");
////        }
//        int index1=(int) ceil((reqPack.startTime-initialTime)/2);
//        int index2=(int) ceil((reqPack.endTime-initialTime)/2);
//        try {
//            s = conn.prepareStatement(orderEcg1);
//            s.setInt(1, index1);
//            s.setInt(2, index2);
//        } catch (SQLException e) {
//            System.out.println("statement fail in value");
//        }
//        try{
//            ResultSet resultSet = s.executeQuery();
//            while(resultSet.next()){
//                values.add(resultSet.getDouble("ecg1"));
//            }
//            if(index2-index1>values.size()){
//                respPack.setLastTime(values.size()*2+initialTime);
//            }
//        } catch (SQLException e) {
//            System.out.println("resultSet fail in value");
//        }
//        try {
//            s.close();
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("end connection fail");
//        }
//        respPack.setValueList(values);
//        String jsonString = gson.toJson(respPack);
//        resp.getWriter().write(jsonString);
//    }

//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        requestPack reqPack1 =gson.fromJson(reqBody,requestPack.class);
//
//        resp.setContentType("application/json");
//        List<Long> timeList=new ArrayList<>();
//        responsePack respPack=new responsePack();
//        try {
//            boolean findIndex=true;
//            long time=(long) new dataBase().getEcg1TimeSet().lower(reqPack1.startTime);
//            if (time< reqPack1.endTime){
//                timeList.add(time);
//            }
//            else findIndex=false;
//            while(findIndex){
//                time=(long) new dataBase().getEcg1TimeSet().lower(timeList.get(timeList.size()-1));
//                if (time< reqPack1.endTime){
//                    timeList.add(time);
//                }else findIndex=false;
//            }
//        }catch (NullPointerException e){
//            System.out.println("exist");
////            if (timeList.size()==0){
////                respPack.setLastTime(timeList.get(timeList.size()-1));
////                System.out.println(respPack.lastTime);
////            }else {
////                respPack.setLastTime((long) new Servlet.dataBase().getTimeSet().higher(timeList.get(timeList.size()-1)));
////            }
//        } finally {
//            List<Double> values=new ArrayList<>();
//            if(timeList.size()!=0) {
//                for (Long i : timeList) {
//                    values.addAll(new dataBase().getData_ecg1(i));
//                }
//            }
//            respPack.setValueList(values);
//            String jsonString = gson.toJson(respPack);
//            resp.getWriter().write(jsonString);
//        }
//    }
}
