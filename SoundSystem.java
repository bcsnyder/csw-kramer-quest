
 
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
public class SoundSystem implements LineListener {
     
    /**
     * Indicates if the file has finished playing.
     */
    boolean playCompleted;
    private Clip audioClip;
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the file to be played.
     */
    void play(String audioFilePath) {
        File audioPath = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
         
    }
    void stop() {
       /**
     * Stops the looping file.
     */
        audioClip.stop();
        
        
    }
     
    /**
     *  Marks the beginning and ending of the audio clip.
     */
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
   /**
     * Example of how to use the SoundSytem.
     */
    //public static void main(String[] args) {
     //   String audioFilePath = "U:/Famitracker/Test/Class_fluidvolt-The_Gusts_of_Aeolus.wav";
    //    SoundSystem player = new SoundSystem();
    //    player.play(audioFilePath);
        
    }
 
}

