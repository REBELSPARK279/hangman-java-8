package code.net.rebelspark.hangman_client.init.regestries;

import code.net.rebelspark.hangman_client.util.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.awt.*;

public class Sounds {

    public static AudioInputStream ARIA_MATH;
    public static AudioInputStream BLIND_SPOTS;

    public static void register() {
        ARIA_MATH = registerSound("/resources/sound/song/aria_math.ogg");
        BLIND_SPOTS = registerSound("/resources/sound/song/blind_spots.ogg");
    }

    public static AudioInputStream registerSound(String path) {
        Logger.INFO("Loading sound \"" + path + "\"");
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(Sounds.class.getResourceAsStream(path));
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

    private static String getSoundInfo(AudioInputStream sound) {
        AudioFormat audioFormat = sound.getFormat();
        return ("SOUND [Channels" + audioFormat.getChannels() + ", Rate" + audioFormat.getSampleRate() + ", Encoding" + audioFormat.getEncoding() + "]");
    }

}
