import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer implements LineListener {

    private Clip audioClip;
    private AudioInputStream audioStream;

    @Override
    public void update(LineEvent event) {
    }

    void play(String audioFilePath, int loopCount) {
        if (audioClip != null || audioStream != null) {
            stop();
        }
        try {
            InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(audioFilePath);

            InputStream bufferedStream = new BufferedInputStream(inputStream);

            audioStream = AudioSystem.getAudioInputStream(bufferedStream);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.loop(loopCount);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.out.println("Error occured during playback process:"+ ex.getMessage());
        }
    }

    void stop() {
        try {
            audioClip.close();
            audioStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}