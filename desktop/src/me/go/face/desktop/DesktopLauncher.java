package me.go.face.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.go.face.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
			config.fullscreen = true;
			config.width = 1920;
			config.height = 1080;
			config.title = "MeGoFace";


		config.resizable = true;
		config.samples = 4;
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(), config);
	}
}
