package dataGenerator;

import java.util.Random;

public abstract class generatorRespiratoryRelate extends generator{
    static int respiratoryIndex=0;
    static int fileRespiratoryIndex;
    public generatorRespiratoryRelate(long initialTime,String status) {
        super(initialTime,status);
    }
    public void randomRespiratoryGenerator(){
        System.out.println("Respiratory"+respiratoryIndex);
        if(respiratoryIndex==0) {
            Random rand = new Random();
            fileRespiratoryIndex = rand.nextInt(randomLimit);
        }
        respiratoryIndex++;
        if (respiratoryIndex==2){
            respiratoryIndex=0;
        }
    }
}
