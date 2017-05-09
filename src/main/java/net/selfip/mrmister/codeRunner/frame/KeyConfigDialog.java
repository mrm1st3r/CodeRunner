package net.selfip.mrmister.codeRunner.frame;

import net.selfip.mrmister.codeRunner.event.KeyConfig;
import net.selfip.mrmister.codeRunner.lang.I18n;

import javax.swing.*;

/**
 * view and modify the current KeyConfig.
 *
 */
public class KeyConfigDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private KeyConfig conf;
	private final I18n i18n;
	private JTable table;

	/**
	 * @param f parent frame
	 * @param i18n
	 */
	public KeyConfigDialog(MainFrame f, I18n i18n) {
		super(f, "Key configuration");

		conf = f.getRunnerPanel().getKeyConfig();
		this.i18n = i18n;
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
		
		table = new JTable(conf.list(), headings);

		add(new JScrollPane(table));
	}
}
