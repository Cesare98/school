package com.mygdx.game.Utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by studente on 17/03/18.
 */

public abstract class AbstractGameObject {

    public Vector2 velocity;//Object current speed in m/s
    public Vector2 terminalVelocity;//positive and negative maximum speed
    public Vector2 friction;//Opposing force that will slow down the object
    public Vector2 acceleration;//The object acceleration in m/(s)^2
    public Rectangle bounds;//Collision box
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    public AbstractGameObject()
    {
        position = new Vector2();
        dimension = new Vector2(1,1);
        origin = new Vector2();
        scale = new Vector2(1,1);
        rotation = 0;
        velocity = new Vector2();
        terminalVelocity = new Vector2(1,1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    public abstract void render(SpriteBatch batch);

    protected void updateMotionX(float deltaTime)
    {
        if(velocity.x!=0)
        {
            //Apply friction
            if(velocity.x>=0)
            {
                velocity.x=Math.max(velocity.x-friction.x*deltaTime,0);
            }else
                {
                    velocity.x = Math.min(velocity.x+friction.x*deltaTime,0);
                }
        }
        //Apply acceleration
        velocity.x += acceleration.x *deltaTime;
        //Make sure the object's velocity does not exceed the
        //positive or negative terminal velocity
        velocity.x = MathUtils.clamp(velocity.x,-terminalVelocity.x,terminalVelocity.x);
    }

    protected void updateMotionY(float deltaTime)
    {
        if(velocity.y!=0)
        {
            //Apply friction
            if(velocity.y>0)
            {
                velocity.y = Math.max(velocity.y-friction.y*deltaTime,0);
            }else
                {
                    velocity.y = Math.min(velocity.y+friction.y*deltaTime,0);
                }
        }
        //Apply friction
        velocity.y += acceleration.y *deltaTime;
        //Make sure the object's velocity does not exceed the
        //positive or negative terminal velocity
        velocity.y = MathUtils.clamp(velocity.y,-terminalVelocity.y,terminalVelocity.y);
    }

    public void update(float deltaTime)
    {
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        //move to new position
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

}
