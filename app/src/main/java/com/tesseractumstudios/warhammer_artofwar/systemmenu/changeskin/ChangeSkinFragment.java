package com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.SettingsStorage;

import art.of.war.tesseractumstudios.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ChangeSkinFragment extends Fragment implements ChangeSkinContract.View {

    @BindView(R.id.changeSkinFragment_rootLinearLayout)
    LinearLayout rootLinearLayout;

    private ChangeSkinContract.Presenter presenter;

    //// FRAGMENT

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_skin, container, false);
        ButterKnife.bind(this, view);

        presenter = new ChangeSkinPresenter(new SettingsStorage(getActivity()));
        presenter.attachView(this);
        presenter.onCreateView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    //// CHANGE SKIN VIEW

    @Override
    public void displayBackground(int resourceId) {
        rootLinearLayout.setBackground(getResources().getDrawable(resourceId));
    }

    @Override
    public void dismiss() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        getActivity().recreate();
    }

    //// CONTROL CALLBACKS

    @OnClick(R.id.changeSkinFragment_backImageButton)
    public void onClickBackImageButton() {
        presenter.onClickBack();
    }

    @OnClick(R.id.changeSkinFragment_classicOptionFrameLayout)
    public void onClickClassicOptionFrameLayout() {
        presenter.onClickClassicOption();
    }

    @OnClick(R.id.changeSkinFragment_redOptionFrameLayout)
    public void onClickRedOptionFrameLayout() {
        presenter.onClickRedOption();
    }

    @OnClick(R.id.changeSkinFragment_grayOptionFrameLayout)
    public void onClickGrayOptionFrameLayout() {
        presenter.onClickGrayOption();
    }
}




















