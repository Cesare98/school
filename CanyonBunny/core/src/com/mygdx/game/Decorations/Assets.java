package com.mygdx.game.Decorations;

/**
 * Created by studente on 15/03/18.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Utility.Constants;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public AssetBunny bunny;
    public AssetRock rock;
    public AssetGoldCoin goldCoin;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;

    // singleton: prevent instantiation from other classes
    private Assets () {
    }

    public AssetFonts fonts;

    public class AssetFonts
    {
        public  BitmapFont defaultSmall ;
        public  BitmapFont defaultNormal;
        public  BitmapFont defaultBig;

        public AssetFonts()
        {
            //create three font using libgdx's 15px bitmap font
            switch (Gdx.app.getType())
            {
                case Android:
                    defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"),true);
                    break;
                case Desktop:
                    defaultSmall =  new BitmapFont(Gdx.files.internal("../CanyonBunny/android/assets/images/arial-15.fnt"),true);
                    break;
            }
            switch (Gdx.app.getType())
            {
                case Android:
                    defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"),true);
                    break;
                case Desktop:
                    defaultNormal =  new BitmapFont(Gdx.files.internal("../CanyonBunny/android/assets/images/arial-15.fnt"),true);
                    break;
            }
            switch (Gdx.app.getType())
            {
                case Android:
                    defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"),true);
                    break;
                case Desktop:
                    defaultBig =  new BitmapFont(Gdx.files.internal("../CanyonBunny/android/assets/images/arial-15.fnt"),true);
                    break;
            }
            //set font sizes
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            //enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }
    }

    public class AssetBunny {
        public final AtlasRegion head;

        public AssetBunny (TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");
        }
    }

    public class AssetRock {
        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public AssetRock (TextureAtlas atlas) {
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock_middle");
        }
    }

    public class AssetGoldCoin {
        public final AtlasRegion goldCoin;

        public AssetGoldCoin (TextureAtlas atlas) {
            goldCoin = atlas.findRegion("item_gold_coin");
        }
    }

    public class AssetFeather {
        public final AtlasRegion feather;

        public AssetFeather (TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }
    }

    public class AssetLevelDecoration {
        public final AtlasRegion cloud01;
        public final AtlasRegion cloud02;
        public final AtlasRegion cloud03;
        public final AtlasRegion mountainLeft;
        public final AtlasRegion mountainRight;
        public final AtlasRegion waterOverlay;

        public AssetLevelDecoration (TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
        }


    }



    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        switch (Gdx.app.getType()){
            case Android:
                assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS_ANDROID,TextureAtlas.class);
                break;
            case Desktop:
                assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS_DESKTOP,TextureAtlas.class);
            break;
        }
        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + a);
        }

            TextureAtlas atlas = null;
        switch (Gdx.app.getType()){
            case Android:
                atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS_ANDROID);
                break;
            case Desktop:
                atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS_DESKTOP);
                break;
        }
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        // create game resource objects
        fonts = new AssetFonts();
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        goldCoin = new AssetGoldCoin(atlas);
        feather = new AssetFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
    }

    @Override
    public void dispose () {
        assetManager.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultBig.dispose();
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);

    }

}