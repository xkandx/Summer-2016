package edu.ucf.cs.wk2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.ucf.cs.wk2.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Robot vs. Spider";
		config.width = 800;
		config.height = 700;
		new LwjglApplication(new GameClass(false), config);
	}
}
