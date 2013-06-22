package net.selfip.mrmister.codeRunner.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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

		// window opens in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int top = (screenSize.height - HEIGHT) / 2;
        int left = (screenSize.width - WIDTH) / 2;
		setLocation(left, top);
		buildMenu();

		setLayout(new BorderLayout());
		buildGameComponent();

		setVisible(true);
		
		Thread th = new Thread(game);
		th.start();
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
		
		add(game, BorderLayout.CENTER);
	}
}
