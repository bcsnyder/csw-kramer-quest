
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
    String audioFilePath;
    String audioFilePath2;
    private Clip audioClip;
    Thread musicLooper;
    private boolean isPlayingMenuMusic;

    /**
     * Play a given audio file.
     * @param audioFilePath Path of the file to be played.
     */
    void playLoop1() {
        File audioPath = new File(audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);
            
            isPlayingMenuMusic = true;
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

    void playLoop2() {
        musicLooper = new Thread(new MusicThread());
        musicLooper.start();
        return;
    }

    void stop() {
        audioClip.stop();
        isPlayingMenuMusic = false;
    }
    
    boolean isPlayingMenuMusic() {
        return isPlayingMenuMusic;
    }

    void stopThread() throws InterruptedException {
        musicLooper.interrupt();
    }

    /**
     *  Marks the beginning and ending of the audio clip.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            //System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            //System.out.println("Playback completed.");
        }

    }

    public void setPath(String filePath) {
        audioFilePath = filePath;
    }

    public void setPath2(String filePath) {
        audioFilePath2 = filePath;
    }

    private class MusicThread implements Runnable {
        public void run(){
            File audioPath = new File(audioFilePath);
            File audioPath2 = new File(audioFilePath2);
            int currentSong = 1;
            File currentPath = audioPath;

            try {
                while(!Thread.currentThread().isInterrupted()) {
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }

                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(currentPath);

                        AudioFormat format = audioStream.getFormat();

                        DataLine.Info info = new DataLine.Info(Clip.class, format);

                        audioClip = (Clip) AudioSystem.getLine(info);

                        audioClip.addLineListener(new SoundSystem());

                        audioClip.open(audioStream);
                        audioClip.start();
                        
                        if (currentSong == 1) {
                            currentSong++;
                            currentPath = audioPath2;
                        } else {
                            currentSong--;
                            currentPath = audioPath;
                        }
                        
                        while(audioClip.getMicrosecondLength() != audioClip.getMicrosecondPosition()) {
                            if (Thread.currentThread().isInterrupted()) {
                                audioClip.stop();
                            }
                        }
                        audioClip.stop();
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

            } catch (InterruptedException e) {
                System.out.println("Stopped playing.");
            }
        }
    }
}
