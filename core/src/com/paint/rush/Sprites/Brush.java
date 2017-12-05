package com.paint.rush.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.paint.rush.PaintRush;
import com.paint.rush.Screens.PlayScreen;

/**
 * Created by preston on 12/1/17.
 */

public class Brush extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion toastStand;

    public Brush(World w, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Toast1"));
        this.world = w;
        defineBrush();
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
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
