package com.paint.rush.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paint.rush.PaintRush;

import java.awt.Paint;

import javax.swing.text.View;

/**
 * Created by preston on 11/24/17.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label brushLabel;

    public Hud(SpriteBatch sb) {
        worldTimer = 0;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(PaintRush.V_WIDTH, PaintRush.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        brushLabel = new Label("BRUSH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("LEVEL 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(String.format("%02d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        countdownLabel.setFontScale(1f, 1f);
        //table.add(worldLabel).expandX().padTop(10);
        table.add(countdownLabel).expandX().padTop(5);
        //table.row();
        //table.add(scoreLabel).expandX();
        //table.add(levelLabel).expandX();
        //table.add(countdownLabel).expandX();

        //stage.addActor(table);

    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            countdownLabel.setText(String.format("%02d", worldTimer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
