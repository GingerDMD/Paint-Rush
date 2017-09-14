package paw.gator.glide.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by preston on 2/4/16.
 */
public class Tube {
    public static final int TUBE_WIDTH = 25;

    private static final int LOWEST_OPENING = 120;
    private static final int TUBE_GAP = 100;
    private static final int FLUCTUATION = 130;
    private Texture topTube;
    private Texture bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Random rand;
    private Rectangle boundsTop, boundsBot;

    public Tube(float x)
    {
        topTube = new Texture("pike_top.png");
        bottomTube = new Texture("pike.png");
        rand = new Random();

        posTopTube = new Vector2(x + 180, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x + 180, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x + 5, posTopTube.y, topTube.getWidth() - 5, topTube.getHeight());
        boundsBot = new Rectangle(posBottomTube.x + 5, posBottomTube.y, bottomTube.getWidth() - 5, bottomTube.getHeight());
    }

    public void reposition(float x)
    {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void dispose()
    {
        topTube.dispose();
        bottomTube.dispose();
    }
}
