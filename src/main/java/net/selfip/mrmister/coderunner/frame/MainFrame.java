package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.ApplicationInfo;
import net.selfip.mrmister.coderunner.CodeRunner;
import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.lang.I18n;

import javax.swing.*;
import java.awt.*;

/**
 * the main frame.
 */
public class MainFrame extends JFrame {

	private RunnerPanel game;
	private final ApplicationInfo applicationInfo;
	private final I18n i18n;
	private final KeyConfig keyConfig;

	public MainFrame(ApplicationInfo applicationInfo, I18n i18n, KeyConfig keyConfig) {
		super(applicationInfo.getSignature());
		this.applicationInfo = applicationInfo;
		this.i18n = i18n;
		this.keyConfig = keyConfig;

		buildGameComponent();
		setupFrameParameters();
	}

	private void setupFrameParameters() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(CodeRunner.WIDTH, CodeRunner.HEIGHT);
		this.setResizable(false);
		setLocationRelativeTo(null);
		setJMenuBar(buildMenuBar());
		setLayout(new BorderLayout());
		setVisible(true);
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
		menuItem.setAccelerator(KeyStroke.getKeyStroke((char) CodeRunner.KEY_START));

		createMenuItem(gameMenu, i18n.t("Quit"))
				.addActionListener(e -> {
					System.exit(0);
				});

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
		game = new RunnerPanel(this, i18n, keyConfig);
		game.setSize(CodeRunner.WIDTH, CodeRunner.HEIGHT);
		add(game, BorderLayout.CENTER);
	}
}
