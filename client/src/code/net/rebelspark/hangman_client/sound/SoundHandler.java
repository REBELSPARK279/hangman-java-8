package code.net.rebelspark.hangman_client.sound;

import code.net.rebelspark.hangman_client.util.Logger;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundHandler {

    private static AudioInputStream loadSong(String path) {
        Logger.INFO("Loading sound \"" + path + "\"");
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(SoundHandler.class.getResourceAsStream(path));
            if (sound == null) {
                Logger.CRITICAL("Unable to find sound \"" + path + "\"");
                Logger.WARN("Unable to load sound \"" + path + "\"");
                return(null);
            } else {
                Logger.INFO("Loaded sound " + getSoundInfo(sound) + "!");
                return (sound);
            }
        } catch (Exception exception) {
            Logger.WARN("Unable to load sound " + path);
            exception.printStackTrace();
            return(null);
        }
    }

    public static void playSong(String path) throws LineUnavailableException, IOException {
        AudioInputStream stream = loadSong(path);
        AudioFormat pcm = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, stream.getFormat().getSampleRate(), 16,
                stream.getFormat().getChannels(), stream.getFormat().getChannels() * 2, stream.getFormat().getSampleRate(), false);
        AudioInputStream decoded = AudioSystem.getAudioInputStream(pcm, stream);

        SourceDataLine dataLine = AudioSystem.getSourceDataLine(pcm);
        dataLine.open(pcm);
        dataLine.start();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = decoded.read(buffer)) != -1) {
            dataLine.write(buffer, 0, bytesRead);
        }

        dataLine.drain();
        dataLine.stop();
        dataLine.close();
        decoded.close();
        stream.close();

    }

    private static String getSoundInfo(AudioInputStream sound) {
        AudioFormat audioFormat = sound.getFormat();
        return ("SOUND [Channels" + audioFormat.getChannels() + ", Rate" + audioFormat.getSampleRate() + ", Encoding" + audioFormat.getEncoding() + "]");
    }

}
