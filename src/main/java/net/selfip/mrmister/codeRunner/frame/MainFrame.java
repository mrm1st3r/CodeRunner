package net.selfip.mrmister.codeRunner.frame;

import net.selfip.mrmister.codeRunner.ApplicationInfo;
import net.selfip.mrmister.codeRunner.CodeRunner;
import net.selfip.mrmister.codeRunner.lang.I18n;

import javax.swing.*;
import java.awt.*;

/**
 * the main frame.
 *
 */
public class MainFrame extends JFrame {

	static final long serialVersionUID = 0x1;

	private RunnerPanel game;
	private ApplicationInfo applicationInfo;
	private final I18n i18n;

	/**
	 * Initialize the main window.
	 * @param applicationInfo
	 * @param i18n
	 */
	public MainFrame(ApplicationInfo applicationInfo, I18n i18n) {
		super(applicationInfo.getSignature());
		this.applicationInfo = applicationInfo;
		this.i18n = i18n;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// fixed window-size
		setSize(CodeRunner.WIDTH, CodeRunner.HEIGHT);
		this.setResizable(false);

		setLocationRelativeTo(null);
		buildMenu();

		setLayout(new BorderLayout());
		buildGameComponent();

		setVisible(true);
	}

	private void buildMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu(i18n.t("Game"));
		mb.add(menu);
		JMenuItem mi = new JMenuItem(i18n.t("New"));
		mi.addActionListener(e -> {
            if (game.isStarted()) {
                game.stop("");
            }
            game.start();
        });
		mi.setAccelerator(KeyStroke.getKeyStroke((char) CodeRunner.KEY_START));
		menu.add(mi);

		mi = new JMenuItem(i18n.t("Quit"));
		mi.addActionListener(e -> {
            game.saveKeyConfig();
            System.exit(0);
        });
		menu.add(mi);

		menu = new JMenu(i18n.t("Help"));
		mb.add(menu);

		mi = new JMenuItem(i18n.t("About"));
		mi.addActionListener(a -> new AboutDialog(MainFrame.this, applicationInfo, i18n));
		menu.add(mi);

		mi = new JMenuItem(i18n.t("Key configuration"));
		mi.addActionListener(a -> new KeyConfigDialog(MainFrame.this, i18n));
		menu.add(mi);

		setJMenuBar(mb);
	}

	private void buildGameComponent() {
		game = new RunnerPanel(this, applicationInfo, i18n);
		game.setSize(CodeRunner.WIDTH, CodeRunner.HEIGHT);

		add(game, BorderLayout.CENTER);
	}

	/**
	 * @return the game panel
	 */
	public RunnerPanel getRunnerPanel() {
		return game;
	}
}
