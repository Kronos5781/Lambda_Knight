package me.go.face.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.go.face.Main;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private short score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label protagonistLabel;

    public Hud(SpriteBatch sb)
    {
        worldTimer = 999;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        scoreLabel = new Label(String.format("%03d", score),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        timeLabel = new Label("TIME",new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        levelLabel = new Label("CURRENT WORLD: " + Main.MAP_SELECT,new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        worldLabel = new Label("WORLD",new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        protagonistLabel = new Label("Kills",new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));

        table.add(protagonistLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        score = Main.PROT_KILLS;
        scoreLabel.setText(String.format("%03d", score));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }



}