package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.SettingsStorage;

import art.of.war.tesseractumstudios.R;

public class FractionRulesActivity extends ActionBarActivity {
    private RelativeLayout spaceMarineForces, imperialForces, chaosSpaceMarines, chaosDaemons, eldar,
            darkEldar, orks, necrons, tau, tyranids;
    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_rules);

        findViews();
        setListeners();

        // Set Back Button
        ImageView backButton = (ImageView) findViewById(R.id.fractionRulesActivity_backImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FractionRulesActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundImageView.setImageDrawable(getResources().getDrawable(
                new SettingsStorage(this).getPreferredSkinResourceId()));
    }

    private void findViews() {
        spaceMarineForces   = (RelativeLayout) findViewById(R.id.fraction_rules_space_marine_forces);
        imperialForces      = (RelativeLayout) findViewById(R.id.fraction_rules_imperial_forces);
        chaosSpaceMarines   = (RelativeLayout) findViewById(R.id.fraction_rules_chaos_space_marines);
        chaosDaemons        = (RelativeLayout) findViewById(R.id.fraction_rules_chaos_daemons);
        eldar               = (RelativeLayout) findViewById(R.id.fraction_rules_eldar);
        darkEldar           = (RelativeLayout) findViewById(R.id.fraction_rules_dark_eldar);
        orks                = (RelativeLayout) findViewById(R.id.fraction_rules_orks);
        necrons             = (RelativeLayout) findViewById(R.id.fraction_rules_necrons);
        tau                 = (RelativeLayout) findViewById(R.id.fraction_rules_tau);
        tyranids            = (RelativeLayout) findViewById(R.id.fraction_rules_tyranids);

        backgroundImageView = (ImageView) findViewById(R.id.fractionRulesActivity_backgroundImageView);
    }

    private void setListeners() {
        spaceMarineForces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Space Marine Forces");
            }
        });
        imperialForces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Imperial Forces");
            }
        });
        chaosSpaceMarines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Chaos Space Marines");
            }
        });
        chaosDaemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Chaos Daemons");
            }
        });
        eldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Eldar");
            }
        });
        darkEldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Dark Eldar");
            }
        });
        orks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Orks");
            }
        });
        necrons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Necrons");
            }
        });
        tau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Tau");
            }
        });
        tyranids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionRules("Tyranids");
            }
        });
    }

    private void openFractionRules(String fractionName) {
        Intent intent = new Intent(this, FractionRulesSumbmenuActivity.class);

        intent.putExtra("path", "app_data/fraction_rules/" + fractionName);
        startActivity(intent);
    }
}
