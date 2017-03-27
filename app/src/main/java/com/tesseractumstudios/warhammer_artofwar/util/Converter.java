package com.tesseractumstudios.warhammer_artofwar.util;


import android.content.Context;

public final class Converter {

    public static int dpTpPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (dp * scale + 0.5f);
    }
}
