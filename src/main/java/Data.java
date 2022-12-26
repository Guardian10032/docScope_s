import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    public String line;
    public ArrayList times = new ArrayList();
    public List<Double> values = new ArrayList();
    public String file = null;
    public long initial_time;
    public boolean toBeComputed = true;

    public Data(String filename,long initial_time){
        this.initial_time=initial_time;
        this.file = "C:/Users/Fitz_Liu/OneDrive - Imperial College London/Prog3/docScope/adult_data/"+filename+".csv";
        if (toBeComputed){
            try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
                while((line = br.readLine()) != null){
                    List v = Arrays.asList(line.split(","));
                    double time=Double.parseDouble((String) v.get(0));
                    double value=Double.parseDouble((String) v.get(1));
                    this.times.add(time*1000);
                    this.values.add(value);
                }
            /*this.times.forEach(l -> System.out.println(l));
            this.values.forEach(n -> System.out.println(n));*/
            } catch (Exception e){
                System.out.println(e);
            }
        }
        this.toBeComputed = false;
        System.out.println("Data loaded!");
    }

    public double outputValues(long currentTime,int fs){
        int index=(int) Math.floor((double) fs*(currentTime-initial_time)/1000);
        return (Double) this.values.get(index);
    }
}