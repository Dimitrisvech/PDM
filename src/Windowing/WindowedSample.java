package Windowing;

import input.AudioSample;

import java.io.File;
import java.util.Collection;

/**
 * Created by Arik on 26-Mar-16.
 * divide sample to windows according to given rate
 */
public class WindowedSample {

    protected int windowSize;
    protected Collection<File> windowedSamples;
    protected String folderPath;

    //Constructor
    public WindowedSample(AudioSample audioSample, int windowSizeInMilliseconds, String folderPath)
    {
        long windowSizeInSamples = windowSizeInMilliseconds * audioSample.getAudioFile().getSampleRate() ;



    }
}
