package net.selfip.mrmister.codeRunner.frame;

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
 * 
 * @author mrm1st3r
 *
 */
public class MainFrame extends JFrame {

	static final long serialVersionUID = 0x1;
	
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	/**
	 * Initialize the main window.
	 */
	public MainFrame() {
		super(CodeRunner.getWindowTitle(true));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// fixed window-size
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setResizable(false);
		
		// window opens in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int top = (screenSize.height - DEFAULT_HEIGHT) / 2;
        int left = (screenSize.width - DEFAULT_WIDTH) / 2;
		setLocation(left, top);
        

		//pack();
		
		buildMenu();
		
		setVisible(true);
	}
	
	private void buildMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Spiel");
		mb.add(menu);
		JMenuItem mi = new JMenuItem("Neu");
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
}
