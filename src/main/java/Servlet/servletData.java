package Servlet;

import dataGenerator.generator_patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class servletData {
    public static String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    public static String emailAddress=null;
    public static List<generator_patient> patients;

}
