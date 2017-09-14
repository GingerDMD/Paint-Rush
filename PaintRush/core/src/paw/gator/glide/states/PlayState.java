package paw.gator.glide.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Preston Wilson starting on 2/3/16.
 * Many many thanks to Brent Aureli's tutorials on Youtube.com.
 * Thanks to RutgerMuller on freesound.org for the smack sound effect, which I've edited.
 * Thanks to Riciery Leal for the VCR OSD MONO font
 * Thanks for qubodup on freesound.org for the woosh sound effect
 * And thanks for Kilobolts Zombie Bird tutorial
 */
public class PlayState extends State {
    private final int NGWIDTH = 45;
    private final int NGHEIGHT = 30;
    private GlyphLayout hs;
    private GlyphLayout s;
    private int score;
    private String ss;
    BitmapFont bmp;
    private paw.gator.glide.sprites.Bird bird;
    private Texture bg;
    private Texture back_button;
    private Texture muteButton;
    private Texture muteButtonOff;
    private static final int TUBE_SPACING = 200;
    private static final int TUBE_COUNT = 4;
    private static final int BIRD_AHEAD = 80;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    public int ground_y_offset = -50;
    private Sound smack;
    private Music mus;
    private boolean canPlay;
    private boolean playMusic;
    private Preferences prefs;


    private Array<paw.gator.glide.sprites.Tube> tubes;

    public PlayState(GameStateManager gsm, boolean playMusic) {
        super(gsm);
        prefs = Gdx.app.getPreferences("Name");
        if (!prefs.contains("highScore"))
        {
            prefs.putInteger("highScore", 0);
        }
        bmp = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        bmp.getData().setScale(0.15f, 0.15f);
        bmp.setUseIntegerPositions(false);
        //bmp.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        score = 0;
        ss = "Score: " + score;
        canPlay = true;
        smack = Gdx.audio.newSound(Gdx.files.internal("smack.mp3"));
        mus = Gdx.audio.newMusic(Gdx.files.internal("mus1.mp3"));
        this.playMusic = playMusic;
        Gdx.input.setCatchBackKey(true);
        if (playMusic)
        {
            mus.setVolume(0.4f);
            mus.setLooping(true);
            mus.play();
        }
        bird = new paw.gator.glide.sprites.Bird(50, 200);
        bg = new Texture("bg_jung.png");
        back_button = new Texture("play_button.png");
        muteButton = new Texture("music.png");
        muteButtonOff = new Texture("musicoff.png");
        ground = new Texture("swamp.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ground_y_offset);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), ground_y_offset);
        //groundPos3 = new Vector2((cam.position.x - cam.viewportWidth / 2) + (2 * ground.getWidth()), GROUND_Y_OFFSET);
        //groundPos4 = new Vector2((cam.position.x - cam.viewportWidth / 2) + (3 * ground.getWidth()), GROUND_Y_OFFSET);
        cam.setToOrtho(false, paw.gator.glide.Flappy.WIDTH / 2, paw.gator.glide.Flappy.HEIGHT / 2);
        tubes = new Array<paw.gator.glide.sprites.Tube>(100);
        hs = new GlyphLayout(bmp, "HIGH SCORE: " + prefs.getInteger("highScore"));
        s = new GlyphLayout(bmp, String.valueOf(score));
        for (int i = 0; i < TUBE_COUNT; i++)
        {
            tubes.add(new paw.gator.glide.sprites.Tube(i * (TUBE_SPACING + paw.gator.glide.sprites.Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            //Gdx.app.exit();
        }
        if (Gdx.input.justTouched())
        {
            if (!bird.canJump)
            {
                float x = Gdx.input.getX();
                float y = Gdx.input.getY();

                Rectangle textureBoundsPlay = new Rectangle(cam.position.x - (NGWIDTH / 2), cam.position.y, NGWIDTH, NGHEIGHT);
                Vector3 touchAreaPlay = new Vector3(x, y, 0);

                Rectangle textureBoundsMute = new Rectangle(cam.position.x -
                        (muteButton.getWidth() / 2), (cam.viewportHeight / 2) - cam.viewportHeight / 4, NGWIDTH / 2, NGHEIGHT / 2);

                cam.unproject(touchAreaPlay);
                if (textureBoundsMute.contains(touchAreaPlay.x, touchAreaPlay.y))
                {
                    playMusic = !playMusic;
                }
                else if (textureBoundsPlay.contains(touchAreaPlay.x, touchAreaPlay.y))
                {
                    //System.out.println("NEW GAME");
                    gsm.set(new PlayState(gsm, playMusic));
                }
            }
            else
                bird.jump();
        }

    }


    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        updateGameOver();
        //System.out.println(cam.position.x);
        bird.update(dt);
        //score = (int)(cam.position.x / 220);
        boolean canUp = true;
        /**
        if (Gdx.input.justTouched() && (Gdx.input.getX() > Flappy.WIDTH - (2 * back_button.getWidth()))
                && Gdx.input.getY() < (back_button.getHeight() * 2))
        {
            mus.dispose();
            bird.dispose();
            resetCam();
            gsm.set(new Menu(gsm));
        }
         */
        cam.position.x = bird.getPosition().x + BIRD_AHEAD;

        for (int i = 0; i < tubes.size; i++)
        {

            paw.gator.glide.sprites.Tube tube = tubes.get(i);
            if (bird.getPosition().x > tube.getPosBottomTube().x && canUp)
            {
                //score = (score + 1);
                canUp = false;
                //System.out.println(score);
            }
            if ((cam.position.x - (cam.viewportWidth/2)) > tube.getPosBottomTube().x + tube.getTopTube().getWidth())
            {
                if (bird.getMovement() < 251)
                {
                    bird.setMovement(bird.getMovement() + 3);
                    //System.out.println(bird.getMovement());
                }
                score++;
                tube.reposition(tube.getPosTopTube().x + ((paw.gator.glide.sprites.Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if (tube.collides(bird.getBounds()))
            {
                if (canPlay)
                {
                    smack.play();
                    canPlay = false;
                }
                mus.stop();
                bird.setMovement(0);
                mus.dispose();
                bird.canJump = false;


            }
        }
        /*
        if (!bird.canJump && Gdx.input.justTouched() && Gdx.input.getX() > ((Flappy.WIDTH / 2) - NGWIDTH)
                && Gdx.input.getX() < ((Flappy.WIDTH / 2) + NGWIDTH)
                )
        {
            gsm.set(new PlayState(gsm));
        }
        */
        canUp = true;
        if (bird.getPosition().y <= ground.getHeight() + ground_y_offset)
        {
            mus.stop();
            mus.dispose();
            bird.canJump = false;
            bird.setMovement(0);
            //gsm.set(new PlayState(gsm));
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        bmp.setColor(new Color(Color.WHITE));
        for (paw.gator.glide.sprites.Tube tube: tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        if (!bird.canJump)
        {
            if (score > prefs.getInteger("highScore"))
            {
                prefs.putInteger("highScore", score);
                prefs.flush();
                hs.setText(bmp, "HIGH SCORE" + prefs.getInteger("highScore"));
            }
            sb.draw(back_button, cam.position.x - (NGWIDTH / 2), cam.position.y, NGWIDTH, NGHEIGHT);
            if (playMusic) {
                sb.draw(muteButton, cam.position.x - (muteButton.getWidth() / 2), (cam.viewportHeight / 2) - cam.viewportHeight / 4, NGWIDTH / 2, NGHEIGHT / 2);
            }
            else if (!playMusic)
            {
                sb.draw(muteButtonOff, cam.position.x - (muteButton.getWidth() / 2), (cam.viewportHeight / 2) - cam.viewportHeight / 4, NGWIDTH / 2, NGHEIGHT / 2);
            }
            bmp.draw(sb, "HIGH SCORE: " + prefs.getInteger("highScore"), cam.position.x - (hs.width / 2), (hs.height * 4)
                    + (cam.viewportHeight / 2));
            //bmp.draw(sb, "GAME OVER", cam.position.x - bmp.getSpaceWidth(), (cam.viewportHeight / 2));
        }
        //sb.draw(ground, groundStart.x, groundStart.y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        //sb.draw(ground, groundPos3.x, groundPos3.y);
        //sb.draw(ground, groundPos4.x, groundPos4.y);
        //sb.draw(back_button, cam.position.x  + (cam.viewportWidth / 2) - (1 * back_button.getWidth()), cam.viewportHeight - back_button.getHeight());
        s.setText(bmp, String.valueOf(score));
        bmp.draw(sb, String.valueOf(score), cam.position.x - (s.width / 2), (cam.viewportHeight - bmp.getCapHeight()));
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        bmp.dispose();
        back_button.dispose();
        muteButton.dispose();
        muteButtonOff.dispose();
        for (paw.gator.glide.sprites.Tube tube: tubes)
        {
            tube.dispose();
        }

    }

    private void updateGround()
    {
        if (cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth())
        {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth())
        {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
        /**
        if (cam.position.x - cam.viewportWidth / 2 > groundPos3.x + ground.getWidth())
        {
            groundPos3.add(ground.getWidth() * 4, 0);
        }
        if (cam.position.x - cam.viewportWidth / 2 > groundPos4.x + ground.getWidth())
        {
            groundPos4.add(ground.getWidth() * 4, 0);
        }
         */
    }

    public void setHighScore(int score)
    {
        prefs.putInteger("highScore", score);
    }

    public int getHighScore()
    {
        return (prefs.getInteger("highScore"));
    }

    public void updateGameOver()
    {
    }

    public void resetCam()
    {
        cam.position.x = 0;
        cam.update();
    }

}
