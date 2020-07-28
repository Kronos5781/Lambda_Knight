package me.go.face.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;

public class RedBall extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Animation dyingAnimation;

    private Array<TextureRegion> frames;
    private Boolean willBeDestroyed;
    private Boolean destroyed;
    private Boolean contact = false;

    private Sound blockSound;
    private Sound explosionSound;



    public RedBall(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        setPosition(x,y);

        willBeDestroyed = false;
        destroyed = false;

        //Ball Animation
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("ball"), i*16,0,16,16));
        }
        walkAnimation = new Animation(0.3f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,16 / Main.PPM);
        frames.clear();

        //Dying Animation
        for(int i = 0; i < 10; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("dyingball"), i*16,0,16,16));
        }
        dyingAnimation = new Animation(0.1f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,16 / Main.PPM);
        frames.clear();

        blockSound = Main.manager.get("audio/sound/blocksound.ogg");
        explosionSound = Main.manager.get("audio/sound/explosion.ogg", Sound.class);


    }

    public void update(float dt) {
        stateTime += dt;
        if (willBeDestroyed != destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
            explosionSound.play(0.5f);
            Main.PROT_KILLS++;
        }
        if (destroyed) {
            setRegion((TextureRegion) dyingAnimation.getKeyFrame(stateTime, false));
        }
        if (!destroyed) {

            b2body.setLinearVelocity(velocityBall);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));

            if(contact && Main.KNIGHT_STATE.equals("attacking"))
                willBeDestroyed = true;
        }
    }



    @Override
    protected void defineEnemy() {
        //Make new Box2D Object at a certain Pos
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX()/ Main.PPM, getY()/Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        //Make a Circle shape to draw the Object
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        //Set Radius and add it to "b2body"
        shape.setRadius(7/ Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.PROT_BIT | Main.OBJECT_BIT | Main.ENEMY_BOUNDS_BIT | Main.ENEMY_BIT | Main.GROUND_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch sb){
        if(!destroyed || stateTime < 1d)
            super.draw(sb);
    }

    @Override
    public void onHit() {
        if(Main.KNIGHT_STATE != "attacking")
            contact = true;
}

    @Override
    public void endHit() {
        contact = false;
    }

    @Override
    public void onShieldHit() {

        if(Main.KNIGHT_STATE == "blocking" && Main.PROT_VELOCITY_Y == 0){
            reverseVelocity(true);
            b2body.applyLinearImpulse(new Vector2(4,0), b2body.getWorldCenter(), true);
            blockSound.play(0.5f);
        }
    }
}
