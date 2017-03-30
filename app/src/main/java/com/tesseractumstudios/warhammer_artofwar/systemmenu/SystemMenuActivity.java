package com.tesseractumstudios.warhammer_artofwar.systemmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.changeskin.ChangeSkinFragment;

import art.of.war.tesseractumstudios.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class SystemMenuActivity extends AppCompatActivity implements SystemMenuContract.View {

    @BindView(R.id.systemMenuActivity_backgroundImageView)
    ImageView backgroundImageView;

    private static final String TAG = SystemMenuActivity.class.getSimpleName();

    private SystemMenuContract.Presenter presenter;

    //// ACTIVITY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_menu);
        ButterKnife.bind(this);

        presenter = new SystemMenuPresenter(new SettingsStorage(this));
        presenter.attachView(this);
        presenter.onCreateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    //// SYSTEM ACTIVITY VIEW

    @Override
    public void displayBackground(int resourceId) {
        backgroundImageView.setImageDrawable(getResources().getDrawable(resourceId));
    }

    @Override
    public void navigateToChangeSkin() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChangeSkinFragment fragment = new ChangeSkinFragment();
        fragmentTransaction.add(R.id.systemMenuActivity_rootFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    //// CONTROL CALLBACKS

    @OnClick(R.id.systemMenuActivity_changeSkinRelativeLayout)
    public void onClickChangeSkinRelativeLayout() {
        presenter.onClickChangeSkinOption();
    }
}











































