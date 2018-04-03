package com.mygdx.game.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Decorations.Assets;
import com.mygdx.game.Utility.AbstractGameObject;
import com.mygdx.game.Utility.CharacterSkin;
import com.mygdx.game.Utility.Constants;
import com.mygdx.game.Utility.GamePreferences;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class BunnyHead extends AbstractGameObject {

    public static final  String TAG = BunnyHead.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUM_TIME_OFFSET_FLYING =
            JUMP_TIME_MAX-0.018f;

    public enum VIEW_DIRECTION{LEFT,RIGHT}

    public enum JUMP_STATE{
        GROUNDED,FALLING,JUMP_RISING,JUMP_FALLING
    }

    private TextureRegion regHead;

    public VIEW_DIRECTION viewDirection;
    public float timeJumping;
    public JUMP_STATE jumpState;
    public boolean hasFeatherPowerUp;
    public float timeLeftFeatherPowerUP;
    public ParticleEffect dustParticles = new ParticleEffect();

    public BunnyHead(){init();}

    private void init()
    {
        dimension.set(1,1);
        regHead = Assets.instance.bunny.head;

        //center image on game object
        origin.set(dimension.x/2,dimension.y/2);

        //bounding box for collision detection
        bounds.set(0,0,dimension.x,dimension.y);

        //set physics value
        terminalVelocity.set(3.0f,4.0f);
        friction.set(12.f,0.0f);
        acceleration.set(0.0f,-25.f);

        //View direction
        viewDirection = VIEW_DIRECTION.RIGHT;

        //jump state
        jumpState = JUMP_STATE.FALLING;
        timeJumping=0;

        //power-ups
        hasFeatherPowerUp=false;
        timeLeftFeatherPowerUP =0;

        //Particles
        switch(Gdx.app.getType()) {
            case Android:
            dustParticles.load(Gdx.files.internal("assets/particles/dust.pfx"), Gdx.files.internal("assets/particles"));
            break;
            case Desktop:
                dustParticles.load(Gdx.files.internal("../CanyonBunny/android/assets/particles/dust.pfx"),Gdx.files.internal("../CanyonBunny/android/assets/particles"));
        }
    }

    public void setJumping(boolean jumpKeyPressed)
    {
        switch (jumpState)
        {
            case GROUNDED://character is standing on a platform
                if(jumpKeyPressed){
                    //start counting jump from the beginning
                    timeJumping=0;
                    jumpState=JUMP_STATE.JUMP_RISING;
                }
                break;
            case JUMP_RISING://Rising in the air
                if(!jumpKeyPressed)
                    jumpState=JUMP_STATE.FALLING;
                break;
            case FALLING://falling down
            case JUMP_FALLING://falling down after jump
                if(jumpKeyPressed && hasFeatherPowerUp)
                {
                    timeJumping = JUM_TIME_OFFSET_FLYING;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
        }
    }

    public void setFeatherPowerUP(boolean pickedUp){
        hasFeatherPowerUp=pickedUp;
        if(pickedUp)
        {
            timeLeftFeatherPowerUP=
                    Constants.ITEM_FEATHER_POWERUP_DURATION;
        }
    }

    public boolean hasFeatherPowerUp(){
        return hasFeatherPowerUp && timeLeftFeatherPowerUP>0;
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        if(velocity.x!=0)
        {
            viewDirection = velocity.x<0?VIEW_DIRECTION.LEFT:VIEW_DIRECTION.RIGHT;
        }
        if(timeLeftFeatherPowerUP>0)
        {
            timeLeftFeatherPowerUP -= deltaTime;
            if(timeLeftFeatherPowerUP<0)
            {
                //disable power-up
                timeLeftFeatherPowerUP =0;
                setFeatherPowerUP(false);
            }
        }
        dustParticles.update(deltaTime);
    }

    public void updateMotionY(float deltaTime)
    {
        switch (jumpState)
        {
            case GROUNDED:
                jumpState=JUMP_STATE.FALLING;
                if(velocity.x !=0)
                {
                    dustParticles.setPosition(position.x+dimension.x/2,position.y);
                    dustParticles.start();
                }
                break;

            case JUMP_RISING:

                //keep track of the jump time
                timeJumping += deltaTime;

                //jump time left?
                if(timeJumping<=JUMP_TIME_MAX)
                {
                    //still jumping
                    velocity.y=terminalVelocity.y;
                }
                break;

            case FALLING:
                break;

            case JUMP_FALLING:

                //add delta times to track jump time
                timeJumping += deltaTime;

                //Jump to minimal height if jump key was pressed too short
                if(timeJumping>0&&timeJumping<=JUMP_TIME_MIN)
                {
                    //still jumping
                    velocity.y=terminalVelocity.y;
                }
        }
        if(jumpState != JUMP_STATE.GROUNDED)
        {
            dustParticles.allowCompletion();
            super.updateMotionY(deltaTime);
         }
    }

    public void render(SpriteBatch batch)
    {
        TextureRegion reg = null;

        //Draw particles
        dustParticles.draw(batch);

        //Apply skin Color
        batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());

        //Set special color when game object has a feather power-up
        if(hasFeatherPowerUp)
        {
            batch.setColor(1.0f,0.8f,0.0f,1.0f);
        }
        //draw image
        reg = regHead;
        batch.draw(reg.getTexture(),position.x,position.y,origin.x,origin.y,
                dimension.x,dimension.y,scale.x,scale.y,rotation,reg.getRegionX(),
                reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),
                viewDirection==VIEW_DIRECTION.LEFT,false);

        //reset color to white
        batch.setColor(1,1,1,1);
    }

}
