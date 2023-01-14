package multiThread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class averageCalculatorTest {

    @Test
    void update() {
        averageCalculator average =new averageCalculator();
        List<List<Double>> temp=new ArrayList<>();
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        temp.add(Arrays.asList(0.0,0.1,0.2));
        for (int i=0;i<41;i++) {
            average.update(temp);
            List<Double>test =average.output();
            System.out.println(Arrays.toString(average.Signals));
            System.out.println(test);
        }
    }
    @Test
    void output(){

    }
}