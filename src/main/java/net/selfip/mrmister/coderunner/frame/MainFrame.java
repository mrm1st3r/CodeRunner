package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.event.Keyboard;
import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.ApplicationInfo;

import javax.swing.*;
import java.awt.*;

/**
 * the main frame.
 */
public class MainFrame extends JFrame {

	private final ApplicationInfo applicationInfo;
	private final I18n i18n;
	private final KeyConfig keyConfig;
	private final Bounds gameBounds;
	private final GameLoop game;

	public MainFrame(ApplicationInfo applicationInfo, I18n i18n, KeyConfig keyConfig, Bounds gameBounds, GameLoop game) {
		super(applicationInfo.getSignature());
		this.applicationInfo = applicationInfo;
		this.i18n = i18n;
		this.keyConfig = keyConfig;
		this.gameBounds = gameBounds;
		this.game = game;
		setupFrameParameters();
		buildGameComponent();
		Keyboard.registerKeyHandler(this, keyConfig, game.getKeyHandler());
		setVisible(true);
	}

	private void setupFrameParameters() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(gameBounds.getWidth(), gameBounds.getHeight());
		this.setResizable(false);
		setLocationRelativeTo(null);
		setJMenuBar(buildMenuBar());
		setLayout(new BorderLayout());
	}

	private JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = createMenu(menuBar, i18n.t("Game"));

		JMenuItem menuItem = createMenuItem(gameMenu, i18n.t("New"));
		menuItem.addActionListener(e -> {
			if (game.isStarted()) {
				game.stop("");
			}
			try {
				game.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke((char) keyConfig.get("start")));

		createMenuItem(gameMenu, i18n.t("Quit"))
				.addActionListener(e -> System.exit(0));

		JMenu helpMenu = createMenu(menuBar, i18n.t("Help"));

		createMenuItem(helpMenu, i18n.t("About"))
				.addActionListener(a -> new AboutDialog(MainFrame.this, applicationInfo, i18n));

		createMenuItem(helpMenu, i18n.t("Key configuration"))
			.addActionListener(a -> new KeyConfigDialog(MainFrame.this, i18n, keyConfig));

		setJMenuBar(menuBar);

		return menuBar;
	}

	private JMenuItem createMenuItem(JMenu menu, String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menu.add(menuItem);
		return menuItem;
	}

	private JMenu createMenu(JMenuBar menuBar, String name) {
		JMenu gameMenu = new JMenu(name);
		menuBar.add(gameMenu);
		return gameMenu;
	}

	private void buildGameComponent() {
		RunnerPanel game = new RunnerPanel(i18n, this.game, gameBounds);
		game.setSize(gameBounds.getWidth(), gameBounds.getHeight());
		add(game, BorderLayout.CENTER);
	}
}
