package net.selfip.mrmister.codeRunner.frame;

import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import net.selfip.mrmister.codeRunner.CodeRunner;

/**
 * Basic about-dialog.
 * @author mrm1st3r
 *
 */
public class AboutFrame extends JDialog {

	static final long serialVersionUID = 0x2;
	private static final int MARGIN = 20;
	
	/**
	 * @param f owner
	 */
	public AboutFrame(JFrame f) {
		super(f, "Über " + CodeRunner.getWindowTitle(false), true);

		JTextArea ta = new JTextArea(CodeRunner.getWindowTitle(true) + "\n"
				+ "\u00A9 " + CodeRunner.YEAR + " " + CodeRunner.AUTHOR);
		ta.setMargin(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));

		add(ta);

		pack();

		setLocationRelativeTo(getParent());

		setVisible(true);
	}
}
