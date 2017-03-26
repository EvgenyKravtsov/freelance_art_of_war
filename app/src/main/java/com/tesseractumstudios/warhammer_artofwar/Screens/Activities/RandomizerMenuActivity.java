package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import art.of.war.tesseractumstudios.R;

public class RandomizerMenuActivity extends ActionBarActivity {
    Button psychicPowers, chaosBoons, tacticalObjectives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer_menu);

        findViews();
        adjustFonts();
        setListeners();
    }

    private void findViews() {
        psychicPowers       = (Button) findViewById(R.id.randomizer_menu_psychic_powers_randomizer);
        chaosBoons          = (Button) findViewById(R.id.randomizer_menu_chaos__boons_randomizer);
        tacticalObjectives  = (Button) findViewById(
                R.id.randomizer_menu_tactical_objectives_randomizer);
    }

    private void adjustFonts() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fonts/Copperplate Gothic Bold Regular.ttf");

        psychicPowers.setTypeface(typeface);
        chaosBoons.setTypeface(typeface);
        tacticalObjectives.setTypeface(typeface);
    }

    private void setListeners() {
        tacticalObjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomizerMenuActivity.this, ObjectivesRandomizerActivity.class);

                startActivity(intent);
            }
        });
        chaosBoons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomizerMenuActivity.this, ChaosBoonsRandomizerActivity.class);

                startActivity(intent);
            }
        });
        psychicPowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomizerMenuActivity.this, PsychicPowersRandomizerActivity.class);

                startActivity(intent);
            }
        });
    }
}
