package com.mygdx.game.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Decorations.Assets;
import com.mygdx.game.Utility.CharacterSkin;
import com.mygdx.game.Utility.Constants;
import com.mygdx.game.Utility.GamePrefence;


public class MenuScreen extends AbstractGameScreen {

    private static final String TAG = MenuScreen.class.getName();
    private Stage stage;
    private Skin skinCanyonBunny;
    private Skin skinLibgdx;

    //menu
    private Image imgBackground;
    private Image imgLogo;
    private Image imgInfo;
    private Image imgCoins;
    private Image imgBunny;
    private Button btnMenuPlay;
    private Button btnMenuOptions;

    //options
    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkSound;
    private Slider sldSound;
    private CheckBox chkMusic;
    private Slider sldMusic;
    private SelectBox<CharacterSkin> selCharacterSkin;
    private Image imgCharSkin;
    private CheckBox chkShowFPSCounter;

    //debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;

    private boolean paused;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (debugEnabled) {
            debugRebuildStage -= deltaTime;
            if (debugRebuildStage <= 0) {
                debugRebuildStage = DEBUG_REBUILD_INTERVAL;
                rebuildStage();
            }
        }
        stage.act(deltaTime);
        stage.draw();
        stage.setDebugAll(true);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
        skinCanyonBunny.dispose();
        skinLibgdx.dispose();
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        //only called in android!
        paused = false;
    }

    private void rebuildStage() {
        skinCanyonBunny = new Skin(Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

        //build all layers
        Table layerBackground = buildLayerBackground();
        Table layerObjects = buildLayerObjects();
        Table layerLogos = buildLogosLayer();
        Table layerControls = buildControlsLayer();
        Table layerOptionsWindow = buildOptionsWindowLayer();

        //Assemble stage for menu screen
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerObjects);
        stack.add(layerLogos);
        stack.add(layerControls);
        stage.addActor(layerOptionsWindow);
    }

    private Table buildLayerBackground() {
        Table layer = new Table();
        // + background
        imgBackground = new Image(skinCanyonBunny, "background");
        layer.add(imgBackground);
        return layer;
    }

    private Table buildLayerObjects() {
        Table layer = new Table();
        // + Coins
        imgCoins = new Image(skinCanyonBunny, "coins");
        layer.addActor(imgCoins);
        imgCoins.setPosition(135, 80);
        //+ bunny
        imgBunny = new Image(skinCanyonBunny, "bunny");
        layer.addActor(imgBunny);
        imgBunny.setPosition(355, 40);
        return layer;
    }

    private Table buildLogosLayer() {
        Table layer = new Table();
        layer.left().top();
        //Game logo
        imgLogo = new Image(skinCanyonBunny, "logo");
        layer.add(imgLogo);
        layer.row().expandY();
        //+ Info logos
        imgInfo = new Image(skinCanyonBunny, "info");
        layer.add(imgInfo).bottom();
        if (debugEnabled) layer.debug();
        return layer;
    }

    private Table buildControlsLayer() {
        Table layer = new Table();
        layer.right().bottom();
        // + Play Button
        btnMenuPlay = new Button(skinCanyonBunny, "play");
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        // + Options Button
        btnMenuOptions = new Button(skinCanyonBunny, "options");
        layer.add(btnMenuOptions);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        if (debugEnabled) layer.debug();
        return layer;
    }

    private Table buildOptionsWindowLayer() {
        winOptions = new Window("Options", skinLibgdx);
        // + Audio settings: Sound/music Checkbox and Volume slider
        winOptions.add(buildOptWinAudioSettings()).row();
        // + Character skin: Selection box (White, Gray, Brown)
        winOptions.add(buildOptWinSkinSelection()).row();
        // + Debug: Show FPS Counter
        winOptions.add(buildOptWinDebug()).row();
        // + Separator and Buttons (Save, Cancel)
        winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);

        //Make options window slightly transparent
        winOptions.setColor(1, 1, 1, 0.8f);
        //Hide options window by default
        winOptions.setVisible(false);
        if (debugEnabled) winOptions.debug();
        //Let Tablelayout recalculate widget size and position
        winOptions.pack();
        //Move options window to bottom right corner
        winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
        return winOptions;
    }

    private void onPlayClicked() {
        game.setScreen(new GameScreen(game));
    }

    private Table buildOptWinAudioSettings() {
        Table table = new Table();
        // + Title: "Audio"
        table.pad(10, 10, 0, 10);
        table.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3);
        table.row();
        table.columnDefaults(0).padRight(10);
        table.columnDefaults(1).padRight(10);
        //+ Checkbox, "Sound" label, sound volume slider
        chkSound = new CheckBox("", skinLibgdx);
        table.add(chkSound);
        table.add(new Label("Sound", skinLibgdx));
        sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        table.add(sldSound);
        table.row();
        // + Checkbox, "music" label, music volume slider
        chkMusic = new CheckBox("", skinLibgdx);
        table.add(chkMusic);
        table.add(new Label("Music", skinLibgdx));
        sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        table.add(sldMusic);
        table.row();
        return table;
    }

    private Table buildOptWinSkinSelection() {
        Table table = new Table();
        // + Title: "Character Skin"
        table.pad(10, 10, 0, 10);
        table.add(new Label("Character skin", skinLibgdx, "default-font", Color.ORANGE)).colspan(2);
        table.row();
        // + Drop down box filled with skin items
        selCharacterSkin = new SelectBox<CharacterSkin>(skinLibgdx);

        selCharacterSkin.setItems(CharacterSkin.values());

        selCharacterSkin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onCharSkinSelected(((SelectBox<CharacterSkin>) actor).getSelectedIndex());
            }
        });
        table.add(selCharacterSkin).width(120).padRight(20);
        // + Skin preview image
        imgCharSkin = new Image(Assets.instance.bunny.head);
        table.add(imgCharSkin).width(50).height(50);
        return table;
    }

    private Table buildOptWinDebug() {
        Table table = new Table();
        // + Title: "Debug"
        table.pad(10, 10, 0, 10);
        table.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
        table.row();
        table.columnDefaults(0).padRight(10);
        table.columnDefaults(1).padRight(10);
        // + Checkbox: "Show FPS Counter"
        chkShowFPSCounter = new CheckBox("", skinLibgdx);
        table.add(new Label("Show FPS Counter", skinLibgdx));
        table.add(chkShowFPSCounter);
        table.row();
        return table;
    }

    private Table buildOptWinButtons() {
        Table table = new Table();
        // + Separator
        Label label = new Label("", skinLibgdx);
        label.setColor(0.75f, 0.75f, 0.75f, 1);
        label.setStyle(new Label.LabelStyle(label.getStyle()));
        label.getStyle().background = skinLibgdx.newDrawable("white");
        table.add(label).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
        table.row();
        label = new Label("", skinLibgdx);
        label.setColor(0.5f, 0.5f, 0.5f, 1);
        label.setStyle(new Label.LabelStyle(label.getStyle()));
        label.getStyle().background = skinLibgdx.newDrawable("white");
        table.add(label).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
        table.row();
        // + Save Button with event handler
        btnWinOptSave = new TextButton("Save", skinLibgdx);
        table.add(btnWinOptSave).padRight(30);
        btnWinOptSave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSaveClicked();
            }
        });
        //+ cancel Button with event handler
        table.add(btnWinOptCancel);
        btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
        table.add(btnWinOptCancel);
        btnWinOptCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onCancelClicked();
            }
        });
        return table;
    }

    private void onOptionsClicked() {
        loadSettings();
        btnMenuPlay.setVisible(false);
        btnMenuOptions.setVisible(false);
        winOptions.setVisible(true);
    }

    private void loadSettings() {
        GamePrefence pref = GamePrefence.instance;
        pref.load();
        chkSound.setChecked(pref.sound);
        sldSound.setValue(pref.volSound);
        chkMusic.setChecked(pref.music);
        sldMusic.setValue(pref.volSound);
        selCharacterSkin.setSelectedIndex(pref.charSkin);
        onCharSkinSelected(pref.charSkin);
        chkShowFPSCounter.setChecked(pref.showFPSCounter);
    }

    private void saveSettings() {
        GamePrefence prefs = GamePrefence.instance;
        prefs.sound = chkSound.isChecked();
        prefs.volSound = sldSound.getValue();
        prefs.music = chkMusic.isChecked();
        prefs.volMusic = sldMusic.getValue();
        prefs.charSkin = selCharacterSkin.getSelectedIndex();
        prefs.showFPSCounter = chkShowFPSCounter.isChecked();
        prefs.save();
    }

    private void onCharSkinSelected(int index) {
        CharacterSkin skin = CharacterSkin.values()[index];
        imgCharSkin.setColor(skin.getColor());
    }

    private void onSaveClicked() {
        saveSettings();
        onCancelClicked();
    }

    private void onCancelClicked() {
        btnMenuPlay.setVisible(true);
        btnMenuOptions.setVisible(true);
        winOptions.setVisible(false);
    }

}
