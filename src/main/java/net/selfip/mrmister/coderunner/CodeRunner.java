package net.selfip.mrmister.coderunner;

import net.selfip.mrmister.coderunner.event.KeyConfig;
import net.selfip.mrmister.coderunner.frame.MainFrame;
import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;
import net.selfip.mrmister.coderunner.util.ApplicationInfo;

/**
 * Launcher for CodeRunner.
 */
public final class CodeRunner {

	public static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final String LANG = "de";
	private static final String KEY_CONFIGURATION_FILE = "keyboard.ini";

	public static void main(String[] args) {
		new CodeRunner();
	}

	private CodeRunner() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		I18n i18n = new I18n(LANG);
		KeyConfig keyConfig = new KeyConfig(KEY_CONFIGURATION_FILE, applicationInfo.getSignature());
		Bounds gameBounds = new Bounds(WIDTH, HEIGHT);
		new MainFrame(
				applicationInfo,
				i18n,
				keyConfig,
                gameBounds,
                new GameLoop(gameBounds, i18n, keyConfig));
	}
}
