package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Decorations.Assets;
import com.mygdx.game.GameScreen.MenuScreen;
import com.mygdx.game.Main.WorldController;
import com.mygdx.game.Main.WorldRenderer;

public class CanyonBunnyMain extends Game {
    private static final String TAG =
            CanyonBunnyMain.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;

    @Override
    public void create() {    //set libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //load assets
        Assets.instance.init(new AssetManager());
        //start game at menu screen
        setScreen(new MenuScreen(this));

    }
}
