package com.tesseractumstudios.warhammer_artofwar.systemmenu;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin.PreferredSkinStorage;

import art.of.war.tesseractumstudios.R;

final class SystemMenuPresenter implements SystemMenuContract.Presenter {

    private SystemMenuContract.View view;

    private final PreferredSkinStorage preferredSkinStorage;

    ////

    SystemMenuPresenter(PreferredSkinStorage preferredSkinStorage) {
        this.preferredSkinStorage = preferredSkinStorage;
    }

    //// SYSTEM MENU PRESENTER

    @Override
    public void attachView(SystemMenuContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void onCreateView() {
        view.displayBackground(preferredSkinStorage.getPreferredSkinResourceId());
    }

    @Override
    public void onClickChangeSkinOption() {
        view.navigateToChangeSkin();
    }
}
