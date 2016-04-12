package windowing;

import input.AudioSample;
import input.SampleDatabase;
import wavFile.WavFileException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Arik on 26-Mar-16.
 * divide sample to windows according to given rate
 */
public class Windowing
{

    //protected long windowSize;
    protected Collection<double[]> windowedSamples;
    protected String folderPath;
    private static ArrayList<String> fileList;

    //constructor
    public Windowing(AudioSample audioSample, int windowSizeMilliseconds, double winOverlayPercentage, String folderPath)
    {
        createWindowedSample(audioSample, windowSizeMilliseconds, winOverlayPercentage, folderPath);
    }

	public static void createWindowedDatabase(SampleDatabase sampleDB, int windowSizeMilliseconds, double winOverlayPercentage, String folderPath)
	{
		for (AudioSample audioSample : sampleDB.getSampleDB())
		{
			createWindowedSample(audioSample, windowSizeMilliseconds, winOverlayPercentage, folderPath);
		}
	}

    public static void createWindowedSample(AudioSample audioSample, int windowSizeMilliseconds, double winOverlayPercentage, String folderPath)
    {
        long windowSizeSamples;
        long winOverlaySamples;
        double[][] buffer;
        int framesRead = 0;
        Integer i = 0;

        //delete folder UNSAFE AS ALL HELL!!!!!
//        File index = new File(folderPath);
//        String[] entries = index.list();
//        for (String s:entries) {
//            File currentFile = new File(index.getPath(), s);
//            currentFile.delete();
//        }
        fileList = new ArrayList<>();
        String path = folderPath + File.separator + audioSample.getFileName()+"_w.txt";

        windowSizeSamples = (long)windowSizeMilliseconds * audioSample.getAudioFile().getSampleRate() / 1000;
        winOverlaySamples = (long)(windowSizeSamples * winOverlayPercentage);

        buffer = new double[audioSample.getAudioFile().getNumChannels()][(int)windowSizeSamples];

        //first read at 100% window size
        try {
            framesRead = audioSample.getAudioFile().readFrames(buffer, (int)windowSizeSamples);
            //framesRead = audioSample.getAudioFile().readFrames(buffer, (int)windowSizeSamples/audioSample.getAudioFile().getNumChannels());
        } catch (IOException | WavFileException e) {
            e.printStackTrace();
        }
        //path = path + "_" + i.toString() + ".txt";
        writeToMemory(buffer);
        //path = folderPath + File.separator + audioSample.getFileName();
        i++;

        //long curWinSize = windowSizeSamples;
        do
        {
            //save overlay with next window
            double[][] tempBuffer = new double[(int)winOverlaySamples][(int)windowSizeSamples];
            for (int j=0; j<audioSample.getAudioFile().getNumChannels(); j++)
                for (int k=0; k<(int)windowSizeSamples; k++)
                    tempBuffer[j][k] = buffer[audioSample.getAudioFile().getNumChannels() - 1 - j][(int)windowSizeSamples - 1 - k];

            // read 1-overlay% to last overlay% of buffer
            try {
                framesRead = audioSample.getAudioFile().readFrames(buffer, (int)winOverlaySamples, (int)windowSizeSamples-(int)winOverlaySamples);
                //framesRead = audioSample.getAudioFile().readFrames(buffer, (int)windowSizeSamples/audioSample.getAudioFile().getNumChannels());
            } catch (IOException | WavFileException e) {
                e.printStackTrace();
            }

            //write back to beginning of buffer
            for (int j=0; j<audioSample.getAudioFile().getNumChannels(); j++)
                for (int k=0; k<(int)windowSizeSamples; k++)
                    buffer[j][k] = tempBuffer[j][k];


            writeToMemory(buffer);
            //path = folderPath + File.separator + audioSample.getFileName();
            i++;
        }
        while(framesRead != 0 || audioSample.getAudioFile().getFramesRemaining() >= windowSizeSamples);
        writeToFile(path);
        //audioSample.getAudioFile().readFrames(/*buffer, offset, numOfFramesToRead*/);

    }

    private static void writeToMemory(double[][] buffer)
    {
        List<String> lines = Arrays.asList(Arrays.deepToString(buffer));
        fileList.add(lines.get(0));

    }

    private static void writeToFile(String path)
    {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //List<String> lines = Arrays.asList(Arrays.deepToString(buffer));
        Path fPath = Paths.get(f.getAbsolutePath());
        try {
            Files.write(fPath, fileList, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
