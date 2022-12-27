package dataGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    public List<Double> values = new ArrayList();

    public List<Double> loadFile(String filename){
        String line;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/adult_data/"+filename+".csv");
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        try (BufferedReader br = new BufferedReader(streamReader)) {
            while((line = br.readLine()) != null){
                this.values.add(Double.valueOf(line));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return values;
    }

//    public double outputValues(){
//        int index=(int) Math.floor((double) fs*(currentTime-initial_time)/1000);
//        return (Double) this.values.get(index);
//    }
}