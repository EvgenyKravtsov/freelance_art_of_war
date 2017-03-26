package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import art.of.war.tesseractumstudios.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ArmorySubmenu extends ActionBarActivity {
    private static final String TAG = "ArmorySubmenu";

    private String path;
    private String parentFolder;
    private String[] fileNames;
    private ArrayList<String> fileNamesArray;
    private LinearLayout buttonsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armory_submenu);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                                    "fonts/Copperplate Gothic Bold Regular.ttf");

        path = getIntent().getStringExtra("path");
        parentFolder = prepareParentPrefix(path);
        try {
            fileNames = getAssets().list(path);
            fileNamesArray = new ArrayList<>(Arrays.asList(fileNames));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonsList = (LinearLayout) findViewById(R.id.armory_submenu_buttons_list);

        for ( String name : fileNames ) {
            final Button submenuButton = (Button) LayoutInflater.from(this)
                                    .inflate(R.layout.armory_submenu_button, null);

            submenuButton.setTypeface(typeface);
            submenuButton.setText(capitalizeString(cutFilePrefixAndSuffix(name)));
            submenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String fileName = getFullFileName((String) submenuButton.getText());

                    if (!isFile(fileName)) {
                        intent = new Intent(ArmorySubmenu.this, ArmorySubmenu.class);
                        intent.putExtra("path", path + "/" + fileName);
                    } else {
                        intent = new Intent(ArmorySubmenu.this, Viewer.class);
                        intent.putExtra("path", "android_asset/" + path + "/" + fileName);
                    }
                    startActivity(intent);
                }
            });

            buttonsList.addView(submenuButton);
        }
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