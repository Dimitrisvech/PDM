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
    }
}
