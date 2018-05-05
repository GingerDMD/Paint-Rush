package com.paint.rush.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.paint.rush.PaintRush;
import com.paint.rush.Screens.PlayScreen;

import java.util.Random;
import java.util.Timer;

/**
 * Created by preston on 4/27/18.
 */

public class Blob extends Enemy {

    private float stateTime;
    private float revTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private int timeCount;
    private int status;
    private Vector2 blobVelocity;
    Random r;

    public Blob(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        r = new Random();
        status = r.nextInt(2);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pinkk"), 0, 0, 16, 16));
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        revTime = 0;
        setBounds(getX(), getY(), 16 / PaintRush.PPM, 16 / PaintRush.PPM);
        timeCount = 0;
        if (status == 1) {
            blobVelocity = new Vector2(3.0f, 0);
        }
        else if (status == 0) {
            blobVelocity = new Vector2(0, 3.0f);
        }
    }

    public void update(float dt) {
        stateTime += dt;
        revTime += dt;
        if (revTime >= 0.25f) {
            b2body.setLinearVelocity(0, 0);
        }
        else {
            b2body.setLinearVelocity(blobVelocity);
        }
        if (revTime >= 1f) {
            if (status == 1) {
                reverseBlobVelocity(true, false);
            }
            else if (status == 0) {
                reverseBlobVelocity(false, true);
            }
            revTime--;
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / PaintRush.PPM, 8 / PaintRush.PPM);  // hit-box size
//        CircleShape shape = new CircleShape();
//        shape.setRadius(7 / PaintRush.PPM);
        fdef.filter.categoryBits = PaintRush.ENEMY_BIT;
        fdef.filter.maskBits = PaintRush.GROUND_BIT |
                PaintRush.COIN_BIT |
                PaintRush.BRICK_BIT |
                PaintRush.ENEMY_BIT |
                PaintRush.OBJECT_BIT |
                PaintRush.TOAST_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void reverseBlobVelocity(boolean x, boolean y) {
        if (x) {
            blobVelocity.x = -blobVelocity.x;
        }
        if (y) {
            blobVelocity.y = -blobVelocity.y;
        }
    }
}
