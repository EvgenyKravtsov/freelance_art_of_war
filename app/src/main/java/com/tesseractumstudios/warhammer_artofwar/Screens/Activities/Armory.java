package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import art.of.war.tesseractumstudios.R;

public class Armory extends ActionBarActivity {
    private Button spaceMarineForces, imperialForces, chaosSpaceMarines, chaosDaemons, eldar,
                            darkEldar, orks, necrons, tau, tyranids, rulebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armory);

        findViews();
        adjustFonts();
        setListeners();
    }

    private void findViews() {
        spaceMarineForces   = (Button) findViewById(R.id.armory_space_marine_forces);
        imperialForces      = (Button) findViewById(R.id.armory_imperial_forces);
        chaosSpaceMarines   = (Button) findViewById(R.id.armory_chaos_space_marines);
        chaosDaemons        = (Button) findViewById(R.id.armory_chaos_daemons);
        eldar               = (Button) findViewById(R.id.armory_eldar);
        darkEldar           = (Button) findViewById(R.id.armory_dark_eldar);
        orks                = (Button) findViewById(R.id.armory_orks);
        necrons             = (Button) findViewById(R.id.armory_necrons);
        tau                 = (Button) findViewById(R.id.armory_tau);
        tyranids            = (Button) findViewById(R.id.armory_tyranids);
        rulebook            = (Button) findViewById(R.id.armory_rulebook);
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
        rulebook.setTypeface(typeface);
    }

    private void setListeners() {
        spaceMarineForces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Space Marine Forces");
            }
        });
        imperialForces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Imperial Forces");
            }
        });
        chaosSpaceMarines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Chaos Space Marines");
            }
        });
        chaosDaemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Chaos Daemons");
            }
        });
        eldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Eldar");
            }
        });
        darkEldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Dark Eldar");
            }
        });
        orks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Orks");
            }
        });
        necrons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Necrons");
            }
        });
        tau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Tau");
            }
        });
        tyranids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Tyranids");
            }
        });
        rulebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFractionArmory("Rulebook");
            }
        });
    }

    private void openFractionArmory(String fractionName) {
        Intent intent = new Intent(this, ArmorySubmenu.class);

        intent.putExtra("path", "app_data/armory/" + fractionName);
        startActivity(intent);
    }
}
