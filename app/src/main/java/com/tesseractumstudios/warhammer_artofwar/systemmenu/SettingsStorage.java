package com.tesseractumstudios.warhammer_artofwar.systemmenu;

import android.content.Context;
import android.content.SharedPreferences;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin.PreferredSkinStorage;
import com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin.SkinType;

import art.of.war.tesseractumstudios.R;

public final class SettingsStorage implements PreferredSkinStorage {

    private static final String STORAGE = "settings_storage";
    private static final String PREFERRED_SKIN_KEY = "preferred_skin_key";

    private final Context context;

    ////

    public SettingsStorage(Context context) {
        this.context = context;
    }

    //// PREFERRED SKIN STORAGE

    @Override
    public int getPreferredSkinResourceId() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        int skinTypeInt = sharedPreferences.getInt(PREFERRED_SKIN_KEY, 0);
        SkinType skinType = intToSkinType(skinTypeInt);
        return drawableResourceIdForSkinType(skinType);
    }

    @Override
    public void setPreferredSkin(SkinType skinType) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFERRED_SKIN_KEY, skinTypeToInt(skinType));
        editor.apply();
    }

    //// PRIVATE

    private int skinTypeToInt(SkinType skinType) {
        switch (skinType) {
            case Classic: return 0;
            case Red: return 1;
            case Gray: return 2;
            default: return 0;
        }
    }

    private SkinType intToSkinType(int skinTypeInt) {
        switch (skinTypeInt) {
            case 0: return SkinType.Classic;
            case 1: return SkinType.Red;
            case 2: return SkinType.Gray;
            default: return SkinType.Classic;
        }
    }

    private int drawableResourceIdForSkinType(SkinType skinType) {
        switch (skinType) {
            case Classic: return R.drawable.general_background;
            case Red: return R.drawable.red_background;
            case Gray: return R.drawable.gray_background;
            default: return R.drawable.general_background;
        }
    }
}
