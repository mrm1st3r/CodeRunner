package net.selfip.mrmister.codeRunner.frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.selfip.mrmister.codeRunner.event.KeyConfig;

/**
 * view and modify the current KeyConfig.
 * @author mrm1st3r
 *
 */
public class KeyConfigDialog extends JDialog {

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
			"Function",
			"Current Key",
			"Default Key"
		};
		
		table = new JTable(conf.list(), headings);

		add(new JScrollPane(table));
	}
}
