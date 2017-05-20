package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.Entity;
import net.selfip.mrmister.coderunner.entities.EntityFactory;
import net.selfip.mrmister.coderunner.entities.PlayableEntity;
import net.selfip.mrmister.coderunner.event.Keyboard;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Main game loop.
 */
public class GameLoop implements Runnable, SpawnManager.SpawnTarget {

    private static final int FPS_LIMIT = 60;
    private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);

    private final GameKeyHandler keyHandler;
    private final Bounds gameBounds;
    private final I18n i18n;
    private final SpawnManager spawner;

    private PlayableEntity player;
    private final List<Entity> entities = new LinkedList<>();
    private Viewport viewport;

    private boolean devMode = false;
    private State state = State.STOPPED;

    private long delta = 0;
    private long last = 0;
    private long fps = 0;

    public GameLoop(Bounds gameBounds, I18n i18n, EntityFactory factory) {
        this.gameBounds = gameBounds;
        this.i18n = i18n;
        spawner = new SpawnManager(this, gameBounds, factory);
        createPlayerObject(factory);
        this.keyHandler = new GameKeyHandler(this, player);
    }

    private void createPlayerObject(EntityFactory factory) {
        try {
            this.player = factory.createPlayer(i18n, this);
        } catch (IOException e) {
            LOG.warn("Could not create player object", e);
        }
    }

    public Keyboard.KeyEventHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * @return whether developer mode is activated or not
     */
    public boolean devMode() {
        return devMode;
    }

    /**
     * toggle developer mode.
     */
    void toggleDevMode() {
        devMode = !devMode;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * start a new game.
     */
    public void start() throws IOException {
        if (state != State.STOPPED) {
            return;
        }
        entities.add(player);

        LOG.info("starting a new game");
        last = System.nanoTime();

        state = State.STARTED;
        Thread th = new Thread(this);
        th.start();
        spawner.start();
    }

    @Override
    public void run() {
        LOG.info("Main Loop starting");
        while (state != State.STOPPED) {

            calculateDelta();

            doLogic();

            viewport.update();

            try {
                Thread.sleep((Time.MILLIS_PER_SEC / FPS_LIMIT));
            } catch (InterruptedException e) {
                LOG.info("interrupted during sleep!");
            }
        }

        LOG.info("Main-Loop is over!");
    }

    private void doLogic() {
        if (entities != null && state != State.PAUSED) {
            for (int i = 0; i < entities.size(); i++) {

                Entity s = entities.get(i);
                s.doLogic(delta);
                s.move(delta);

                if (s.outOfSight()) {
                    entities.remove(i);
                }
            }

            calculateCollisions();
        }
    }

    private void calculateCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            if (!(entities.get(i) instanceof PlayableEntity)
                    && entities.get(i).collidedWith(player)) {
                entities.remove(i);
            }
        }
    }

    /**
     * end the current game.
     * @param newMsg message to be displayed
     */
    public void stop(String newMsg) {
        if (state == State.STOPPED) {
            return;
        }

        viewport.print(String.format("%s\n %s: %d", newMsg, i18n.t("score"), gameBounds.getOffset()));
        state = State.STOPPED;
        spawner.stop();
    }

    /**
     * pause the current game.
     */
    void pause() {
        if (state != State.STARTED) {
            return;
        }

        LOG.info("paused the running game");
        state = State.PAUSED;
    }

    /**
     * resume a paused game.
     */
    void resume() {
        if (state != State.PAUSED) {
            return;
        }

        LOG.info("resumed the game");
        state = State.STARTED;
    }

    /**
     * @return whether or not the game is paused
     */
    boolean isPaused() {
        return state == State.PAUSED;
    }

    /**
     * @return whether or not the game is paused
     */
    public boolean isStarted() {
        return state == State.STARTED;
    }

    /**
     * increment game progress.
     * @param p increment value
     */
    public void progress(int p) {
        gameBounds.addToOffset(p);
    }

    private void calculateDelta() {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = Time.NANOS_PER_SEC / delta;
    }

    public long currentFps() {
        return fps;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public void spawnEntity(Entity entity) {
        entities.add(entity);
    }

    public interface Viewport {
        void update();

        void print(String info);
    }

    public enum State {
        STOPPED, STARTED, PAUSED
    }
}
