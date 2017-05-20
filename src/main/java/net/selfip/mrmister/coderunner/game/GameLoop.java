package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.AbstractEntity;
import net.selfip.mrmister.coderunner.entities.EntityFactory;
import net.selfip.mrmister.coderunner.entities.Player;
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

    private Player player;
    private final List<AbstractEntity> entities = new LinkedList<>();
    private Viewport viewport;

    private boolean paused = false;
    private boolean started = false;
    private boolean devMode = false;

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
    public void toggleDevMode() {
        devMode = !devMode;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * start a new game.
     */
    public void start() throws IOException {
        if (started) {
            return;
        }
        entities.add(player);

        LOG.info("starting a new game");
        last = System.nanoTime();

        started = true;
        Thread th = new Thread(this);
        th.start();
        spawner.start();
    }

    @Override
    public void run() {
        LOG.info("Main Loop starting");
        while (started) {

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
        if (entities != null && !paused) {
            for (int i = 0; i < entities.size(); i++) {

                AbstractEntity s = entities.get(i);
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
            if (!(entities.get(i) instanceof Player)
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
        if (!started) {
            return;
        }

        viewport.print(String.format("%s\n %s: %d", newMsg, i18n.t("score"), gameBounds.getOffset()));
        started = false;
        spawner.stop();
    }

    /**
     * pause the current game.
     */
    public void pause() {
        if (paused || !started) {
            return;
        }

        LOG.info("paused the running game");
        paused = true;
    }

    /**
     * resume a paused game.
     */
    public void resume() {
        if (!paused || !started) {
            return;
        }

        LOG.info("resumed the game");
        paused = false;
    }

    /**
     * @return whether or not the game is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @return whether or not the game is paused
     */
    public boolean isStarted() {
        return started;
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

    public List<AbstractEntity> getEntities() {
        return entities;
    }

    @Override
    public void spawnEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public interface Viewport {
        void update();

        void print(String info);
    }
}
