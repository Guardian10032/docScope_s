package multiThread;

import java.util.ArrayList;
import java.util.List;

/**
 * calculate the average when their 60 values
 */
public class averageCalculator {
    //receive a list<list<double>> each list is one type of signal
    //update the average if there are 60 values and delete them
    List[] Signals;
    public averageCalculator(){
        Signals=new List[] {new ArrayList<>(),new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>()};
    }

    /**
     * add values to averageCalculator
     * @param newSignals new signals
     */
    public void update (List<List<Double>> newSignals){
        for (int i=0;i<5;i++){
            Signals[i].addAll(newSignals.get(i));
        }
    }

    /**
     * check whether there are 60 values. If true, calculate and return the average.
     * If false do nothing and return null
     * @return null or average
     */
    public List<Double> output(){
        if (Signals[0].size()>=60){
            List<Double> average=new ArrayList<>();
            for (int i=0;i<5;i++){
                double sum=0.0;
                for (int t=0;t<60;t++) {
                    sum += (double)Signals[i].get(t);
                }
                average.add(sum/60);
                Signals[i].subList(0,60).clear();//delete signals whose averages have been calculated
            }
            return average;
        }
        else return null;
    }
}