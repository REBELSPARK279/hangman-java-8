package code.net.rebelspark.hangman_client.eventHandlers;

import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.features.interfaces.InputtableFeature;
import code.net.rebelspark.hangman_client.renderer.features.util.InputtableFeatures;
import code.net.rebelspark.hangman_client.renderer.overlay.in_game.EndOverlay;
import code.net.rebelspark.hangman_client.renderer.features.Button;
import code.net.rebelspark.hangman_client.renderer.features.CheckBox;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MouseListenerHandler implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        Logger.DEBUG("click");
        for (InputtableFeature clickable: new ArrayList<>(InputtableFeatures.INPUTTABLE_FEATURES)) {
            if (clickable.getRenderScreen().equals(GameStateHandler.getGameState()) ||
                    (EndOverlay.getState() && clickable.getRenderScreen() == GameStates.END_SCREEN &&
                    (GameStateHandler.getGameState() == GameStates.IN_GAME_BASE))) {
                clickable.mouseClicked(e);
            }
        }
        GameStateHandler.setDelayedState();
        EndOverlay.setDelayedState();
        GamePanel.panel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
