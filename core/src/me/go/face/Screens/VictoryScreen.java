package me.go.face.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.go.face.Main;

public class VictoryScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;

    private Label menuLabel;
    private Label menuText1;

    private Game game;

    private Music victoryTheme;

    public VictoryScreen(Game game) {

        this.game = game;
        viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, ((Main) game).sb);
        sb = new SpriteBatch();


        if(!Main.level1Cleared) {
            Main.levelCleared++;
        }


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        menuLabel = new Label(String.format("Well done Young Blood"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        menuText1 = new Label(String.format("Press ENTER to go back to the Main Menu!"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        table.add(menuLabel).expandX();
        table.row();
        table.add(menuText1).expandX().padTop(100f);

        stage.addActor(table);

        victoryTheme = Main.manager.get("audio/music/victoryTheme.mp3");
        victoryTheme.play();
        victoryTheme.setVolume(0.1f);



    }


    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    public void update(float dt){
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0.1f,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        victoryTheme.dispose();
        stage.dispose();
    }
}
