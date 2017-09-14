package paw.gator.glide.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = paw.gator.glide.Flappy.WIDTH;
		config.height = paw.gator.glide.Flappy.HEIGHT;
		config.title = paw.gator.glide.Flappy.TITAL;
		new LwjglApplication(new paw.gator.glide.Flappy(), config);
	}
}
