package audio;

import java.io.*;
import sun.audio.*;


public class WavPlayer {

    public WavPlayer(String url) {
        // open the sound file as a Java input stream
        InputStream in = null;
        AudioStream audioStream = null;
        try {
            in = new FileInputStream(url);
            audioStream = new AudioStream(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        AudioPlayer.player.start(audioStream);
    }
}