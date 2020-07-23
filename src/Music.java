import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music implements LineListener, Runnable{

    boolean playCompleted;
    public synchronized void playMusicLoop(String file)
    {
        File audioFile = new File(file);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.loop(999);

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();

        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }

    @Override
    public void run() {
        playMusicLoop(file);
    }

    String file;
    public void play(String _file)
    {
        file = _file;
        new Thread(this).start();
    }
}