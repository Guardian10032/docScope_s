package dataGenerator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class generator_patientTest {

    @Test
    void outputValuesSlow() throws InterruptedException {
        generator_patient patient1 = new generator_patient("patient1");
        Thread.sleep(2000);
//        while (true) {
        for (List<Double> temp : patient1.outputValuesSlow()) {
            System.out.println(temp);
        }
//        }
    }

    @Test
    void outputValuesFast() {
    }
}