package me.go.face.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public class Wood extends InteractiveTileObject{
    public Wood(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        Main.GROUND = "wood";
    }
}
