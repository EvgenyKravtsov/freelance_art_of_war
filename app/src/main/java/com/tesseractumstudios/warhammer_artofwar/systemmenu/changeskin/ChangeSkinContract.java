package com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin;

interface ChangeSkinContract {

    interface Presenter {

        void attachView(ChangeSkinContract.View view);

        void detachView();

        void onCreateView();

        void onClickBack();

        void onClickClassicOption();

        void onClickRedOption();

        void onClickGrayOption();
    }

    interface View {

        void displayBackground(int resourceId);

        void dismiss();
    }
}
