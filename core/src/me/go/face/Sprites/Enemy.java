package me.go.face.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    protected World world;
    protected Screen screen;
    public Body b2body;
    public Vector2 velocityBall;
    public Vector2 velocityDevil;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        velocityBall = new Vector2(0,1);
        velocityDevil = new Vector2(1,0);

        setPosition(x,y);
        defineEnemy();
    }

    protected abstract void defineEnemy();
    public abstract  void update(float dt);
    public abstract void onHit();
    public abstract void endHit();
    public abstract void onShieldHit();

    public void reverseVelocity(boolean reverse){
        if(reverse)
        {
            velocityDevil.x = velocityDevil.x * -1;
            velocityBall.x = velocityBall.x * -1;
            velocityDevil.y = velocityDevil.y * -1;
            velocityBall.y = velocityBall.y * -1;
        }
    }
}

