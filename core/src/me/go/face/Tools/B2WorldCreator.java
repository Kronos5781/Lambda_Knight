package me.go.face.Tools;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;
import me.go.face.Sprites.*;


public class B2WorldCreator {

    private Array<RedBall> redBalls;
    private Array<Devil> devils;



    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        Map map = screen.getMap();
        //Box2d Variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //Make Collision for the Ground Layer
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Main.PPM, (rect.getY() + rect.getHeight()/2)/Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Main.PPM, rect.getHeight() /2 /Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.GROUND_BIT;
            body.createFixture(fdef);
        }

        //Make Collision for Enemys
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Main.PPM, (rect.getY() + rect.getHeight()/2)/Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Main.PPM, rect.getHeight() /2 /Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.ENEMY_BOUNDS_BIT;
            body.createFixture(fdef);
        }

        //Make Collison for Lava

        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Lava(screen ,rect);
        }

        //Make Collision for Dirt
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Dirt(screen,rect);
        }


        //Make Collision for Wood
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Wood(screen,rect);
        }

        //Make Collision for Stone
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Stone(screen,rect);
        }

        //create All RedBalls
        redBalls = new Array<RedBall>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            float y = rect.getY() + 0.1f;

            redBalls.add(new RedBall(screen,rect.getX(),y));
        }

        //create Collision for FinishLine
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new FinishLine(screen,rect);
        }

        //create Collision for Devil
        devils = new Array<Devil>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            float y = rect.getY() + 0.1f;

            devils.add(new Devil(screen,rect.getX(),y));
        }


    }
    public Array<RedBall> getRedBalls() {
        return redBalls;
    }
    public Array<Devil> getDevils(){return devils;}
}
