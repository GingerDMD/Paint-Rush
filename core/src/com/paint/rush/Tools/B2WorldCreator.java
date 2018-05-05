package com.paint.rush.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.paint.rush.PaintRush;
import com.paint.rush.Screens.PlayScreen;
import com.paint.rush.Sprites.BigBlob;
import com.paint.rush.Sprites.Blob;
import com.paint.rush.Sprites.Brick;
import com.paint.rush.Sprites.Butter;
import com.paint.rush.Sprites.Coin;
import com.paint.rush.Sprites.Droplet;
import com.paint.rush.Sprites.Enemy;
import com.paint.rush.Sprites.PBGuy;

/**
 * Created by preston on 12/1/17.
 */

public class B2WorldCreator {
    private Array<Enemy> enemies;
    private Array<Enemy> enemiesTwo;

    public B2WorldCreator(PlayScreen screen, String mapName) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //for ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PaintRush.PPM, (rect.getY() + rect.getHeight() / 2) / PaintRush.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2)  / PaintRush.PPM, (rect.getHeight() / 2) / PaintRush.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //for pipes
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PaintRush.PPM, (rect.getY() + rect.getHeight() / 2) / PaintRush.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PaintRush.PPM, rect.getHeight() / 2 / PaintRush.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = PaintRush.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //for bricks
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rect);
        }

        //for coins
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
        }

        //create all enemies
        enemies = new Array<Enemy>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if (mapName.equals("One.tmx")) {
                enemies.add(new Butter(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
            else if (mapName.equals("Two.tmx")) {
                enemies.add(new Blob(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
            else if (mapName.equals("PB_Map.tmx")) {
                enemies.add(new PBGuy(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
        }

        enemiesTwo = new Array<Enemy>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if (mapName.equals("One.tmx")) {
                enemies.add(new Butter(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
            else if (mapName.equals("Two.tmx")) {
                enemies.add(new BigBlob(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
            else if (mapName.equals("PB_Map.tmx")) {
                enemies.add(new Droplet(screen, rect.getX() / PaintRush.PPM, rect.getY() / PaintRush.PPM));
            }
        }



    }

    public Array<Enemy> getButters(){
        return enemies;
    }

    public Array<Enemy> getMoreEnemies() {
        return  enemiesTwo;
    }
}
