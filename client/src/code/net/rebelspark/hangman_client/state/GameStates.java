package code.net.rebelspark.hangman_client.state;

import code.net.rebelspark.hangman_client.renderer.util.Button;

import java.awt.*;
import java.awt.image.BufferedImage;//double underscore indicates subscreen. syntax main_screen__sub_screen
public enum GameStates {
    MAIN_MENU,
    MAIN_MENU__EXIT_SCREEN,
    SETTINGS,
    GAME_MENU,
    IN_GAME_BASE,
    SHOP
}
