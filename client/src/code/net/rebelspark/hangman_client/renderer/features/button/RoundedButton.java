package code.net.rebelspark.hangman_client.renderer.features.button;

import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoundedButton extends Button{
    protected RoundedButton(String name, GameStates screen, float x, float y, float w, float h, String text, float textSize, BufferedImage idleImage, BufferedImage hoverImage, Color idleColor, Color hoverColor, Color idleTextColor, Color hoverTextColor, Border border, Type type, boolean drawFromTopLeft, Runnable runnable, int[] keyboardSelectIndex, Font font) {
        super(name, screen, x, y, w, h, text, textSize, idleImage, hoverImage, idleColor, hoverColor, idleTextColor, hoverTextColor, border, type, drawFromTopLeft, runnable, keyboardSelectIndex, font);
    }

    public static class Properties extends Button.Properties {
        public Properties(GameStates screen, String name, float x, float y, Runnable runnable) {
            super(screen, name, x, y, runnable);
        }
    }



}
