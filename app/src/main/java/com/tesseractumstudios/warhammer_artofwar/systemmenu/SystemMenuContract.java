package com.tesseractumstudios.warhammer_artofwar.systemmenu;

import android.graphics.drawable.Drawable;

interface SystemMenuContract {

    interface Presenter {

        void attachView(SystemMenuContract.View view);

        void detachView();

        void onCreateView();

        void onClickChangeSkinOption();
    }

    interface View {

        void displayBackground(int resourceId);

        void navigateToChangeSkin();
    }
}
