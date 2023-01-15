package Servlet;

import com.google.gson.Gson;
import dataGenerator.generator_patient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Servlet.servletData.*;

/**
 * servlet with put and post method
 */
@WebServlet(urlPatterns = {"/data"},loadOnStartup = 1)
public class Servlet extends HttpServlet{

    Gson gson = new Gson();

    /**
     * given the reference number, get the corresponding updated thresholds
     * @param req reference whose thresholds were updated
     */
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        //get the reference
        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String ref =gson.fromJson(reqBody,String.class);
        //get the threshold
        String order="select temperaturehigh,temperaturelow,hearthigh,heartlow,systolichigh,systoliclow," +
                "diastolichigh,diastoliclow,respiratoryhigh,respiratorylow from patientlist where reference=?";
        Connection conn;
        PreparedStatement s;
        List<Double> thr=new ArrayList<>(10);
        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            s = conn.prepareStatement(order);
            s.setString(1,ref);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                for (int i=1;i<=10;i++) {
                    thr.add(resultSet.getDouble(i));
                }
            }
            s.close();
            conn.close();
        } catch (SQLException ignored) {}
        //update the thresholds of corresponding patient in the servlet
        for(generator_patient patient:patients){
            if (ref.equals(patient.ref)){
                patient.thr=thr;
                System.out.println(patient.thr);
            }
            break;
        }
    }

    /**
     * get the e-mail address from the client.
     * If it is null, then try to send the e-mail address from the servlet.
     * If not, then update stored address.
     * @param req e-mail address from the client
     * @param resp e-mail address from the servlet
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //get e-mail address
        String reqBody=req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String newEmailAddress =gson.fromJson(reqBody,String.class);
        if (newEmailAddress == null) {
            //give the stored e-mail address
            resp.setContentType("application/json");
            String jsonString = gson.toJson(emailAddress);
            resp.getWriter().write(jsonString);
        }else emailAddress=newEmailAddress;//update stored address
    }
}
