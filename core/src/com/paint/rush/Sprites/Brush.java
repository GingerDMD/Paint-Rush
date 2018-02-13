package com.paint.rush.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.paint.rush.PaintRush;
import com.paint.rush.Screens.PlayScreen;

/**
 * Created by preston on 12/1/17.
 */

public class Brush extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State prevState;
    public World world;
    public Body b2body;
    private TextureRegion toastStand;
    private Animation<TextureRegion> brushRun;
    private Animation<TextureRegion> brushJump;
    private boolean runningRight;
    private float stateTimer;

    public Brush(World w, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Toast1"));
        this.world = w;
        defineBrush();

        runningRight = true;

        currentState = State.STANDING;
        prevState = State.STANDING;
        stateTimer = 0;



        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        }
        brushRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        }
        brushJump = new Animation<TextureRegion>(0.1f, frames);


        toastStand = new TextureRegion(getTexture(), 0, 0, 64, 64);
        setBounds(0, 0, 25 / PaintRush.PPM, 25 / PaintRush.PPM);
        setRegion(toastStand);


    }

    public void defineBrush() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PaintRush.PPM + 3f, 32 / PaintRush.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / PaintRush.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PaintRush.PPM, 8 / PaintRush.PPM),
                new Vector2(2 / PaintRush.PPM, 8 / PaintRush.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");

    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        }
        else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }
        else {
            return State.STANDING;
        }
    }

    public TextureRegion getFrame(float dt) {

        TextureRegion region;
        region = toastStand;
        /*
        currentState = getState();


        switch (currentState) {
            case JUMPING:
                region = brushJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = brushRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = toastStand;
        }
        */

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        //stateTimer = currentState == prevState ? stateTimer + dt : 0;
        //prevState = currentState;
        return region;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
}
