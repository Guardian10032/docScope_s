package multiThread;

import java.sql.*;

import static Servlet.servletData.*;

public class scheduleTask implements Runnable{
    @Override
    public void run() {
//        for
//        String table="create table ?( \n" +
//                "                                      id serial primary key, \n" +
//                "                                      temperature double precision, \n" +
//                "                                      heart smallint, \n" +
//                "                                      systolic smallint, \n" +
//                "                                      diastolic smallint, \n" +
//                "                                      respiratory smallint \n" +
//                "                );";
//        try {
//            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
//            Statement s = conn.prepareStatement(getReference);
//            ResultSet resultSet = s.executeQuery();
//            while (resultSet.next()) {
//                values.add(resultSet.getDouble("temperature"));
//            }
//            s.close();
//            conn.close();
//        } catch (SQLException e) {
//        }
    }
}
