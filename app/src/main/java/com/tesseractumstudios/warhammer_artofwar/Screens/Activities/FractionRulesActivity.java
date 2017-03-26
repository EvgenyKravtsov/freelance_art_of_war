package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import art.of.war.tesseractumstudios.R;

public class FractionRulesActivity extends ActionBarActivity {
    private Button spaceMarineForces, imperialForces, chaosSpaceMarines, chaosDaemons, eldar,
            darkEldar, orks, necrons, tau, tyranids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_rules);

        findViews();
        adjustFonts();
        setListeners();
    }

    private void findViews() {
        spaceMarineForces   = (Button) findViewById(R.id.fraction_rules_space_marine_forces);
        imperialForces      = (Button) findViewById(R.id.fraction_rules_imperial_forces);
        chaosSpaceMarines   = (Button) findViewById(R.id.fraction_rules_chaos_space_marines);
        chaosDaemons        = (Button) findViewById(R.id.fraction_rules_chaos_daemons);
        eldar               = (Button) findViewById(R.id.fraction_rules_eldar);
        darkEldar           = (Button) findViewById(R.id.fraction_rules_dark_eldar);
        orks                = (Button) findViewById(R.id.fraction_rules_orks);
        necrons             = (Button) findViewById(R.id.fraction_rules_necrons);
        tau                 = (Button) findViewById(R.id.fraction_rules_tau);
        tyranids            = (Button) findViewById(R.id.fraction_rules_tyranids);
    }

    private void adjustFonts() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HERETICA INQUISITOR.TTF");

        spaceMarineForces.setTypeface(typeface);
        imperialForces.setTypeface(typeface);
        chaosSpaceMarines.setTypeface(typeface);
        chaosDaemons.setTypeface(typeface);
        eldar.setTypeface(typeface);
        darkEldar.setTypeface(typeface);
        orks.setTypeface(typeface);
        necrons.setTypeface(typeface);
        tau.setTypeface(typeface);
        tyranids.setTypeface(typeface);
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
