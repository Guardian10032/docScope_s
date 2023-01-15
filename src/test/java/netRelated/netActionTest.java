package netRelated;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Servlet.servletData.dbUrl;
import static Servlet.servletData.emailAddress;
import static org.junit.jupiter.api.Assertions.*;

class netActionTest {

    @Test
    void testGetName() {
        String Order="insert into patientlist (reference,firstname,lastname) values(?,?,?)";
        Connection conn;
        PreparedStatement s;
        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            s = conn.prepareStatement(Order);
            s.setString(1,"gama");
            s.setString(2,"first");
            s.setString(3,"last");
            s.executeUpdate();
            s.close();
            conn.close();
        } catch (SQLException ignored) {
        }
        assertEquals("first last",netAction.getName("gama"));
    }

    @Test
    void sendEmail() {
        //will receive an email
        emailAddress="liu.10032@gmail.com";
        netAction.SendEmail("gama",0);
    }

}