package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.Player;
import net.selfip.mrmister.coderunner.event.Keyboard;

/**
 *
 */
public class GameKeyHandler implements Keyboard.KeyEventHandler {

    private final GameLoop game;
    private final Player player;

    GameKeyHandler(GameLoop gameLoop, Player player) {
        this.game = gameLoop;
        this.player = player;
    }

    @Override
    public void onPauseButtonPressed() {
        if (game.isPaused()) {
            game.resume();
        } else if (game.isStarted()) {
            game.pause();
        }
    }

    @Override
    public void onDevModeButtonPressed() {
        game.toggleDevMode();
    }

    @Override
    public void onMoveLeftPressed() {
        player.moveLeft();
    }

    @Override
    public void onMoveRightPressed() {
        player.moveRight();
    }

    @Override
    public void onJumpPressed() {
        player.startJump();
    }

    @Override
    public void onMoveRightReleased() {
        player.stop();
    }

    @Override
    public void onMoveLeftReleased() {
        player.stop();
    }

    @Override
    public void onJumpReleased() {
        player.stopJump();
    }
}
