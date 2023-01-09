package dataGenerator;

import java.util.Random;

public class generatorHeartRelate extends generator{
    static int heartIndex=0;
    static int fileHeartIndex;
    public generatorHeartRelate(long initialTime) {
        super(initialTime);
    }
    public void randomHeartGenerator(){
        System.out.println("Heart "+heartIndex);
        if(heartIndex==0) {
            Random rand = new Random();
            fileHeartIndex = rand.nextInt(99);
        }
        heartIndex++;
        if (heartIndex==3){
            heartIndex=0;
        }
    }
}
