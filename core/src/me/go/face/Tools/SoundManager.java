package me.go.face.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import me.go.face.Main;
import me.go.face.Sprites.Protagonist;

public class SoundManager {

    private Sound mudStep;
    private Sound woodStep;
    private Sound stoneStep;
    private Sound walkingArmor;
    private Sound hitSound;
    private Sound grabShieldSound;
    private Sound deathSound;



    private float stepCounter;



    public SoundManager()
    {
        mudStep = Main.manager.get("audio/sound/mudfootstep.ogg");
        woodStep = Main.manager.get("audio/sound/woodfootstep.ogg");
        walkingArmor = Main.manager.get("audio/sound/walkingarmor.ogg");
        hitSound = Main.manager.get("audio/sound/hitsound.ogg");
        grabShieldSound = Main.manager.get("audio/sound/grabshieldsound.ogg");
        deathSound = Main.manager.get("audio/sound/deathScream.ogg");
        stoneStep = Main.manager.get("audio/sound/stonefootstep.ogg");

    }

    public void update(float dt, Protagonist.State state)
    {
        if(!Main.GAMEOVER) {
            stepCounter += dt;

            if (stepCounter > 0.3f && state == Protagonist.State.RUNNING) {
                if (Main.GROUND.equals("dirt")) {
                    mudStep.play(1f);
                    walkingArmor.play(0.5f);
                } else if (Main.GROUND.equals("wood")) {
                    woodStep.play(1f);
                    walkingArmor.play(0.5f);
                }
                else if (Main.GROUND.equals("stone")){
                    stoneStep.play(0.1f);
                    walkingArmor.play(0.7f);
                }

                stepCounter = 0;

            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.H))
                hitSound.play(0.5f);
            if (Gdx.input.isKeyJustPressed(Input.Keys.J))
                grabShieldSound.play(0.7f);
        }
    }

    public void playDeathScream(){
        deathSound.play();
    }


}
