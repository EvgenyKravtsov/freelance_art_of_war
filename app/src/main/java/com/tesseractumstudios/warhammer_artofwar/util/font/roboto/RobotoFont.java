package com.tesseractumstudios.warhammer_artofwar.util.font.roboto;


import android.content.Context;
import android.graphics.Typeface;

final class RobotoFont {

    private static Typeface typefaceRegular;

    ////

    RobotoFont(Context context) {
        if (typefaceRegular == null) {
            typefaceRegular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        }
    }

    ////

    Typeface getTypefaceRegular() {
        return typefaceRegular;
    }
}
