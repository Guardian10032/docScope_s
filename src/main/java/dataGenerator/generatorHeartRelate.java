package dataGenerator;

import java.util.Random;

/**
 * class to make sure all heart related signal using the same random number to select file.
 * Thus, the rate of ecg is the same as the heart rate.
 */
public abstract class generatorHeartRelate extends generator{
    static int heartIndex=0;
    /**
     * random number generated based patient status
     */
    static int fileHeartIndex;
    public generatorHeartRelate(long initialTime,String status) {
        super(initialTime,status);
    }

    /**
     * update random number
     */
    public void randomHeartGenerator(){
        //when heartIndex is 0, generate a new random number
        if(heartIndex==0) {
            Random rand = new Random();
            fileHeartIndex = rand.nextInt(randomLimit);
        }
        heartIndex++;
        //when heartIndex is 3, it means ecg1, ecg2 and heart rate generator all have called this method.
        // A new random number is needed next time.
        if (heartIndex==3){
            heartIndex=0;
        }
    }
}
