package testUI;

import input.*;
import windowing.Windowing;

import java.util.Iterator;

/**
 * Created by BlackDragon on 23/03/2016.
 */
public class AppMain {
    public static void main(String[] args) {
        //String path="D:\\temp\\a.wav";
	    String pathDB="D:\\temp\\";
        SampleDatabase sampleDb = new SampleDatabase(pathDB);
        //AudioSample sample=new AudioSample(path);
        //sample.Display();
	    for (Iterator<AudioSample> iterator = sampleDb.getSampleDB().iterator(); iterator.hasNext(); )
	    {
		    AudioSample audioSample = iterator.next();
		    audioSample.Display();
	    }

	    Windowing.createWindowedDatabase(sampleDb, 20, 0.5, "D:\\temp\\windowed");
        //SampleDatabase sampleDB = new SampleDatabase(folderPath); //populate database
        //WindowedDatabase winDB = new WindowedDatabase(sampleDB);
        //winDB.windowing(); //populate collection of indices inside sample files. Should be better than actually creating new files
    }
}
