package me.go.face.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public class Lava extends InteractiveTileObject{

    public Lava(PlayScreen screen, Rectangle bounds) {
        super(screen,bounds);
        fixture.setUserData(this);

    }

    @Override
    public void onHit() {
        Main.GAMEOVER = true;
    }






}
