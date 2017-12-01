package com.paint.rush.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.paint.rush.PaintRush;

/**
 * Created by preston on 12/1/17.
 */

public class Brush extends Sprite {
    public World world;
    public Body b2body;

    public Brush(World w) {
        this.world = w;
        defineBrush();
    }

    public void defineBrush() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PaintRush.PPM, 32 / PaintRush.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PaintRush.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
