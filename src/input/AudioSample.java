package input;

import wavFile.WavFile;
import java.io.*;
/**
 * Created by BlackDragon on 23/03/2016.
 */
public class AudioSample {

    //region DATA
    public WavFile audioFile;
    //endregion

    //region Constructor
    public AudioSample(String path){
        try {
            audioFile = WavFile.openWavFile(new File(path));
        }
        catch(Exception e){
            //Show Error or throw e
        }
    }
    //endregion

    public void Display(){
        audioFile.display();
    }
}
