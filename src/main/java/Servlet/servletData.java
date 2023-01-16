package Servlet;

import dataGenerator.generator_patient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * class with static fields and static method
 */
public class servletData {
    /**
     * address of database
     */
    public static String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    /**
     * e-mail address of the doctor
     */
    public static String emailAddress=null;
    /**
     * list conning all patient simulators
     */
    public static List<generator_patient> patients;
    /**
     * send e-mail to the doctor for abnormal situation
     * @param ref reference of the patient who have abnormal situation
     * @param typeNumber signal that is abnormal,
     *                   0 means Body temperature, 1 means Heart rate, 2 means Systolic pressure,
     *                   2 means Diastolic pressure, 4 means Respiratory rate
     */
    public static void SendEmail(String ref,int typeNumber) {
        String name = null;
        String orderTime = "select firstname,lastname from patientlist where reference=?";
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

        String type;
        //change the number to string
        if (typeNumber==0){type="Body temperature";}
        else if (typeNumber==1) {type="Heart rate";}
        else if (typeNumber==2) {type="Systolic pressure";}
        else if (typeNumber==3) {type="Diastolic pressure";}
        else type="Respiratory rate";

        //send e-mail
        String from = "docscopeimperial0@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        //connect to gmail servlet
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
            //edit e-mail title and content
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject(name+" ("+ref+") "+"is in urgent condition");
            message.setText(name+" is in urgent condition in "+
                    new Timestamp(System.currentTimeMillis())+".\n"+
                    type+" is out of the defined thresholds.\n"+
                    "Please check the application for detail.\n\n"+
                    "docScope");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
