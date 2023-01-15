package dataGenerator;

import java.util.Random;

/**
 * class to make sure all respiratory related signal using the same random number to select file.
 * Thus, the rate of resp is the same as the respiratory rate.
 */
public abstract class generatorRespiratoryRelate extends generator{
    static int respiratoryIndex=0;
    /**
     * random number generated based patient status
     */
    static int fileRespiratoryIndex;
    public generatorRespiratoryRelate(long initialTime,String status) {
        super(initialTime,status);
    }
    /**
     * update random number
     */
    public void randomRespiratoryGenerator(){
        //when respiratoryIndex is 0, generate a new random number
        if(respiratoryIndex==0) {
            Random rand = new Random();
            fileRespiratoryIndex = rand.nextInt(randomLimit);
        }
        respiratoryIndex++;
        //when respiratoryIndex is 2, it means resp and rate generator all have called this method.
        // A new random number is needed next time.
        if (respiratoryIndex==2){
            respiratoryIndex=0;
        }
    }
}
