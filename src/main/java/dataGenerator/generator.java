package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

/**
 * abstract class for generators of each type.
 */
abstract class generator {
    Data data=new Data();
    List<Double> Former;
    List<Double> Latter;
    /**
     * size of the values from the file that is currently using.
     * It is used to determine whether all the values are used or not.
     */
    int size;
    long previousTime;
    long initialTime;
    int index1=0;
    int index2=0;
    int fileIndex;
    int interval;
    /**
     * determined by whether the patient should be normal or abnormal
     */
    int randomLimit;

    /**
     * determine the patient is normal or abnormal
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
    public generator(long initialTime,String status){
        this.initialTime=initialTime;
        if(status=="normal"){
            randomLimit=49;
        }else randomLimit=99;
    }

    /**
     * select the file randomly based on the status of the patient
     * @return file name
     */
    public abstract String fileSelector();

    /**
     * give the value of correspond type when the method is called
     * @param currentTime the time when the method is called
     * @return values from last time it was called to the current time
     */
    public List<Double> outputValues(long currentTime){
        if (index2>size-1){
            //when all the value from the first file is used, clear them,
            // load a new file and add the values to the end of the values from the second file
            initialTime=initialTime+ (long) interval *size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        //calculate the corresponding index based on time
        index1= (int) ceil((double) (previousTime-initialTime)/interval);
        index2= (int) ceil((double) (currentTime-initialTime)/interval);
        //save the current time to previous time for the next call
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }
}
