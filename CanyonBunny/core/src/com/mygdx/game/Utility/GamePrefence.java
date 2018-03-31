package com.mygdx.game.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePrefence {
    public static final String TAG = GamePrefence.class.getName();

    public static final GamePrefence instance =new GamePrefence();

    public boolean sound;
    public boolean music;
    public float volSound;
    public float volMusic;
    public int charSkin;
    public boolean showFPSCounter;

    private Preferences pref;

    //singleton: prevent instantiation from other class
    private GamePrefence()
    {
        pref = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void save(){
        pref.putBoolean("sound",sound);
        pref.putBoolean("music",music);
        pref.putFloat("volSound",volSound);
        pref.putFloat("volMusic",volMusic);
        pref.putInteger("charSkin",charSkin);
        pref.putBoolean("showFPSCounter",showFPSCounter);
        pref.flush();
    }

    public void load(){
        sound = pref.getBoolean("sound",true);
        music = pref.getBoolean("music",true);
        volSound = MathUtils.clamp(pref.getFloat("volSound",0.5f),0.0f,1.0f);
        volMusic = MathUtils.clamp(pref.getFloat("volMusic",0.5f),0.0f,1.0f);
        charSkin = MathUtils.clamp(pref.getInteger("charSkin",0),0,2);
        showFPSCounter = pref.getBoolean("showFPSCounter",false);
    }

}