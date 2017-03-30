package com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin;

import art.of.war.tesseractumstudios.R;

final class ChangeSkinPresenter implements ChangeSkinContract.Presenter {

    private ChangeSkinContract.View view;

    private final PreferredSkinStorage preferredSkinStorage;

    ////

    ChangeSkinPresenter(PreferredSkinStorage preferredSkinStorage) {
        this.preferredSkinStorage = preferredSkinStorage;
    }

    //// CHANGE SKIN PRESENTER

    @Override
    public void attachView(ChangeSkinContract.View view) {
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
    public void onClickBack() {
        view.dismiss();
    }

    @Override
    public void onClickClassicOption() {
        preferredSkinStorage.setPreferredSkin(SkinType.Classic);
        view.displayBackground(preferredSkinStorage.getPreferredSkinResourceId());
    }

    @Override
    public void onClickRedOption() {
        preferredSkinStorage.setPreferredSkin(SkinType.Red);
        view.displayBackground(preferredSkinStorage.getPreferredSkinResourceId());
    }

    @Override
    public void onClickGrayOption() {
        preferredSkinStorage.setPreferredSkin(SkinType.Gray);
        view.displayBackground(preferredSkinStorage.getPreferredSkinResourceId());
    }
}
