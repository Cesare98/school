package com.mygdx.game.Main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.Decorations.Rock;
import com.mygdx.game.GameObject.BunnyHead;
import com.mygdx.game.GameObject.Feather;
import com.mygdx.game.GameObject.GoldCoin;
import com.mygdx.game.GameScreen.MenuScreen;
import com.mygdx.game.Utility.Level;
import com.mygdx.game.Utility.CameraHelper;
import com.mygdx.game.Utility.Constants;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by studente on 10/03/18.
 */

public class WorldController extends InputAdapter {

    public Level level;
    public int lives;
    public int score;
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    private float timeLeftGameOverDelay;
    private Game game;

    public WorldController(Game game) {
        this.game=game;
        init();
    }

    private void initLevel(){
        score =0;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.bunnyHead);
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        timeLeftGameOverDelay = 0;
        initLevel();

    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        if(isGameOver()){timeLeftGameOverDelay-=deltaTime;
        if(timeLeftGameOverDelay<0)backToMenu();
        }else{
        handleInputGame(deltaTime);}
        level.update(deltaTime);
        testCollision();
        cameraHelper.update(deltaTime);
        if(!isGameOver() && isPlayerInWater())
        {
            lives--;
            if(isGameOver())
                timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
            else
                initLevel();
        }
    }

    public boolean isGameOver(){return lives<0;}

    public boolean isPlayerInWater(){return level.bunnyHead.position.y<-5;}

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

       if(!cameraHelper.getTarget().equals(level.bunnyHead)){
        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
                camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
                -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);
        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
                camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
                -camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
    }
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        //toggle camera follow
        else if(keycode == Keys.ENTER)
        {
            cameraHelper.setTarget(cameraHelper.hasTarget()?null:level.bunnyHead);
            Gdx.app.debug(TAG,"camera follow enabled: "+cameraHelper.hasTarget());
        }

        //Back to menu
        else if(keycode == Keys.ESCAPE || keycode == Keys.BACK)
        {
            backToMenu();
        }
        return false;
    }

    private void handleInputGame(float deltaTime)
    {
        if(cameraHelper.getTarget().equals(level.bunnyHead))
        {
            //Player movement
            if(Gdx.input.isKeyPressed(Keys.LEFT))
            {
                level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
            }else if(Gdx.input.isKeyPressed(Keys.RIGHT))
            {
                level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
            }else
                {
                    //Execute auto-forward movement on non-desktop platform
                    if(Gdx.app.getType()!= Application.ApplicationType.Desktop)
                    {
                        level.bunnyHead.velocity.x= level.bunnyHead.terminalVelocity.x;
                    }
                }

                //bunny jump
            if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE))
            {
                level.bunnyHead.setJumping(true);
            }else{
                level.bunnyHead.setJumping(false);
            }
        }
    }

    //Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private void onCollisionBunnyHeadWithRock(Rock rock){
        BunnyHead bunnyHead = level.bunnyHead;
        float heightDifference = Math.abs(bunnyHead.position.y
                -(rock.position.y+rock.bounds.height));
        if(heightDifference>0.25f)
        {
            boolean hitRigthEdge = bunnyHead.position.x>(
                    rock.position.x + rock.bounds.width/2.0f);
            if(hitRigthEdge)
            {
                bunnyHead.position.x = rock.position.x+rock.bounds.width;
            }else
                {
                    bunnyHead.position.x=rock.position.x-bunnyHead.bounds.width;
                }
                return;
        }
        switch (bunnyHead.jumpState)
        {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                bunnyHead.position.y=rock.position.y+
                        bunnyHead.bounds.height + bunnyHead.origin.y;
                bunnyHead.jumpState = BunnyHead.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                bunnyHead.position.y = rock.position.y+bunnyHead.bounds.height
                        +bunnyHead.origin.y;
                break;
        }
    }

    private void onCollisionBunnyHeadWithGoldCoin(GoldCoin coin)
    {
        coin.collected=true;
        score+=coin.getScore();
        Gdx.app.log(TAG,"Gold coin collected");
    }

    private void onCollisionBunnyHeadWithFeather(Feather feather){
        feather.collected=true;
        score += feather.getScore();
        level.bunnyHead.setFeatherPowerUP(true);
        Gdx.app.log(TAG,"Feather collected");
    }

    private void testCollision()
    {
        r1.set(level.bunnyHead.position.x,level.bunnyHead.position.y,
                level.bunnyHead.bounds.width,level.bunnyHead.bounds.height);

        //test collision:BunnyHead <-> Rocks
        for(Rock rock:level.rocks)
        {
            r2.set(rock.position.x,rock.position.y,rock.bounds.width,rock.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionBunnyHeadWithRock(rock);
            //IMPORTANT: must do all collision for valid
            //edge testing on rocks
        }

        //Test collision:BunnyHead <-> Gold Coins
        for(GoldCoin goldCoin:level.goldCoins)
        {
            if(goldCoin.collected)continue;
            r2.set(goldCoin.position.x,goldCoin.position.y,
                    goldCoin.bounds.width,goldCoin.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionBunnyHeadWithGoldCoin(goldCoin);
            break;
        }

        //Test collision: BunnyHead <-> Feathers
        for(Feather feather:level.feathers)
        {
            if(feather.collected)continue;
            r2.set(feather.position.x,feather.position.y,
                    feather.bounds.width,feather.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionBunnyHeadWithFeather(feather);
            break;
        }
    }

    private void backToMenu()
    {
        //switch to menu screen
        game.setScreen(new MenuScreen(game));
    }

}
