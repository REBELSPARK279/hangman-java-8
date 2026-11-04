package code.net.rebelspark.hangman_client.eventHandlers;

import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.CheckBox;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MouseListenerHandler implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        Logger.DEBUG("click");
        for (Button button : new ArrayList<>(Button.buttons)) {
            if (button.getRenderScreen() == GameStateHandler.getGameState()) {
                button.mouseClicked(e);
            }
        }
        for (CheckBox checkBox: CheckBox.checkBoxes) {
            if (checkBox.getRenderScreen() == GameStateHandler.getGameState()) {
                checkBox.mouseClicked();
            }
        }
        GameStateHandler.setDelayedState();
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
