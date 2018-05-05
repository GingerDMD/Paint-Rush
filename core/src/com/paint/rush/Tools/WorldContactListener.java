package com.paint.rush.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.paint.rush.PaintRush;
import com.paint.rush.Sprites.Enemy;
import com.paint.rush.Sprites.InteractiveTileObject;

/**
 * Created by preston on 2/4/18.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef) {
            case PaintRush.ENEMY_BIT | PaintRush.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == PaintRush.ENEMY_BIT) {
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                }
                else {
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                }
            case PaintRush.TOAST_BIT | PaintRush.ENEMY_BIT:
                Gdx.app.log("DIE TOAST", "Die");
                break;
            case PaintRush.ENEMY_BIT | PaintRush.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
