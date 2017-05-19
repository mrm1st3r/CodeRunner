package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.util.ApplicationInfo;
import net.selfip.mrmister.coderunner.lang.I18n;

import javax.swing.*;
import java.awt.*;

/**
 * Basic about-dialog.
 */
class AboutDialog extends JDialog {

	private static final int MARGIN = 20;
	private static final int FONT_SIZE = 12;

	private final ApplicationInfo applicationInfo;
	private final I18n i18n;

	AboutDialog(JFrame f, ApplicationInfo applicationInfo, I18n i18n) {
		super(f, "About " + applicationInfo.getName(), true);
		this.applicationInfo = applicationInfo;
		this.i18n = i18n;

		JPanel p = new JPanel(new GridLayout(2, 1));
		p.add(createHeading());
		p.add(createTextArea());
		add(p);

		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	private JTextArea createTextArea() {
		JTextArea text = new JTextArea(formatText());
		text.setMargin(new Insets(0, MARGIN, MARGIN, MARGIN));
		return text;
	}

	private String formatText() {
		return String.format("%s \u00A9 %s %s \n\n %s",
				i18n.t("Code"), ApplicationInfo.YEAR, ApplicationInfo.CODE_AUTHOR,
				i18n.t("java_trademark"));
	}

	private JTextArea createHeading() {
		JTextArea heading = new JTextArea();
		heading.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		heading.setText(applicationInfo.getSignature());
		heading.setMargin(new Insets(MARGIN, MARGIN, 0, MARGIN));
		return heading;
	}
}
