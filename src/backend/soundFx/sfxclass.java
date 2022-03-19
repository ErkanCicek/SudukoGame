import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class sfxclass {
    File file;
    AudioInputStream audioInputStream;
    Clip clip;

    public sfxclass(String pathtosoundfile) {
        this.file = new File(pathtosoundfile);
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(this.file);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            this.clip.open(this.audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound(){
        this.clip.start();
    }
}
