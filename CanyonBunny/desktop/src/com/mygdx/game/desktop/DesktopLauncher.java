package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.*;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.mygdx.game.CanyonBunnyMain;


public class DesktopLauncher {
    private static boolean rebuildAtlas = true;
    private static boolean drawDebugOutline = false;

    public static void main(String[] args) {
        if (rebuildAtlas) {
            Settings settings = new Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            settings.duplicatePadding = false;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "desktop/assets-raw/images", "../CanyonBunny/android/assets/images", "canyonbunny");
            TexturePacker.process(settings, "desktop/assets-raw/images-ui", "../CanyonBunny/android/assets/images", "canyonbunny-ui");
        }

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "CanyonBunny";
        cfg.width = 800;
        cfg.height = 400;

        new LwjglApplication(new CanyonBunnyMain(), cfg);
    }
}