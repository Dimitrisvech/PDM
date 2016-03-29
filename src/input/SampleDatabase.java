package input;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Arik on 26-Mar-16.
 * Collection of all samples
 * to add: several folderpaths,
 */
public class SampleDatabase
{

    protected Collection<AudioSample> SampleDB;

	public String getPath()
	{
		return path;
	}

	protected String path;

    //Constructor
    public SampleDatabase(String folderPath)
    {
	    File f = new File(folderPath);
        //File[] files = f.listFiles();
	    ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        //try {
            //files = new File(folderPath).listFiles();
        //}
        //catch (NullPointerException e) {
            //Show error or throw e
            //Null pointer exception
            //return;
        //}

        SampleDB = new ArrayList<>();

        try {
            // Add samples to sample collection
            for (File file : files) {
                if (file.isFile() == true)
	                SampleDB.add(new AudioSample(file.getPath()));
            }
        }
        catch (Exception e) {
            //Show error or throw e
        }

	    path = folderPath;
    }

    public Collection<AudioSample> getSampleDB()
    {
        return SampleDB;
    }


}
