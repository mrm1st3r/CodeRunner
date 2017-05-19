package net.selfip.mrmister.coderunner.game;

import net.selfip.mrmister.coderunner.entities.AbstractEntity;
import net.selfip.mrmister.coderunner.entities.Player;
import net.selfip.mrmister.coderunner.entities.SpawnManager;
import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.frame.MainFrame;
import net.selfip.mrmister.coderunner.frame.RunnerPanel;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Vector;

/**
 * Main game loop.
 */
public class GameLoop implements Runnable {

    private static final int FPS_LIMIT = 60;
    private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);
    private static boolean devMode = false;

    private MainFrame mainFrame;
    private final I18n i18n;
    private final KeyConfig keyConfig;
    private RunnerPanel panel;

    private Player player;
    private Vector<AbstractEntity> entities;
    private SpawnManager spawner;

    private double progress = 0;
    private boolean paused = false;
    private boolean started = false;
    private String msg;

    private long delta = 0;
    private long last = 0;
    private long fps = 0;

    public GameLoop(I18n i18n, KeyConfig keyConfig) {
        this.i18n = i18n;
        this.keyConfig = keyConfig;
    }

    /**
     * @return whether developer mode is activated or not
     */
    public static boolean devMode() {
        return devMode;
    }

    /**
     * toggle developer mode.
     */
    public static void toggleDevMode() {
        devMode = !devMode;
    }

    public void setFrame(MainFrame frame, RunnerPanel panel) {
        mainFrame = frame;
        this.panel = panel;
    }

    /**
     * start a new game.
     */
    public void start() throws IOException {
        if (started) {
            return;
        }

        msg = null;
        progress = 0;

        entities = new Vector<>();
        player = new Player(panel, i18n, this);
        player.registerKeyHandler(mainFrame, keyConfig);
        entities.add(player);

        LOG.info("starting a new game");
        last = System.nanoTime();

        spawner = new SpawnManager(panel, this);

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

            panel.repaint();

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

                AbstractEntity s = entities.elementAt(i);
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
            if (!(entities.elementAt(i) instanceof Player)
                    && entities.elementAt(i).collidedWith(player)) {
                entities.remove(i);
            }
        }
    }

    /**
     * add a new entity to the game.
     * @param e new entity
     */
    public void addEntity(AbstractEntity e) {
        entities.add(e);
    }

    /**
     * end the current game.
     * @param newMsg message to be displayed
     */
    public void stop(String newMsg) {
        if (!started) {
            return;
        }

        msg = newMsg + "\n" + i18n.t("score") + ": " + (int) getProgress();
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
     * get the current game progress.
     * @return progress in length-units
     */
    public double getProgress() {
        return progress;
    }

    /**
     * increment game progress.
     * @param p increment value
     */
    public void progress(int p) {
        progress += p;
    }

    private void calculateDelta() {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = Time.NANOS_PER_SEC / delta;
    }

    public long currentFps() {
        return fps;
    }

    public Vector<AbstractEntity> getEntities() {
        return entities;
    }
}
