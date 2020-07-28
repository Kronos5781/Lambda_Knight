package me.go.face.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, Rectangle bounds)
    {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ Main.PPM, (bounds.getY() + bounds.getHeight()/2)/Main.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 /Main.PPM, bounds.getHeight() /2 /Main.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = Main.OBJECT_BIT;
        fixture = body.createFixture(fdef);


    }

    public abstract void onHit();
}
