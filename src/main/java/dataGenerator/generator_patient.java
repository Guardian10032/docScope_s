package dataGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static Servlet.servletData.dbUrl;
import static Servlet.servletData.emailAddress;
import static netRelated.netAction.SendEmail;

public class generator_patient {
    private generator_ecg1 ecg1Generator;
    private generator_ecg2 ecg2Generator;
    private generator_resp respGenerator;
    private generator_temperature temperatureGenerator;
    private generator_heartRate heartGenerator;
    private generator_systolic systolicGenerator;
    private generator_diastolic diastolicGenerator;
    private generator_respiratoryRate respiratoryGenerator;
    public String ref;
    public List<Double> thr;
    public List<Boolean> urgent;
    public generator_patient(String ref,String status){
        thr= Arrays.asList(38.0,35.0,110.0,50.0,145.0,85.0,90.0,55.0,20.0,12.0);
        urgent=Arrays.asList(false,false,false,false,false);
        this.ref=ref;
        long initialTime=new Timestamp(System.currentTimeMillis()).getTime();
        String patientOrder=
                "INSERT INTO patientList (reference,initialTime,temperaturehigh,temperaturelow,hearthigh,heartlow," +
                        "systolichigh,systoliclow,diastolichigh,diastoliclow,respiratoryhigh,respiratorylow) " +
                        "values (?,?,38,35,110,50,145,85,90,55,20,12);\n"+
                "drop table if exists "+ref+"Slow;\n"+
                "create table "+ref+"Slow(\n" +
                "                      id serial primary key,\n" +
                "                      temperature double precision,\n" +
                "                      heart double precision,\n" +
                "                      systolic double precision,\n" +
                "                      diastolic double precision,\n" +
                "                      respiratory double precision\n" +
                ");\n" +
                "drop table if exists "+ref+"SlowAverage;\n"+
                "create table "+ref+"SlowAverage(\n" +
                "                      id serial primary key,\n" +
                "                      temperature double precision,\n" +
                "                      heart double precision,\n" +
                "                      systolic double precision,\n" +
                "                      diastolic double precision,\n" +
                "                      respiratory double precision\n" +
                ");\n"+
                "drop table if exists "+ref+"Fast;\n"+
                "create table "+ref+"Fast(\n" +
                "                      id serial primary key,\n" +
                "                      ecg1 double precision,\n" +
                "                      ecg2 double precision,\n" +
                "                      RESP double precision\n" +
                ");";
        Connection conn;
        PreparedStatement s;
        try {
            conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
            s = conn.prepareStatement(patientOrder);
            s.setString(1,ref);
            s.setLong(2,initialTime);
            s.executeUpdate();
            s.close();
            conn.close();
        } catch (SQLException ignored) {
        }

        ecg1Generator =new generator_ecg1(initialTime,status);
        ecg2Generator =new generator_ecg2(initialTime,status);
        respGenerator=new generator_resp(initialTime,status);

        temperatureGenerator =new generator_temperature(initialTime,status);
        heartGenerator=new generator_heartRate(initialTime,status);
        systolicGenerator =new generator_systolic(initialTime,status);
        diastolicGenerator =new generator_diastolic(initialTime,status);
        respiratoryGenerator=new generator_respiratoryRate(initialTime,status);
    }

    public List<List<Double>> outputValuesSlow(){
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        List<List<Double>> output=new ArrayList<>();
        output.add(temperatureGenerator.outputValues(currentTime));
        output.add(heartGenerator.outputValues(currentTime));
        output.add(systolicGenerator.outputValues(currentTime));
        output.add(diastolicGenerator.outputValues(currentTime));
        output.add(respiratoryGenerator.outputValues(currentTime));
        if (emailAddress != null) {
            for(int i=0;i<5;i++){
                for(double t : output.get(i)){
                    if (t>thr.get(2*i) || t<thr.get(2*i+1)){
                        if (!urgent.get(i)){
                            SendEmail(ref,i);
//                        System.out.println(output+"    "+i);
                        }
                        urgent.set(i,true);
                    }else urgent.set(i,false);
                }
            }
        }
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
