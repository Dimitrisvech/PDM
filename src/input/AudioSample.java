package input;

import wavFile.WavFile;
import java.io.*;
/**
 * Created by BlackDragon on 23/03/2016.
 */
public class AudioSample {

    //region DATA
    protected WavFile audioFile; //
    protected String fileName; // to categorize by severity?
    //endregion

    //region Constructor
    public AudioSample(String path){
        try {
            audioFile = WavFile.openWavFile(new File(path));
            fileName = new File(path).getName();
        }
        catch(Exception e){
            //Show Error or throw e
        }
    }
    //endregion

    //audioFile getter
    public WavFile getAudioFile()
    {
        return audioFile;
    }
    //end getter

    //fileName getter
    public String getFileName()
    {
        return fileName;
    }
    //end getter

    public void Display(){
        audioFile.display();
    }
}
