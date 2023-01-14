package netRelated;

import com.google.gson.Gson;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

import static Servlet.servletData.dbUrl;
import static Servlet.servletData.emailAddress;

public class netAction {
    public static void SendEmail(String ref,int typeNumber) {
        if (emailAddress == null) {
        } else {
            String name=getName(ref);
            String type;
            if (typeNumber==0){type="Body temperature";}
            else if (typeNumber==1) {type="Heart rate";}
            else if (typeNumber==2) {type="Systolic pressure";}
            else if (typeNumber==3) {type="Diastolic pressure";}
            else type="Respiratory rate";

            String from = "docscopeimperial0@gmail.com";
            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("docscopeimperial@gmail.com", "fwkfppkrzarynbji");
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
                message.setSubject(name+"is in urgent condition");
                message.setText(name+" is in urgent condition in "+
                        new Timestamp(System.currentTimeMillis())+".\n"+
                        type+" is out of the defined thresholds.\n"+
                        "Please check the application for detail.\n\n"+
                        "docScope");
                Transport.send(message);
            } catch (
                    MessagingException mex) {
                mex.printStackTrace();
            }
        }
    }
    public static responsePack postRequestReal(requestPack reqPack) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(reqPack);
        // Set up the body data
        String message = jsonString;
        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        URL myURL = new URL("http://localhost:8080/docScope_s/data");
        HttpURLConnection conn = null;

        conn = (HttpURLConnection) myURL.openConnection();
// Set up the header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(body.length));
        conn.setDoOutput(true);

// Write the body of the request
        try (OutputStream outputStream = conn.getOutputStream()) {
            outputStream.write(body, 0, body.length);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        String inputLine;
// Read the body of the response
        responsePack respPack = null;
        while ((inputLine = bufferedReader.readLine()) != null) {
//            System.out.println(inputLine);
            respPack=gson.fromJson(inputLine,responsePack.class);
            return respPack;
        }
        bufferedReader.close();

        return respPack;
    }
    public static void makeGetRequest() throws IOException {
        URL myURL = new URL("https://servlet10032.herokuapp.com/patients");
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(myURL.openStream()));

        String inputLine;
// Read the body of the response
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();

    }
    public static void databaseUpdate(String order){
        Connection conn=null;
        PreparedStatement s=null;
        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            s = conn.prepareStatement(order);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("execute fail in databaseUpdate");
        }
        try {
            s.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("end connection fail");
        }
    }
    public static String getName(String ref){
        String name=null;
        String orderTime = "select lastname,firstname from patientlist where reference=?";
        try {
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            PreparedStatement s = conn.prepareStatement(orderTime);
            s.setString(1,ref);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString(1)+" "+resultSet.getString(2);
            }
            s.close();

        } catch (SQLException e) {
            System.out.println("end statement fail in time");
        }
        return name;
    }
}
