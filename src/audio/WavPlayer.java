package audio;

import java.io.*;
import sun.audio.*;


public class WavPlayer {

    public WavPlayer(String url) {
        // open the sound file as a Java input stream
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(getClass().getResourceAsStream(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        AudioPlayer.player.start(audioStream);
    }
}