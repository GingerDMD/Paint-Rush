package paw.gator.glide.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by preston on 2/4/16.
 */
public class Bird {
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private int movement;
    private Rectangle bounds;
    private paw.gator.glide.sprites.Animation birdAnimation;
    private Texture texture;
    private Sound flap;

    public boolean canJump = true;

    public Bird(int x, int y)
    {
        movement = 100;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("gator_small_hat.png");
        birdAnimation = new paw.gator.glide.sprites.Animation(new TextureRegion(texture), 3, 0.2f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("woosh.mp3"));
    }

    public void update(float dt)
    {
        birdAnimation.update(dt);
        if (position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(movement * dt, velocity.y, 0);
        velocity.scl(1/dt);
        if (position.y < 0)
        {
            position.y = 0;
        }
        bounds.setPosition(position.x, position.y);
    }


    public void jump()
    {
        if (canJump) {
            velocity.y = 250;
            flap.play(0.3f);
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setMovement(int movement)
    {
        this.movement = movement;
    }

    public int getMovement()
    {
        return movement;
    }

    public void dispose()
    {
        texture.dispose();
        flap.dispose();
    }
}
