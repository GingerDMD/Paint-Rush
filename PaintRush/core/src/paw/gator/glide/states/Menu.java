package paw.gator.glide.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import paw.gator.glide.Flappy;
import paw.gator.glide.sprites.Bird;

/**
 * Created by preston on 2/2/16.
 */
public class Menu extends State {

    public static boolean playMusic = true;
    private Texture background;
    private Texture playButton;
    private Texture muteButton;
    private Texture muteButtonOff;
    private Music mus;
    private Bird b;
    public Menu(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        cam.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);
        b = new Bird(-30, (Flappy.HEIGHT / 2) - 200);
        mus = Gdx.audio.newMusic(Gdx.files.internal("title.mp3"));
        mus.setVolume(0.4f);
        mus.setLooping(true);
        mus.play();
        background = new Texture("jung_men.png");
        playButton = new Texture("play_button.png");
        muteButton = new Texture("music.png");
        muteButtonOff = new Texture("musicoff.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched())
        {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            Rectangle textureBoundsPlay = new Rectangle(cam.position.x - playButton.getWidth() / 2,
                    cam.position.y, playButton.getWidth(), playButton.getHeight());

            Rectangle textureBoundsMute = new Rectangle(cam.position.x - muteButtonOff.getWidth() / 2,
                    (cam.viewportHeight / 2) - cam.viewportHeight / 4,
                    muteButtonOff.getWidth(), muteButtonOff.getHeight());

            Vector3 touchArea = new Vector3(x, y, 0);

            cam.unproject(touchArea);

            if (textureBoundsPlay.contains(touchArea.x, touchArea.y))
            {
                mus.stop();
                mus.dispose();
                gsm.set(new paw.gator.glide.states.PlayState(gsm, playMusic));
            }
            else if (textureBoundsMute.contains(touchArea.x, touchArea.y))
            {
                playMusic = !playMusic;
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        b.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playButton, cam.position.x - playButton.getWidth() / 2, cam.position.y);
        sb.draw(b.getBird(), b.getPosition().x, cam.position.y - cam.position.y / 2 + 50);
        if (playMusic)
        {
            mus.play();
            sb.draw(muteButton, cam.position.x - muteButton.getWidth() / 2, (cam.viewportHeight / 2) - cam.viewportHeight / 4);
        }
        if (!playMusic)
        {
            mus.stop();
            sb.draw(muteButtonOff, cam.position.x - muteButtonOff.getWidth() / 2, (cam.viewportHeight / 2) - cam.viewportHeight / 4);
        }
        if (Gdx.input.isButtonPressed(Input.Keys.BACK))
        {
            //System.out.println("Exiting");
            //Gdx.app.exit();
        }
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        muteButton.dispose();
        muteButtonOff.dispose();
        b.dispose();
    }

    public void resetCam()
    {
        cam.position.x = 0;
    }
}
