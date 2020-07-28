package me.go.face.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import me.go.face.Main;
import me.go.face.Screens.PlayScreen;
import me.go.face.Sprites.Enemy;
import me.go.face.Sprites.InteractiveTileObject;
import me.go.face.Sprites.Protagonist;
import me.go.face.Sprites.RedBall;

public class WorldContactListener implements ContactListener {




    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        //Knight contact with lava and change sound and finish line
        if(fixA.getUserData() == "knight" || fixB.getUserData() == "knight")
        {
            Fixture knight = fixA.getUserData() == "knight" ? fixA : fixB;
            Fixture object = knight == fixA ? fixB : fixA;

            if((object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())))
                ((InteractiveTileObject) object.getUserData()).onHit();

        }




        //Make Hit detection for Right Sword
        if(fixA.getUserData() == "swordright" || fixB.getUserData() == "swordright") {
            Fixture sword = fixA.getUserData() == "swordright" ? fixA : fixB;
            Fixture object = sword == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass()) && Main.KNIGHT_POINTING_RIGHT){
                ((Enemy) object.getUserData()).onHit();
            }

        }

        //Make Hit detection for left Sword
        if(fixA.getUserData() == "swordleft" || fixB.getUserData() == "swordleft" ) {
            Fixture sword = fixA.getUserData() == "swordleft" ? fixA : fixB;
            Fixture object = sword == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass()) && !Main.KNIGHT_POINTING_RIGHT){
                ((Enemy) object.getUserData()).onHit();
            }
        }

        //Make Collision for RightShield
        if(fixA.getUserData() == "shieldright" || fixB.getUserData() == "shieldright" ) {
            Fixture shield = fixA.getUserData() == "shieldright" ? fixA : fixB;
            Fixture object = shield == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass()) && Main.KNIGHT_POINTING_RIGHT){
                ((Enemy) object.getUserData()).onShieldHit();
            }
        }

        //Make Collision for LeftShield
        if(fixA.getUserData() == "shieldleft" || fixB.getUserData() == "shieldleft" ) {
            Fixture shield = fixA.getUserData() == "shieldleft" ? fixA : fixB;
            Fixture object = shield == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass()) && !Main.KNIGHT_POINTING_RIGHT){
                ((Enemy) object.getUserData()).onShieldHit();
            }
        }

        //Knight Death
        if(fixA.getUserData() == "knight" || fixB.getUserData() == "knight"  )
        {
            Fixture knight = fixA.getUserData() == "knight" ? fixA : fixB;
            Fixture object = knight == fixA ? fixB : fixA;


            if(object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
                Main.GAMEOVER = true;
            }


        }

        //Switch case for all other collisions
        switch(cDef){
            case Main.ENEMY_BIT | Main.ENEMY_BOUNDS_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true);
                break;

            case Main.ENEMY_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true);
                break;

            case Main.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true);
                ((Enemy)fixB.getUserData()).reverseVelocity(true);
                break;


        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();




        //Make End detection for Right Sword and Shield
        if(fixA.getUserData() == "swordright" || fixB.getUserData() == "swordright") {
            Fixture sword = fixA.getUserData() == "swordright" ? fixA : fixB;
            Fixture object = sword == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())){
                ((Enemy) object.getUserData()).endHit();
            }


        }

        //Make End detection for left Sword and Shield
        if(fixA.getUserData() == "swordleft" || fixB.getUserData() == "swordleft" ) {
            Fixture sword = fixA.getUserData() == "swordleft" ? fixA : fixB;
            Fixture object = sword == fixA ? fixB : fixA;

            if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())){
                ((Enemy) object.getUserData()).endHit();
            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
