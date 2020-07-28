package me.go.face.Sprites;

import com.badlogic.gdx.math.Rectangle;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public class FinishLine extends InteractiveTileObject{

    public FinishLine(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        Main.VICTORY = true;
    }
}
