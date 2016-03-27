package testUI;

import input.*;
/**
 * Created by BlackDragon on 23/03/2016.
 */
public class AppMain {
    public static void main(String[] args) {
        String path="D:\\temp\\a.wav";
        AudioSample sample=new AudioSample(path);
        sample.Display();
        //SampleDatabase sampleDB = new SampleDatabase(folderPath); //populate database
        //WindowedDatabase winDB = new WindowedDatabase(sampleDB);
        //winDB.windowing(); //populate collection of indices inside sample files. Should be better than actually creating new files
    }
}
