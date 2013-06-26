package net.selfip.mrmister.codeRunner.frame;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.selfip.mrmister.codeRunner.CodeRunner;

/**
 * Basic about-dialog.
 * @author mrm1st3r
 *
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 0x2;
	private static final int MARGIN = 20;
	private static final int FONT_SIZE = 12;

	/**
	 * @param f owner
	 */
	public AboutDialog(JFrame f) {
		super(f, "About " + CodeRunner.getWindowTitle(false), true);

		JTextArea heading = new JTextArea();
		heading.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		heading.setText(CodeRunner.getWindowTitle(true));
		heading.setMargin(new Insets(MARGIN, MARGIN, 0, MARGIN));

		JTextArea text = new JTextArea("Code \u00A9 " + CodeRunner.YEAR
				+ " " + CodeRunner.CODE_AUTHOR
				+ "\nGraphics \u00A9 " + CodeRunner.YEAR
				+ " " + CodeRunner.GRAPHICS_AUTHOR + "\n\n"
				+ "The Java-logo is a trademark of Oracle Corporation");
		text.setMargin(new Insets(0, MARGIN, MARGIN, MARGIN));

		JPanel p = new JPanel(new GridLayout(2, 1));

		p.add(heading);
		p.add(text);

		add(p);

		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}
}