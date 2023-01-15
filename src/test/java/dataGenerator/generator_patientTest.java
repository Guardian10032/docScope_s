package dataGenerator;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class generator_patientTest {
    //test whether the generator_patient class will give non-null signals

    @Test
    void outputValuesSlow() throws InterruptedException {
        generator_patient patient1 = new generator_patient("patient1","normal");
        generator_patient patient2 = new generator_patient("patient2","abnormal");
        Thread.sleep(2000);
        //signal from normal patient should within threshold
        List<List<Double>> temps=patient1.outputValuesSlow();
        System.out.println(temps);
        int size=temps.get(0).size();
        for (int i=0;i<temps.size();i++) {
            assertEquals(temps.get(i).size(),size);//same size for all signals
            for (int t=0;t<temps.get(i).size();t++){
                assertTrue(temps.get(i).get(t)<patient1.thr.get(2*i) && temps.get(i).get(t)>patient1.thr.get(2*i+1),
                        "error in "+temps.get(i).get(t)+" "+patient1.thr.get(2*i)+" "+patient1.thr.get(2*i+1));
            }
        }
        //signals from abnormal should be non-null
        temps=patient2.outputValuesSlow();
        System.out.println(temps);
        size=temps.get(0).size();
        for (int i=0;i<temps.size();i++) {
            assertEquals(temps.get(i).size(),size);
            for (int t=0;t<temps.get(i).size();t++){
                assertNotNull(temps.get(i).get(t));
            }
        }
    }

    @Test
    void outputValuesFast() throws InterruptedException{
        //both signals from normal and abnormal should be non-null with same size
        generator_patient patient1 = new generator_patient("patient2","normal");
        Thread.sleep(2000);
        List<List<Double>> temps=patient1.outputValuesFast();
        System.out.println(temps);
        int size=temps.get(0).size();
        for (int i=0;i<temps.size();i++) {
            assertEquals(temps.get(i).size(),size);//same size for all signals
            for (int t=0;t<temps.get(i).size();t++){
                assertNotNull(temps.get(i).get(t));
            }
        }
        generator_patient patient2 = new generator_patient("patient2","abnormal");
        temps=patient2.outputValuesFast();
        System.out.println(temps);
        size=temps.get(0).size();
        for (int i=0;i<temps.size();i++) {
            assertEquals(temps.get(i).size(),size);
            for (int t=0;t<temps.get(i).size();t++){
                assertNotNull(temps.get(i).get(t));
            }
        }
    }
}