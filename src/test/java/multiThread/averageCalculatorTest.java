package multiThread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class averageCalculatorTest {
    //test whether the calculator will calculate the average of first 60 values
    //then erase them
    @Test
    void update() {
        averageCalculator average =new averageCalculator();
        List<List<Double>> temp=new ArrayList<>();
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        for (int i=0;i<25;i++) {
            average.update(temp);
            List<Double>test =average.output();
            if (test == null) {
            }else {
                assertEquals(i,19);
                assertEquals(0,average.Signals[0].size());//size should be zero when erase the first 60 values
                for (double t:test){
                    assertEquals(0.10000000000000002,t);//compare the average
                }
            }
        }
    }
}