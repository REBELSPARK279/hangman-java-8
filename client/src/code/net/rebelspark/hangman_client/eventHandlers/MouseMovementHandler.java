package code.net.rebelspark.hangman_client.eventHandlers;

import code.net.rebelspark.hangman_client.renderer.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMovementHandler implements MouseMotionListener {

    private static int mouseX;
    private static int mouseY;

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        GamePanel.panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        GamePanel.panel.repaint();
    }

    public static int[] getMouse() {
        return (new int[]{mouseX, mouseY});
    }

    public static int getMouseX() {
        return (mouseX);
    }

    public static int getMouseY() {
        return (mouseY);
    }

}
