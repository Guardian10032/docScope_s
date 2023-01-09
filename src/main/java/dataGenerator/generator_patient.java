package dataGenerator;

import Servlet.servletData;
import netRelated.netAction;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Servlet.servletData.dbUrl;

public class generator_patient {
    private generator_ecg1 ecg1Generator;
    private generator_ecg2 ecg2Generator;
    private generator_resp respGenerator;
    private generator_temperature temperatureGenerator;
    private generator_heartRate heartGenerator;
    private generator_systolic systolicGenerator;
    private generator_diastolic diastolicGenerator;
    private generator_respiratoryRate respiratoryGenerator;
    public generator_patient(String ref){
        long initialTime=new Timestamp(System.currentTimeMillis()).getTime();
        String patientOrder="INSERT INTO patientList (reference,initialTime) values (?,?);";
        Connection conn=null;
        PreparedStatement s=null;
        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
        } catch (SQLException e) {
            System.out.println("connection fail");
        }
        try {
            s = conn.prepareStatement(patientOrder);
            s.setString(1,ref);
            s.setLong(2,initialTime);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("execute fail in adding ref");
        }
        try {
            s.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("end connection fail");
        }
        servletData.initialTime.add(initialTime);

        ecg1Generator =new generator_ecg1(initialTime);
        ecg2Generator =new generator_ecg2(initialTime);
        respGenerator=new generator_resp(initialTime);

        temperatureGenerator =new generator_temperature(initialTime);
        heartGenerator=new generator_heartRate(initialTime);
        systolicGenerator =new generator_systolic(initialTime);
        diastolicGenerator =new generator_diastolic(initialTime);
        respiratoryGenerator=new generator_respiratoryRate(initialTime);
    }

    public List<List<Double>> outputValuesSlow(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();
        output.add(temperatureGenerator.outputValues(currentTime));
        output.add(heartGenerator.outputValues(currentTime));
        output.add(systolicGenerator.outputValues(currentTime));
        output.add(diastolicGenerator.outputValues(currentTime));
        output.add(respiratoryGenerator.outputValues(currentTime));
        return output;
    }
    public List<List<Double>> outputValuesFast(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();
        output.add(ecg1Generator.outputValues(currentTime));
        output.add(ecg2Generator.outputValues(currentTime));
        output.add(respGenerator.outputValues(currentTime));
        return output;
    }
}
