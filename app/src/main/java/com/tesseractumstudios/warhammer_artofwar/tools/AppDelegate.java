package com.tesseractumstudios.warhammer_artofwar.tools;

import java.io.IOException;
import java.io.InputStream;

public class AppDelegate {
    private static volatile AppDelegate instance;

    public static AppDelegate getInstance() {
        AppDelegate localInstance = instance;

        if ( localInstance == null ) {
            synchronized (AppDelegate.class) {
                localInstance = instance;
                if ( localInstance == null ) {
                    instance = localInstance = new AppDelegate();
                }
            }
        }

        return localInstance;
    }

    public String readStringFromAssets(String relativePath) {
        String      jsonString;
        InputStream is          = null;

        try {
            int     size;
            byte[]  buffer;

            is      = WarhammerArtofwar.getAppContext().getAssets().open(relativePath);
            size    = is.available();
            buffer  = new byte[size];

            is.read(buffer);
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();

            return "{}";
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonString;
    }
}
