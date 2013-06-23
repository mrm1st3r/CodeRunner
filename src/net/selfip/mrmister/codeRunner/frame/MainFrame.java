package net.selfip.mrmister.codeRunner.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	static final long serialVersionUID = 0x1;
	
	private RunnerPanel game;
	
	/**
	 * Initialize the main window.
	 */
	public MainFrame() {
		super(CodeRunner.getWindowTitle(true));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// fixed window-size
		setSize(WIDTH, HEIGHT);
		this.setResizable(false);

		setLocationRelativeTo(null);
		buildMenu();

		setLayout(new BorderLayout());
		buildGameComponent();

		setVisible(true);
	}

	private void buildMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Spiel");
		mb.add(menu);
		JMenuItem mi = new JMenuItem("Neu");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.start();
			}
		});
		mi.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER));
		menu.add(mi);

		mi = new JMenuItem("Beenden");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(mi);

		menu = new JMenu("Hilfe");
		mi = new JMenuItem("Über");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new AboutFrame(MainFrame.this);
			}
		});
		menu.add(mi);

		mb.add(menu);
		setJMenuBar(mb);
	}

	private void buildGameComponent() {
		game = new RunnerPanel(this);
		game.setSize(MainFrame.HEIGHT, MainFrame.WIDTH);
		
		add(game, BorderLayout.CENTER);
	}
}
