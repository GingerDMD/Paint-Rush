package com.paint.rush;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paint.rush.Screens.PlayScreen;

public class PaintRush extends Game {
	public SpriteBatch batch;
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

    public static final short GROUND_BIT = 1;
    public static final short TOAST_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("8bittoastyguy.wav", Music.class);
		manager.load("music.mp3", Music.class);
		manager.load("royaltyfreebutterland.mp3", Music.class);
		manager.load("You_Groove_You_Lose.mp3", Music.class);
		manager.load("8bittachankaslightimprove.mp3", Music.class);
		manager.load("oopsiepoopsie.mp3", Music.class);
        manager.load("Butter_Time_deep.wav", Sound.class);
        manager.load("Jam_Up_deep.wav", Sound.class);
        manager.load("Nutty_deep.wav", Sound.class);
		manager.finishLoading();
		setScreen(new PlayScreen(this, "One.tmx", "8bittachankaslightimprove.mp3"));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
