package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tesseractumstudios.warhammer_artofwar.util.Converter;
import com.tesseractumstudios.warhammer_artofwar.util.font.roboto.TextViewRobotoRegular;

import art.of.war.tesseractumstudios.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChartsAndTablesActivity extends ActionBarActivity {
    private static final String TAG = "ChartsAndTablesActivity";

    private String path;
    private String[] fileNames;
    private LinearLayout buttonsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts_and_tables);

        path = getIntent().getStringExtra("path");

        // Set Back Button
        ImageView backButton = (ImageView) findViewById(R.id.chartsTablesActivity_backImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartsAndTablesActivity.this.finish();
            }
        });

        try {
            fileNames = getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonsList = (LinearLayout) findViewById(R.id.chart_and_tables_buttons_list);

        for ( String name : fileNames ) {
            final FrameLayout submenuButton = (FrameLayout) LayoutInflater.from(this)
                    .inflate(R.layout.armory_submenu_button_2, null);

            final TextViewRobotoRegular submenuButtonTitle = (TextViewRobotoRegular)
                    submenuButton.findViewById(R.id.armorySubmenuButton2_buttonTitle);

            submenuButton.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, Converter.dpTpPx(this, 64)));

            submenuButtonTitle.setText(capitalizeString(cutFileExtension(name)));
            submenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String fileName = getFullFileName((String) submenuButtonTitle.getText());

                    if ( !isFile(fileName) ) {
                        intent = new Intent(ChartsAndTablesActivity.this, RulesSubmenuActivity.class);
                        intent.putExtra("path", path + "/" + fileName);
                    } else {
                        intent = new Intent(ChartsAndTablesActivity.this, Viewer.class);
                        intent.putExtra("path", "android_asset/" + path + "/" + fileName);
                    }
                    startActivity(intent);
                }
            });

            buttonsList.addView(submenuButton);
        }
    }

    private String cutFileExtension(String fullFileName) {
        int indexOfDot = fullFileName.lastIndexOf(".");

        if ( indexOfDot != -1 ) {
            return fullFileName.substring(0, indexOfDot);
        }

        return fullFileName;
    }

    private String getFullFileName(String shortFileName) {
        String buttonText = shortFileName.toLowerCase();

        for ( String fileName : fileNames ) {
            if ( fileName.toLowerCase().contains(buttonText.toLowerCase()) ) {
                return fileName;
            }
        }

        return shortFileName.toLowerCase();
    }

    private boolean isFile(String filename) {
        return filename.contains(".");
    }

    private String capitalizeString(String string) {
        char[]  chars = string.toLowerCase().toCharArray();
        boolean found = false;

        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') {
                found = false;
            }
        }

        return String.valueOf(chars);
    }
}
