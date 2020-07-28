package me.go.face.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.go.face.Main;
import me.go.face.Scenes.Hud;
import me.go.face.Sprites.Enemy;
import me.go.face.Sprites.Protagonist;
import me.go.face.Tools.B2WorldCreator;
import me.go.face.Tools.MapManager;
import me.go.face.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private Main game;

    //Protagonist
    private Protagonist protagonist;
    private TextureAtlas atlas;

    //Cam,Viewport, Hud Variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //TiledMaps Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;


    //Texture Packer Atlas
    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    //Music and Sounds
    private Music theme;


    //Map manager
    private MapManager mapManager;




    public PlayScreen(Main game) {
        this.game = game;

        //map Manager
        mapManager = new MapManager();

        //create GameCam
        gameCam = new OrthographicCamera();

        //create GamePort
        gamePort = new FitViewport(Main.V_WIDTH /5 /Main.PPM, Main.V_HEIGHT/5 /Main.PPM, gameCam);

        //create HUD
        hud = new Hud(game.sb);

        //create TiledMaps
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapManager.getMap(Main.MAP_SELECT));
        renderer = new OrthogonalTiledMapRenderer(map,1 /Main.PPM);

        //create GameCamStartposition
        gameCam.position.set(159 /Main.PPM,89 /Main.PPM,0);

        //create Box2DVariables
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();





        //Create Protagonist
        atlas = new TextureAtlas("Textures/Atlas Pack/textures.pack");
        protagonist = new Protagonist(this);

        //B2 World Creator
        creator = new B2WorldCreator(this);

        //Contact Listener
        world.setContactListener(new WorldContactListener());

        //Music and Sounds
        theme = Main.manager.get("audio/music/playTheme.mp3", Music.class);
        theme.setLooping(true);
        theme.setVolume(0.15f);
        theme.play();




    }




    @Override
    public void show() {

    }

    public void handleInput(float dt)
    {
        if(!Main.GAMEOVER) {
            //Input for Protagonist
            if (Gdx.input.isKeyPressed(Input.Keys.D) && protagonist.b2body.getLinearVelocity().x <= 2f && protagonist.currentState != Protagonist.State.BLOCKING && protagonist.currentState != Protagonist.State.ATTACKING) {
                protagonist.b2body.applyLinearImpulse(new Vector2(0.1f, 0), protagonist.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && protagonist.b2body.getLinearVelocity().x >= -2f && protagonist.currentState != Protagonist.State.BLOCKING && protagonist.currentState != Protagonist.State.ATTACKING) {
                protagonist.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), protagonist.b2body.getWorldCenter(), true);
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (protagonist.currentState == Protagonist.State.STANDING || protagonist.currentState == Protagonist.State.RUNNING)) {
                protagonist.b2body.applyLinearImpulse(new Vector2(0, 4f), protagonist.b2body.getWorldCenter(), true);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
                if(Main.isSwordOn)
                    Main.isSwordOn = false;
                else
                    Main.isSwordOn = true;
            }

        }
    }

    public void update(float dt)
    {
        handleInput(dt);
        hud.update(dt);

        //Create Box 2d objects
        world.step(1/60f, 6, 2);

        protagonist.update(dt);
        //Update Red Balls
        for(Enemy enemy : creator.getRedBalls()) {
            enemy.update(dt);
        }

        //Update Devils
        for(Enemy enemy : creator.getDevils()){
            enemy.update(dt);
        }

        //Make Cam follow Protagonist
        if(!Main.GAMEOVER) {
            gameCam.position.x = protagonist.b2body.getPosition().x;
            gameCam.position.y = protagonist.b2body.getPosition().y;
        }



        //Update Cam
        gameCam.update();

        //Only things get renderered that you can see
        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render TiledMap
        renderer.render();

        //Render Outline for Box2D Objects
        //b2dr.render(world,gameCam.combined);


        //Render Protagonist
        game.sb.setProjectionMatrix(gameCam.combined);
        game.sb.begin();
        protagonist.draw(game.sb);

        //Draw Red Balls
        for(Enemy enemy : creator.getRedBalls()) {
            enemy.draw(game.sb);
        }
        for(Enemy enemy : creator.getDevils()) {
            enemy.draw(game.sb);
        }
        //Draw Devils

        game.sb.end();

        //Give Hud Stage Camera and render the Hud
        game.sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if(Main.VICTORY){
            dispose();
            Main.VICTORY = false;
            game.setScreen(new VictoryScreen(game));
        }
    }

    // Wait one second until Game Over Screen Appears
    public boolean gameOver(){
        if(protagonist.currentState == Protagonist.State.DEAD && protagonist.getStateTimer() > 2) {
            return true;
        }
        else
            return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    public TiledMap getMap(){
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
        theme.dispose();
        atlas.dispose();
        Main.GAMEOVER = false;
    }
}
