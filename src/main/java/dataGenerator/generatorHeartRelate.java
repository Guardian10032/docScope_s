package dataGenerator;

import java.util.Random;

public abstract class generatorHeartRelate extends generator{
    static int heartIndex=0;
    static int fileHeartIndex;
    public generatorHeartRelate(long initialTime,String status) {
        super(initialTime,status);
    }
    public void randomHeartGenerator(){
        System.out.println("Heart "+heartIndex);
        if(heartIndex==0) {
            Random rand = new Random();
            fileHeartIndex = rand.nextInt(randomLimit);
        }
        heartIndex++;
        if (heartIndex==3){
            heartIndex=0;
        }
    }
}
