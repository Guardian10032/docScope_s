//import com.google.gson.Gson;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import dataGenerator.*;
//import netRelated.*;
//
//@WebServlet(urlPatterns = {"/reader"},loadOnStartup = 1)
//public class reader extends HttpServlet {
//    ServletConfig config = null;
//    String reference;
//    Gson gson = new Gson();
//    public void init(ServletConfig sc) {
//        config = sc;
//        System.out.println("in init");
//    }
//
//    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
//        System.out.println("in put");
//        String ref=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        this.reference=gson.fromJson(ref,String.class);
//        if(reference.equals("1234")){
//
//        }
//    }
//}
