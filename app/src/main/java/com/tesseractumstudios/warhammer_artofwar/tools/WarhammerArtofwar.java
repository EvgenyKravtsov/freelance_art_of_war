package com.tesseractumstudios.warhammer_artofwar.tools;

import android.app.Application;
import android.content.Context;

public class WarhammerArtofwar extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        WarhammerArtofwar.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return WarhammerArtofwar.context;
    }
}
