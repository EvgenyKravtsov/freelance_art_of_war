package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;

import java.io.IOException;

public class RulesSubmenuActivity extends ActionBarActivity {
    private ListView                buttonslist;
    private ArrayAdapter<String>    adapter;
    private String[]                filesListArray;
    private String[]                itemsArray;
    private String                  path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_submenu);

        findViews();

        path = getIntent().getStringExtra("path");
        try {
            filesListArray  = getAssets().list(path);
            itemsArray      = prepareItemsArray(filesListArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<>(this, R.layout.item_rules_button_list, itemsArray);

        buttonslist.setFooterDividersEnabled(false);
        buttonslist.setAdapter(adapter);

        buttonslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String fileName = filesListArray[position];

                if ( !isFile(fileName) ) {
                    intent = new Intent(RulesSubmenuActivity.this, RulesSubmenuActivity.class);
                    intent.putExtra("path", path + "/" + fileName);
                } else {
                    intent = new Intent(RulesSubmenuActivity.this, Viewer.class);
                    intent.putExtra("path", "android_asset/" + path + "/" + fileName);
                }
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        buttonslist = (ListView) findViewById(R.id.activity_rules_buttons_list);
    }

    private String[] prepareItemsArray(String[] fileList) {
        int         size    = fileList.length;
        String[]    result  = new String[size];

        for ( int i = 0; i < size; i++ ) {
            result[i] = capitalizeString(cutFileExtension(fileList[i]));
        }

        return result;
    }

    private String cutFileExtension(String fullFileName) {
        int indexOfDot = fullFileName.lastIndexOf(".");

        if ( indexOfDot != -1 ) {
            return fullFileName.substring(0, indexOfDot);
        }

        return fullFileName;
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
