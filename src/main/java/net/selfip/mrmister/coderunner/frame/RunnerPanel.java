package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.entities.Entity;
import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.DisplayWriter;
import net.selfip.mrmister.coderunner.util.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel showing the actual game.
 */
public class RunnerPanel extends JPanel implements GameLoop.Viewport {

	private static final Logger LOG = LoggerFactory.getLogger(RunnerPanel.class);

	private final I18n i18n;
	private final GameLoop game;
	private final Bounds gameBounds;
	private BufferedImage backgroundImage;
	private String msg;

	RunnerPanel(I18n i18n, GameLoop game, Bounds gameBounds) {
		super();
		this.i18n = i18n;
		this.game = game;
		this.gameBounds = gameBounds;
		game.setViewport(this);

		try {
			backgroundImage = Images.loadImage("background.png");
		} catch (Exception e) {
			LOG.warn("Could not load background image", e);
		}

		msg = i18n.t("start_msg");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);

		DisplayWriter out = new DisplayWriter(g, this);

		if (game.devMode()) {
			out.println("FPS: " + game.currentFps());
			out.println(game.getEntities().size() + " entities");
		}

		for (Entity e : game.getEntities()) {
			drawEntity(g, e);
		}
		out.printlnRight(i18n.t("energy") + ": " + game.getPlayer().getEnergy());

		if (msg != null) {
			out.printCentered(msg);
		}
	}

	private void drawBackground(Graphics g) {
		int splitPoint = -(gameBounds.getOffset() % gameBounds.getWidth());
		g.drawImage(backgroundImage, splitPoint, 0, this);
		g.drawImage(backgroundImage, splitPoint + gameBounds.getWidth(), 0, this);
	}

	private void drawEntity(Graphics g, Entity e) {
		g.drawImage(e.currentImage(), e.getXPosition() - gameBounds.getOffset(), relativeYPosition(e) , null);
	}

	private int relativeYPosition(Entity e) {
		return gameBounds.getHeight() - e.getYPosition() - e.height();
	}

	@Override
	public void update() {
		repaint();
	}

	@Override
	public void print(String info) {
		msg = info;
	}
}
