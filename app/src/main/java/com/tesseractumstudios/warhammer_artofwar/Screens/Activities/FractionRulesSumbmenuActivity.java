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

import com.tesseractumstudios.warhammer_artofwar.systemmenu.SettingsStorage;
import com.tesseractumstudios.warhammer_artofwar.util.Converter;
import com.tesseractumstudios.warhammer_artofwar.util.font.roboto.TextViewRobotoRegular;

import art.of.war.tesseractumstudios.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FractionRulesSumbmenuActivity extends ActionBarActivity {
    private static final String TAG = "FractionRulesSumbmenu";

    private String path;
    private String parentFolder;
    private String[] fileNames;
    private ArrayList<String> fileNamesArray;
    private LinearLayout buttonsList;
    private FrameLayout rootViewGroup;

    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_rules_submenu);

        path = getIntent().getStringExtra("path");
        parentFolder = prepareParentPrefix(path);

        // Set Title
        TextViewRobotoRegular title = (TextViewRobotoRegular)
                findViewById(R.id.fractionRulesSubmenuActivity_title);
        String[] pathDivided = path.split("/");
        title.setText(pathDivided[pathDivided.length - 1]);

        // Set Back Button
        ImageView backButton = (ImageView) findViewById(R.id.fractionRulesSubmenuActivity_backImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FractionRulesSumbmenuActivity.this.finish();
            }
        });

        try {
            fileNames = getAssets().list(path);
            fileNamesArray = new ArrayList<>(Arrays.asList(fileNames));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonsList = (LinearLayout) findViewById(R.id.fraction_rules_submenu_buttons_list);

        for ( String name : fileNames ) {
            final FrameLayout submenuButton = (FrameLayout) LayoutInflater.from(this)
                    .inflate(R.layout.armory_submenu_button_2, null);

            final TextViewRobotoRegular submenuButtonTitle = (TextViewRobotoRegular)
                    submenuButton.findViewById(R.id.armorySubmenuButton2_buttonTitle);

            submenuButton.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, Converter.dpTpPx(this, 64)));

            submenuButtonTitle.setText(capitalizeString(cutFilePrefixAndSuffix(name)));
            submenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String fileName = getFullFileName((String) submenuButtonTitle.getText());

                    if ( !isFile(fileName) ) {
                        intent = new Intent(FractionRulesSumbmenuActivity.this,
                                FractionRulesSumbmenuActivity.class);
                        intent.putExtra("path", path + "/" + fileName);
                    } else {
                        intent = new Intent(FractionRulesSumbmenuActivity.this, Viewer.class);
                        intent.putExtra("path", "android_asset/" + path + "/" + fileName);
                    }
                    startActivity(intent);
                }
            });

            buttonsList.addView(submenuButton);
        }

        backgroundImageView = (ImageView) findViewById(R.id.fractionRulesSubmenuActivity_backgroundImageView);

//        Button chaosArtifactsSubmenuButton = (Button) LayoutInflater.from(this)
//                .inflate(R.layout.armory_submenu_button, null);
//        chaosArtifactsSubmenuButton.setTypeface(typeface);
//        chaosArtifactsSubmenuButton.setText("Chaos Artefacts");
//        chaosArtifactsSubmenuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        buttonsList.addView(chaosArtifactsSubmenuButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundImageView.setImageDrawable(getResources().getDrawable(
                new SettingsStorage(this).getPreferredSkinResourceId()));
    }

    private int getBackgroundId(String path) {
        String  fractionName = path.replace("app_data/fraction_rules/", "");
        int     indexOfSlash = fractionName.indexOf("/");

        if ( indexOfSlash != -1 ) {
            fractionName = fractionName.substring(0, indexOfSlash);
        }

        fractionName = fractionName.replace(" ", "_").toLowerCase();

        return getResources().getIdentifier("fraction_rules_" + fractionName,
                "drawable", this.getPackageName());
    }

    private String cutFilePrefixAndSuffix(String fullFileName) {
        StringBuilder   fileName    = new StringBuilder(fullFileName);
        int             indexOfDot  = fileName.lastIndexOf(".");

        if ( indexOfDot != -1 ) {
            fileName.delete(indexOfDot, fileName.length());
        }

        return fileName.toString().replace(parentFolder, "");
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

    private String prepareParentPrefix(String path) {
        StringBuilder prefix = new StringBuilder(path);
        int indexOfSlash = prefix.lastIndexOf("/");

        return prefix.substring(indexOfSlash + 1) + " ";
    }
}
