package code.net.rebelspark.hangman_client.renderer.features.interfaces;

import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.event.MouseEvent;

public interface InputtableFeature {
    void mouseClicked(MouseEvent e);
    void enterClicked(int[] selection);
    void input();
    GameStates getRenderScreen();
}
