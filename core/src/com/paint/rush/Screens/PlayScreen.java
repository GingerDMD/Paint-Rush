package com.paint.rush.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paint.rush.PaintRush;
import com.paint.rush.Scenes.Hud;
import com.paint.rush.Sprites.Brush;
import com.paint.rush.Sprites.Butter;
import com.paint.rush.Sprites.Enemy;
import com.paint.rush.Tools.B2WorldCreator;
import com.paint.rush.Tools.WorldContactListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paint.rush.Screens.PlayScreen;

import org.w3c.dom.css.Rect;

import java.awt.Paint;

/**
 * Created by preston on 11/23/17.
 */

public class PlayScreen implements Screen{

    private Brush player;

    private PaintRush game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;

    private Music music;
    private Sound butterTime;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private String currMap;


    public PlayScreen(PaintRush game, String mapString, String musicName) {
        atlas = new TextureAtlas("Toastanim.atlas");
        currMap = mapString;
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(PaintRush.V_WIDTH / PaintRush.PPM, PaintRush.V_HEIGHT / PaintRush.PPM, gamecam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapString);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PaintRush.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        if (mapString.equals("One.tmx")) {
            butterTime = PaintRush.manager.get("Butter_Time_deep.wav", Sound.class);
            butterTime.play();
        }
        else if (mapString.equals("Two.tmx")) {
            butterTime = PaintRush.manager.get("Jam_Up_deep.wav", Sound.class);
            butterTime.play();
        }
        else if (mapString.equals("PB_Map.tmx")) {
            butterTime = PaintRush.manager.get("Nutty_deep.wav", Sound.class);
            butterTime.play();
        }
        music = PaintRush.manager.get(musicName, Music.class);
        music.setVolume(0.20f);
        music.setLooping(true);


        world = new World(new Vector2(0, 0), true); //gravity is second arg for Vector2
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this, mapString);

        player = new Brush(this);
        world.setContactListener(new WorldContactListener());
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getButters()) {
            enemy.update(dt);
        }
        for (Enemy enemy : creator.getMoreEnemies()) {
            enemy.update(dt);
        }
        hud.update(dt);
        if (player.b2body.getPosition().x >= 600 / PaintRush.PPM) {
            music.play();
        }
        if (player.b2body.getPosition().x >= 2500 / PaintRush.PPM) {
            music.setVolume(0.20f);
        }
        if (player.b2body.getPosition().x >= 2600 / PaintRush.PPM) {
            music.setVolume(0.19f);
        }
        if (player.b2body.getPosition().x >= 2700 / PaintRush.PPM) {
            music.setVolume(0.17f);
        }
        if (player.b2body.getPosition().x >= 2800 / PaintRush.PPM) {
            music.setVolume(0.15f);
        }
        if (player.b2body.getPosition().x >= 2900 / PaintRush.PPM) {
            music.setVolume(0.12f);
        }
        if (player.b2body.getPosition().x >= 3000 / PaintRush.PPM) {
            music.setVolume(0.08f);
        }
        if (player.b2body.getPosition().x >= 3100 / PaintRush.PPM) {
            music.setVolume(0.04f);
        }
        if (player.b2body.getPosition().x >= 3200 / PaintRush.PPM) {
            music.setVolume(0.02f);
        }
        if (player.b2body.getPosition().x >= 3300 / PaintRush.PPM) {
            music.setVolume(0.0f);
        }

        if (player.b2body.getPosition().x >= 3600 / PaintRush.PPM) {
            music.dispose();
            game.dispose();
            if (currMap.equals("One.tmx")) {
                game.setScreen(new PlayScreen(game, "Two.tmx", "oopsiepoopsie.mp3"));
            }
            else if (currMap.equals("Two.tmx")) {
                game.setScreen(new PlayScreen(game, "PB_Map.tmx", "8bittoastyguy.wav"));
            }
            else {
                game.setScreen(new PlayScreen(game, "One.tmx", "8bittachankaslightimprove.mp3"));
            }

        }
        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    public void handleInput(float dt) {
        player.b2body.setLinearVelocity(0.75f, player.b2body.getLinearVelocity().y);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0.9f);
            //player.b2body.applyLinearImpulse(new Vector2(0, 1f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, -0.9f);
            // player.b2body.applyLinearImpulse(new Vector2(0, -1f), player.b2body.getWorldCenter(), true);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, -0.0f);
            // player.b2body.applyLinearImpulse(new Vector2(0, -1f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.b2body.setLinearVelocity(1.5f, player.b2body.getLinearVelocity().y);
            player.setBrushFrame(0.03f);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.b2body.setLinearVelocity(1f, player.b2body.getLinearVelocity().y);
            player.setBrushFrame(0.10f);
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getButters()) {
            enemy.draw(game.batch);
        }
        for (Enemy enemy : creator.getMoreEnemies()) {
            enemy.draw(game.batch);
        }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
