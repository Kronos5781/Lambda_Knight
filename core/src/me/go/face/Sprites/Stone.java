package me.go.face.Sprites;

import com.badlogic.gdx.math.Rectangle;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public class Stone extends InteractiveTileObject {
    public Stone(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        Main.GROUND = "stone";

    }
}
