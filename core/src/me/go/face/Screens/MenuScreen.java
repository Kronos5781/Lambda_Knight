package me.go.face.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.go.face.Main;

public class MenuScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;

    private Game game;

    private Label menuLabel;
    private Label menuText1;

    //Textures
    private Texture background1080;
    private Texture levelButton1;
    private Texture levelSelectedButton1;
    private Texture levelButton2;
    private Texture levelSelectedButton2;
    private Texture levelButton3;
    private Texture levelSelectedButton3;

    private Music menuTheme;

    private int levelSelected = 1;

    public MenuScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, ((Main) game).sb);
        sb = new SpriteBatch();


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        menuLabel = new Label(String.format("Breites Ritter Game mit viel Moos"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        menuText1 = new Label(String.format("Press ENTER to start!"),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false), Color.WHITE));
        table.add(menuLabel).expandX();
        table.row();
        table.add(menuText1).expandX().padTop(100f);

        stage.addActor(table);

        //Textures
        levelButton1 = new Texture("Textures/images/Level1Button.png");
        levelSelectedButton1 = new Texture("Textures/images/Level1SelectedButton.png");
        levelButton2 = new Texture("Textures/images/Level2Button.png");
        levelSelectedButton2 = new Texture("Textures/images/Level2SelectedButton.png");
        levelButton3 = new Texture("Textures/images/Level3Button.png");
        levelSelectedButton3 = new Texture("Textures/images/Level3SelectedButton.png");


        menuTheme = Main.manager.get("audio/music/menuTheme.mp3", Music.class);
        menuTheme.play();
        menuTheme.setVolume(0.1f);
        menuTheme.setLooping(true);

        levelSelected = Main.levelCleared + 1;
    }

    @Override
    public void show() {

    }

    public void handleInput()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(Main.levelCleared + 1 >= levelSelected){
                Main.MAP_SELECT = levelSelected;
                dispose();
                game.setScreen(new PlayScreen((Main) game));
            }

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && levelSelected < 3)
            levelSelected++;

        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && levelSelected > 1)
            levelSelected--;
    }

    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0.1f,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        sb.begin();

        if(levelSelected == 1) {
            sb.draw(levelSelectedButton1, 200   , 800);
            sb.draw(levelButton2, 700,800);
            sb.draw(levelButton3, 1200,800);
        }else if(levelSelected == 2){
            sb.draw(levelButton1, 200, 800);
            sb.draw(levelSelectedButton2, 700,800);
            sb.draw(levelButton3, 1200,800);
        }else if(levelSelected == 3){
            sb.draw(levelButton1, 200, 800);
            sb.draw(levelButton2, 700,800);
            sb.draw(levelSelectedButton3, 1200,800);
        }



        sb.end();
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
        menuTheme.dispose();
    }
}
