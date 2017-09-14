package paw.gator.glide;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Flappy extends ApplicationAdapter {
	public static final int WIDTH = 440;
	public static final int HEIGHT = 800;

	public static final String TITAL = "Gator thing";
	private paw.gator.glide.states.GameStateManager gsm;
	private SpriteBatch batch;
	private Music music;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new paw.gator.glide.states.GameStateManager();
		//music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		//music.setLooping(true);
		//music.setVolume(0.1f);
		//music.play();
		img = new Texture("badlogic.jpg");
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new paw.gator.glide.states.Menu(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		music.dispose();
	}
}
