package com.paint.rush.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.paint.rush.PaintRush;
import com.paint.rush.Screens.PlayScreen;

/**
 * Created by preston on 3/14/18.
 */

public class Butter extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Butter(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("BUTTERDEFEATu"), 0, 0, 48, 64));
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 32 / PaintRush.PPM, 64 / PaintRush.PPM);
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PaintRush.PPM + 15f, 32 / PaintRush.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PaintRush.PPM);  // circle hit-box size
        fdef.filter.categoryBits = PaintRush.ENEMY_BIT;
        fdef.filter.maskBits = PaintRush.GROUND_BIT |
                PaintRush.COIN_BIT |
                PaintRush.BRICK_BIT |
                PaintRush.ENEMY_BIT |
                PaintRush.OBJECT_BIT |
                PaintRush.TOAST_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
