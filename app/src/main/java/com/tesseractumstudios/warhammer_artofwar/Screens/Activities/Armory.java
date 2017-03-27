package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tesseractumstudios.warhammer_artofwar.util.font.roboto.TextViewRobotoRegular;

import art.of.war.tesseractumstudios.R;

public class Armory extends ActionBarActivity {
    private RelativeLayout spaceMarineForces, imperialForces, chaosSpaceMarines, chaosDaemons, eldar,
                            darkEldar, orks, necrons, tau, tyranids, rulebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armory);

        findViews();
        setListeners();
    }

    private void findViews() {
        spaceMarineForces   = (RelativeLayout) findViewById(R.id.armory_space_marine_forces);
        imperialForces      = (RelativeLayout) findViewById(R.id.armory_imperial_forces);
        chaosSpaceMarines   = (RelativeLayout) findViewById(R.id.armory_chaos_space_marines);
        chaosDaemons        = (RelativeLayout) findViewById(R.id.armory_chaos_daemons);
        eldar               = (RelativeLayout) findViewById(R.id.armory_eldar);
        darkEldar           = (RelativeLayout) findViewById(R.id.armory_dark_eldar);
        orks                = (RelativeLayout) findViewById(R.id.armory_orks);
        necrons             = (RelativeLayout) findViewById(R.id.armory_necrons);
        tau                 = (RelativeLayout) findViewById(R.id.armory_tau);
        tyranids            = (RelativeLayout) findViewById(R.id.armory_tyranids);
        rulebook            = (RelativeLayout) findViewById(R.id.armory_rulebook);

        // Set Back Button
        ImageView backButton = (ImageView) findViewById(R.id.armoryActivity_backImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Armory.this.finish();
            }
        });
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
