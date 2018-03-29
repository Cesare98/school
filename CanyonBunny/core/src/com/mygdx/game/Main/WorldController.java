package com.mygdx.game.Main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.Decorations.Rock;
import com.mygdx.game.GameObject.BunnyHead;
import com.mygdx.game.GameObject.Feather;
import com.mygdx.game.GameObject.GoldCoin;
import com.mygdx.game.Utility.Level;
import com.mygdx.game.Utility.CameraHelper;
import com.mygdx.game.Utility.Constants;

import java.awt.Rectangle;

/**
 * Created by studente on 10/03/18.
 */

public class WorldController extends InputAdapter {

    public Level level;
    public int lives;
    public int score;
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;

    public WorldController() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        init();
    }

    private void initLevel(){
        score =0;
        level = new Level(Constants.LEVEL_01);
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();

    }

    private void initTestObjects() {
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        testCollision();
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;
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
        return false;
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
        Gdx.app.log(TAG,"Feather collected");
    }

    private void testCollision()
    {
        r1.setBounds(level.bunnyHead.bounds);level.bunnyHead.position.x,level.bunnyHead.position.y,
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
}
