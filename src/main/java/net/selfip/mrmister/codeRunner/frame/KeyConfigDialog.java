package net.selfip.mrmister.codeRunner.frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.selfip.mrmister.codeRunner.event.KeyConfig;
import net.selfip.mrmister.codeRunner.lang.I18n;
import net.selfip.mrmister.codeRunner.lang.Translatable;

/**
 * view and modify the current KeyConfig.
 *
 */
public class KeyConfigDialog extends JDialog implements Translatable {

	private static final long serialVersionUID = 1L;

	private KeyConfig conf;
	private JTable table;

	/**
	 * @param f parent frame
	 */
	public KeyConfigDialog(MainFrame f) {
		super(f, "Key configuration");

		conf = f.getRunnerPanel().getKeyConfig();
		buildKeyTable();

		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	private void buildKeyTable() {
		String[] headings = {
			t("function"),
			t("current_key"),
			t("default_key")
		};
		
		table = new JTable(conf.list(), headings);

		add(new JScrollPane(table));
	}

	@Override
	public String t(String t) {
		return I18n.getTranslationFor(t);
	}
}
