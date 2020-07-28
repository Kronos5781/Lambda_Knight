package me.go.face.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;
import me.go.face.Tools.SoundManager;


public class Protagonist extends Sprite {

    public enum State{RUNNING,STANDING,JUMPING,ATTACKING, BLOCKING, DEAD};
    public State currentState;
    public State previousState;

    private Animation protRunSword;
    private Animation protRunBow;
    private Animation protJumpSword;
    private Animation protJumpBow;
    private Animation protBreathingSword;
    private Animation protBreathingBow;
    private Animation protAttackingSword;
    private Animation protBlockingSword;
    private Animation protChargingBow;
    private Animation protShootingBow;
    private Animation protBlockingBow;
    private Animation protDead;

    private boolean runningRight;
    private boolean dead = false;
    private float stateTimer;

    private SoundManager soundManager;


    public World world;
    public Body b2body;

    private TextureRegion protagonistStand;

    public Protagonist(PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("breathingknightsword"));
        this.world = screen.getWorld();


        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //Add Running Animation with Sword
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("runningknightsword"), i * 32,0,32,32));
        }
        protRunSword = new Animation(0.1f, frames);
        frames.clear();

        //Add Jumping Animation with Sword
        for(int i = 0; i < 3 ; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jumpingknightsword"), i * 32,0,32,32));
        }
        protJumpSword = new Animation(0.2f,frames);
        frames.clear();

        //Add Standing Animation with Sword
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("breathingknightsword"), i * 32,0,32,32));
        }
        protBreathingSword = new Animation(0.1f,frames);
        frames.clear();

        //ADD Attacking Animation with Sword
        for(int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("attackingknight"), i * 32,0,32,32));
        }
        protAttackingSword = new Animation(0.05f,frames);
        frames.clear();

        //ADD Blocking Animation with Sword
        for(int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("blockingknightsword"), i * 32,0,32,32));
        }
        protBlockingSword = new Animation(0.1f,frames);
        frames.clear();

        //ADD running Animation with Bow
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("runningknightbow"), i * 32,0,32,32));
        }
        protRunBow = new Animation(0.1f,frames);
        frames.clear();

        //ADD Breathing Animation with Bow
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("breathingknightbow"), i * 32,0,32,32));
        }
        protBreathingBow = new Animation(0.1f,frames);
        frames.clear();

        //ADD Jumping Animation with bow
        for(int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jumpingknightbow"), i * 32,0,32,32));
        }
        protJumpBow = new Animation(0.2f,frames);
        frames.clear();

        //ADD Blocking Animation with Bow
        for(int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("blockingknightbow"), i * 32,0,32,32));
        }
        protBlockingBow = new Animation(0.1f,frames);
        frames.clear();

        //ADD Charging Animation with Bow
        for(int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("chargingknightbow"), i * 32,0,32,32));
        }
        protChargingBow = new Animation(0.1f,frames);
        frames.clear();




        //ADD Dead Animation
        for(int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("deadKnight"), i * 32,0,32,32));
        }
        protDead = new Animation(3f,frames);
        frames.clear();


        //Starting Sprite
        protagonistStand = new TextureRegion(screen.getAtlas().findRegion("breathingknightsword"), 64,0,16,16);
        defineProtagonist();
        setBounds(0,0, 32 / Main.PPM,32/  Main.PPM);
        setRegion(protagonistStand);

        //SoundManager
        soundManager = new SoundManager();

    }

    public void update(float dt)
    {
        //Set Sprite on B2Body and get next Frame
        setPosition(b2body.getPosition().x - 0.32f, b2body.getPosition().y - 0.15f);
        setRegion(getFrame(dt));

        //SoundManager
        soundManager.update(dt, currentState);

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){b2body.setTransform(new Vector2(32/Main.PPM, 200/Main.PPM), 0f); Main.GAMEOVER = false;};

        if(currentState == State.DEAD && !dead) {
            soundManager.playDeathScream();
            Filter filter = new Filter();
            filter.maskBits = Main.NOTHING_BIT;
            for(Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 5f), b2body.getWorldCenter(), true);
            dead = true;
        }
        Main.PROT_VELOCITY_Y = (int)b2body.getLinearVelocity().x;


    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        //Set new State
        TextureRegion region;
        switch (currentState){
            case RUNNING:
                if(Main.isSwordOn)
                    region = (TextureRegion) protRunSword.getKeyFrame(stateTimer,true);
                else
                    region = (TextureRegion) protRunBow.getKeyFrame(stateTimer,true);
                break;
            case JUMPING:
                if(Main.isSwordOn)
                    region = (TextureRegion) protJumpSword.getKeyFrame(stateTimer,false);
                else
                    region = (TextureRegion) protJumpBow.getKeyFrame(stateTimer,false);
                break;
            case ATTACKING:
                if(Main.isSwordOn)
                    region = (TextureRegion) protAttackingSword.getKeyFrame(stateTimer,false);
                else
                    region = (TextureRegion) protChargingBow.getKeyFrame(stateTimer,false);

                break;
            case BLOCKING:
                if(Main.isSwordOn)
                    region = (TextureRegion) protBlockingSword.getKeyFrame(stateTimer,false);
                else
                    region = (TextureRegion) protBlockingBow.getKeyFrame(stateTimer,false);
                break;
            case DEAD:
                region = (TextureRegion) protDead.getKeyFrame(stateTimer,false);
                break;
            default:
                if(Main.isSwordOn)
                    region = (TextureRegion) protBreathingSword.getKeyFrame(stateTimer,true);
                else
                    region = (TextureRegion) protBreathingBow.getKeyFrame(stateTimer,true);
                break;
        }
        //switch pointing directions

            if(((Gdx.input.isKeyPressed(Input.Keys.A) || !runningRight) && !region.isFlipX()) && !Main.GAMEOVER){
                region.flip(true,false);
                runningRight = false;
                Main.KNIGHT_POINTING_RIGHT = runningRight;
            }
            else if((Gdx.input.isKeyPressed(Input.Keys.D) || runningRight) && region.isFlipX() && !Main.GAMEOVER){
                region.flip(true,false);
                runningRight = true;
                Main.KNIGHT_POINTING_RIGHT = runningRight;
            }

        //Set State Timer
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState()
    {
        //Conditions for new State
        if(Main.GAMEOVER){
        Main.KNIGHT_STATE = "Dead";
        return State.DEAD;
        } else if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.H)){
            Main.KNIGHT_STATE = "attacking";
            return State.ATTACKING;
        } else if(Gdx.input.isKeyPressed(Input.Keys.J)){
            Main.KNIGHT_STATE = "blocking";
            if(b2body.getLinearVelocity().y == 0)
                b2body.setLinearVelocity(0,0);
            return State.BLOCKING;
        } else if(b2body.getLinearVelocity().y != 0){
            Main.KNIGHT_STATE = "nothing";
          return  State.JUMPING;
        } else if(b2body.getLinearVelocity().x != 0) {
            Main.KNIGHT_STATE = "nothing";
            return State.RUNNING;
        } else{
            Main.KNIGHT_STATE = "nothing";
            return State.STANDING;
        }

    }

    public void defineProtagonist()
    {
        //Make new Box2D Object at a certain Pos
        BodyDef bdef = new BodyDef();
        bdef.position.set(240 /Main.PPM, 170/Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        //Make a Circle shape to draw the Object
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //Set Radius and add it to "b2body"
        shape.setAsBox(3/Main.PPM,7/Main.PPM);
        fdef.filter.categoryBits = Main.PROT_BIT;
        fdef.filter.maskBits =  Main.OBJECT_BIT | Main.GROUND_BIT | Main.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("knight");

        //Make Sword RIGHT
        PolygonShape swordright = new PolygonShape();
        Vector2[] vertice = new  Vector2[4];
        vertice[0] = new Vector2(6, -8).scl(1/Main.PPM);
        vertice[1] = new Vector2(6, 4).scl(1/Main.PPM);
        vertice[2] = new Vector2(20, 4).scl(1/Main.PPM);
        vertice[3] = new Vector2(20,-8).scl(1/Main.PPM);
        swordright.set(vertice);
        fdef.shape = swordright;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("swordright");

        //Make Sword LEFT
        PolygonShape swordleft = new PolygonShape();
        vertice[0] = new Vector2(-6, -8).scl(1/Main.PPM);
        vertice[1] = new Vector2(-6, 4).scl(1/Main.PPM);
        vertice[2] = new Vector2(-20, 4).scl(1/Main.PPM);
        vertice[3] = new Vector2(-20,-8).scl(1/Main.PPM);
        swordleft.set(vertice);
        fdef.shape = swordleft;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("swordleft");

        //Make Shield Right
        EdgeShape shieldright = new EdgeShape();
        shieldright.set(new Vector2(12/Main.PPM,7/Main.PPM),new Vector2(12/Main.PPM,5/Main.PPM));
        fdef.shape = shieldright;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("shieldright");

        //Make Shield Left
        EdgeShape shieldleft = new EdgeShape();
        shieldleft.set(new Vector2(-12/Main.PPM,7/Main.PPM),new Vector2(-12/Main.PPM,5/Main.PPM));
        fdef.shape = shieldleft;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("shieldleft");
    }

    public float getStateTimer(){return stateTimer;}


}
