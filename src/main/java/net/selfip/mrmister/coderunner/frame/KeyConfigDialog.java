package net.selfip.mrmister.coderunner.frame;

import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.lang.I18n;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

/**
 * Dialog for viewing and modifying the current KeyConfig.
 */
class KeyConfigDialog extends JDialog {

	private final I18n i18n;
	private final KeyConfig keyConfig;

	KeyConfigDialog(MainFrame f, I18n i18n, KeyConfig keyConfig) {
		super(f, "Key configuration");

		this.i18n = i18n;
		this.keyConfig = keyConfig;
		buildKeyTable();

		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	private void buildKeyTable() {
		String[] headings = {
			i18n.t("function"),
			i18n.t("current_key"),
			i18n.t("default_key")
		};

		JTable table = new JTable(buildTable(), headings);

		add(new JScrollPane(table));
	}

	private String[][] buildTable() {
		Enumeration<Object> keys = keyConfig.keys();
		String[][] rows = new String[keyConfig.keyCount()][3];

		for (int i = 0; keys.hasMoreElements(); i++) {
			String key = (String) keys.nextElement();
			rows[i][0] = i18n.t(key);
			rows[i][1] = KeyEvent.getKeyText(keyConfig.get(key));
			rows[i][2] = KeyEvent.getKeyText(keyConfig.getDefaultValue(key));
		}

		return rows;
	}
}
