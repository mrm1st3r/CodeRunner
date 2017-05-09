package net.selfip.mrmister.codeRunner.frame;

import net.selfip.mrmister.codeRunner.ApplicationInfo;
import net.selfip.mrmister.codeRunner.lang.I18n;
import net.selfip.mrmister.codeRunner.lang.Translatable;

import javax.swing.*;
import java.awt.*;

/**
 * Basic about-dialog.
 *
 */
public class AboutDialog extends JDialog implements Translatable {

	private static final long serialVersionUID = 0x2;
	private static final int MARGIN = 20;
	private static final int FONT_SIZE = 12;

	/**
	 * @param f owner
	 * @param applicationInfo
	 */
	public AboutDialog(JFrame f, ApplicationInfo applicationInfo) {
		super(f, "About " + applicationInfo.getName(), true);

		JTextArea heading = new JTextArea();
		heading.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		heading.setText(applicationInfo.getSignature());
		heading.setMargin(new Insets(MARGIN, MARGIN, 0, MARGIN));

		JTextArea text = new JTextArea(t("Code") + " \u00A9 " + ApplicationInfo.YEAR
				+ " " + ApplicationInfo.CODE_AUTHOR
				+ "\n" + t("Graphics") + " \u00A9 " + ApplicationInfo.YEAR
				+ " " + ApplicationInfo.GRAPHICS_AUTHOR + "\n\n"
				+ t("java_trademark"));
		text.setMargin(new Insets(0, MARGIN, MARGIN, MARGIN));

		JPanel p = new JPanel(new GridLayout(2, 1));

		p.add(heading);
		p.add(text);

		add(p);

		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	@Override
	public String t(String t) {
		return I18n.getTranslationFor(t);
	}
}
