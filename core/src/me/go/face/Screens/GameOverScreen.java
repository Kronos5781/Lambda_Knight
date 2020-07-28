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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.go.face.Main;

public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Label gameOverLabel;
    private Label gameOverText1;

    private Music gameOverTheme;

    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, ((Main) game).sb);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        gameOverLabel = new Label(String.format("GAME OVER"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        gameOverText1 = new Label(String.format("Press Enter to go back to the Main Menu"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(gameOverText1).expandX().padTop(100f);

        stage.addActor(table);

        gameOverTheme = Main.manager.get("audio/music/gameOverTheme.mp3");
        gameOverTheme.play();
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    public void update(float dt)
    {
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1 );
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
        stage.dispose();
        gameOverTheme.dispose();
    }
}
