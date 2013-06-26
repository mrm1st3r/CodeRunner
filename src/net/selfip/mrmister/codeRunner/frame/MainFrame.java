package net.selfip.mrmister.codeRunner.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.selfip.mrmister.codeRunner.CodeRunner;

/**
 * the main frame.
 * @author mrm1st3r
 *
 */
public class MainFrame extends JFrame {

	static final long serialVersionUID = 0x1;

	private RunnerPanel game;

	/**
	 * Initialize the main window.
	 */
	public MainFrame() {
		super(CodeRunner.getWindowTitle(true));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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
		JMenu menu = new JMenu("Game");
		mb.add(menu);
		JMenuItem mi = new JMenuItem("New");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.isStarted()) {
					game.stop("");
				}
				game.start();
			}
		});
		mi.setAccelerator(KeyStroke.getKeyStroke((char) CodeRunner.KEY_START));
		menu.add(mi);

		mi = new JMenuItem("Quit");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.saveKeyConfig();
				System.exit(0);
			}
		});
		menu.add(mi);

		menu = new JMenu("Help");
		mb.add(menu);

		mi = new JMenuItem("About");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new AboutDialog(MainFrame.this);
			}
		});
		menu.add(mi);

		mi = new JMenuItem("Key configuration");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new KeyConfigDialog(MainFrame.this);
			}
		});
		menu.add(mi);

		setJMenuBar(mb);
	}

	private void buildGameComponent() {
		game = new RunnerPanel(this);
		game.setSize(CodeRunner.HEIGHT, CodeRunner.WIDTH);

		add(game, BorderLayout.CENTER);
	}

	/**
	 * @return the game panel
	 */
	public RunnerPanel getRunnerPanel() {
		return game;
	}
}
