package net.selfip.mrmister.coderunner.entities;

/**
 * Base interface for entities that are controlled by a player.
 */
public interface PlayableEntity extends Entity {

    void startJump();

    void stopJump();

    void moveLeft();

    void moveRight();

    void stop();

    State getState();

    void reset();

    int getEnergy();

    enum State {
        ALIVE, ASLEEP, CAFFEINE_SHOCKED, DEPRESSED
    }
}
