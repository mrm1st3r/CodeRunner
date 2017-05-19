package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.entities.AbstractEntity;
import net.selfip.mrmister.coderunner.entities.Bug;
import net.selfip.mrmister.coderunner.entities.Coffee;
import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.DisplayWriter;
import net.selfip.mrmister.coderunner.util.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel showing the actual game.
 */
public class RunnerPanel extends JPanel implements GameLoop.Viewport {

	private final GameLoop game;
	private final Bounds gameBounds;
	private BufferedImage backgroundImage;
	private String msg;

	RunnerPanel(I18n i18n, GameLoop game, Bounds gameBounds) {
		super();
		this.game = game;
		this.gameBounds = gameBounds;
		game.setViewport(this);

		try {
			backgroundImage = Images.loadImage("background.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Coffee.init();
		Bug.init();

		msg = i18n.t("start_msg");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		DisplayWriter out = new DisplayWriter(g, this);

		drawBackground(g);

		if (GameLoop.devMode()) {
			out.println("FPS: " + game.currentFps());
			out.println(game.getEntities().size() + " entities");
		}

		for (AbstractEntity e : game.getEntities()) {
			e.draw(g, out);
		}

		if (msg != null) {
			out.printCentered(msg);
		}
	}

	private void drawBackground(Graphics g) {
		int splitPoint = -(gameBounds.getOffset() % gameBounds.getWidth());
		g.drawImage(backgroundImage, splitPoint, 0, this);
		g.drawImage(backgroundImage, splitPoint + gameBounds.getWidth(), 0, this);
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
