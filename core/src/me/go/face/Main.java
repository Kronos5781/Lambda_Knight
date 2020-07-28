package me.go.face;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.go.face.Screens.MenuScreen;
import me.go.face.Screens.PlayScreen;
import me.go.face.Sprites.Protagonist;


public class Main extends Game {

	public SpriteBatch sb;
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1080;
	public static boolean GAMEOVER = false;
	public static AssetManager manager;
	public static String GROUND = "";
	public static int MAP_SELECT = 1;
	public static String KNIGHT_STATE = "nothing";
	public static boolean KNIGHT_POINTING_RIGHT = true;
	public static int PROT_VELOCITY_Y;
	public static short PROT_KILLS = 0;
	public static boolean VICTORY = false;
	public static int levelCleared = 1;
	public static boolean level1Cleared = false;
	public static boolean isSwordOn = true;


	//Bits
	public static final short OBJECT_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short PROT_BIT = 2;
	public static final short GROUND_BIT = 1;
	public static final short ENEMY_BOUNDS_BIT = 16;
	public static final short NOTHING_BIT = 32;


	//Variable for Scaling the world so that the gravity... is right
	public static final float PPM = 50;


	@Override
	public void create () {
		sb = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/playTheme.mp3", Music.class);
		manager.load("audio/sound/mudfootstep.ogg", Sound.class);
		manager.load("audio/sound/woodfootstep.ogg", Sound.class);
		manager.load("audio/sound/walkingarmor.ogg", Sound.class);
		manager.load("audio/sound/hitsound.ogg", Sound.class);
		manager.load("audio/sound/blocksound.ogg", Sound.class);
		manager.load("audio/sound/stabsound.ogg", Sound.class);
		manager.load("audio/sound/grabshieldsound.ogg", Sound.class);
		manager.load("audio/sound/deathScream.ogg", Sound.class);
        manager.load("audio/sound/stonefootstep.ogg", Sound.class);
        manager.load("audio/music/menuTheme.mp3", Music.class);
        manager.load("audio/sound/explosion.ogg", Sound.class);
		manager.load("audio/music/gameOverTheme.mp3", Music.class);
		manager.load("audio/music/victoryTheme.mp3", Music.class);


		manager.finishLoading();

		setScreen(new MenuScreen(this));
	}


	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		manager.dispose();
		sb.dispose();
	}
}
