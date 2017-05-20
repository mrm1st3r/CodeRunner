package net.selfip.mrmister.coderunner.event;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class mapping pressed keys to event methods.
 */
public class Keyboard extends KeyAdapter {

    private KeyConfig keyConfig;
    private KeyEventHandler handler;

    private Keyboard(KeyConfig keyConfig, KeyEventHandler keyEventHandler) {
        this.keyConfig = keyConfig;
        this.handler = keyEventHandler;
    }

    public static void registerKeyHandler(JFrame frame, KeyConfig keyConfig, KeyEventHandler handler) {
        KeyListener[] l = frame.getKeyListeners();
        for (KeyListener k : l) {
            if (k instanceof Keyboard) {
                frame.removeKeyListener(k);
            }
        }
        frame.addKeyListener(new Keyboard(keyConfig, handler));
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = Character.toLowerCase((char) e.getKeyCode());

        if (key == keyConfig.get("move_left")) {
            handler.onMoveLeftPressed();
        } else if (key == keyConfig.get("move_right")) {
            handler.onMoveRightPressed();
        } else if (key == keyConfig.get("jump")) {
            handler.onJumpPressed();
        } else if (key == keyConfig.get("pause")) {
            handler.onPauseButtonPressed();
        } else if (key == keyConfig.get("dev_mode")) {
            handler.onDevModeButtonPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = Character.toLowerCase((char) e.getKeyCode());

        if (key == keyConfig.get("move_left")) {
            handler.onMoveLeftReleased();
        } else if (key == keyConfig.get("move_right")) {
            handler.onMoveRightReleased();
        } else if (key == keyConfig.get("jump")) {
            handler.onJumpReleased();
        }
    }

    public interface KeyEventHandler {

        void onPauseButtonPressed();

        void onDevModeButtonPressed();

        void onMoveLeftPressed();

        void onMoveRightPressed();

        void onJumpPressed();

        void onMoveRightReleased();

        void onMoveLeftReleased();

        void onJumpReleased();
    }
}
