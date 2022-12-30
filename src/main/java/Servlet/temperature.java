package Servlet;

import com.google.gson.Gson;
import netRelated.requestPack;
import netRelated.responsePack;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/temperature"},loadOnStartup = 2)
public class temperature extends HttpServlet {
    Gson gson = new Gson();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        requestPack reqPack1 = gson.fromJson(reqBody, requestPack.class);

        resp.setContentType("application/json");
        List<Long> timeList = new ArrayList<>();
        responsePack respPack = new responsePack();
        try {
            boolean findIndex = true;
            long time = (long) new dataBase().getEcg1TimeSet().lower(reqPack1.startTime);
            if (time < reqPack1.endTime) {
                timeList.add(time);
            } else findIndex = false;
            while (findIndex) {
                time = (long) new dataBase().getEcg1TimeSet().lower(timeList.get(timeList.size() - 1));
                if (time < reqPack1.endTime) {
                    timeList.add(time);
                } else findIndex = false;
            }
        } catch (NullPointerException e) {
            System.out.println("exist");

        } finally {
            List<Double> values = new ArrayList<>();
            if (timeList.size() != 0) {
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
