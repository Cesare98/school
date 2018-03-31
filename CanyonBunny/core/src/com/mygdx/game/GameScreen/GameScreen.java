package com.mygdx.game.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Main.WorldController;
import com.mygdx.game.Main.WorldRenderer;
import com.mygdx.game.Utility.Constants;
import com.mygdx.game.Utility.GamePrefence;


public class GameScreen extends AbstractGameScreen {
    private final static String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;

    public GameScreen(Game game)
    {
        super(game);
    }

   @Override
   public void render(float deltaTime)
   {
       //Do not update if the game is paused
       if(!paused)
       {
           //update game world by the time that has passed
           //since last frame
           worldController.update(deltaTime);
       }
       //Sets the clear screen color to: Cornflower Blue
       Gdx.gl.glClearColor(0x64/255.0f,0x95/255.0f,0xed/255.0f,0xff/255.0f);
       //clears the screen
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       //render game world to the screen
       worldRenderer.render();
   }

   @Override
    public void resize(int width,int height)
   {
       worldRenderer.resize(width,height);
   }

   @Override
    public void show()
   {
       GamePrefence.instance.load();
       worldController = new WorldController(game);
       worldRenderer = new WorldRenderer(worldController);
       Gdx.input.setCatchBackKey(true);
   }

    public void hide () {
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }


    @Override  public void pause () {
        paused = true;
    }

    @Override  public void resume () {
        super.resume();
    // Only called on Android!
         paused = false;
        }
}
