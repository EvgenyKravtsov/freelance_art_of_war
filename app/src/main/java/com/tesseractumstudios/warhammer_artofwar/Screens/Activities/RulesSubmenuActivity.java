package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tesseractumstudios.warhammer_artofwar.systemmenu.SettingsStorage;
import com.tesseractumstudios.warhammer_artofwar.util.Converter;
import com.tesseractumstudios.warhammer_artofwar.util.font.roboto.TextViewRobotoRegular;

import art.of.war.tesseractumstudios.R;

import java.io.IOException;

public class RulesSubmenuActivity extends ActionBarActivity {
    private ListView                buttonslist;
    private ArrayAdapter<String>    adapter;
    private String[]                filesListArray;
    private String[]                itemsArray;
    private String                  path;

    private TextViewRobotoRegular screenTitle;

    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_submenu);

        findViews();

        path = getIntent().getStringExtra("path");
        String[] pathDivided = path.split("/");
        screenTitle.setText(pathDivided[pathDivided.length - 1]);

        try {
            filesListArray  = getAssets().list(path);
            itemsArray      = prepareItemsArray(filesListArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new RulesSubmenuArrayAdapter(this, R.layout.item_rules_button_list, itemsArray);

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

        backgroundImageView = (ImageView) findViewById(R.id.rulesSubmenuActivity_backgroundImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundImageView.setImageDrawable(getResources().getDrawable(
                new SettingsStorage(this).getPreferredSkinResourceId()));
    }

    private void findViews() {
        buttonslist = (ListView) findViewById(R.id.activity_rules_buttons_list);

        screenTitle = (TextViewRobotoRegular) findViewById(R.id.rulesSubmenuActivity_title);


        ImageView backButton = (ImageView) findViewById(R.id.rulesSubmenuActivity_backImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RulesSubmenuActivity.this.finish();
            }
        });
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

    ////

    class RulesSubmenuArrayAdapter extends ArrayAdapter {

        private String[] itemsArray;

        ////

        RulesSubmenuArrayAdapter(Context context, int textResourceId, String[] itemsArray) {
            super(context, textResourceId, itemsArray);
            this.itemsArray = itemsArray;
        }

        ////

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.item_rules_button_list_2, null);
            }

            String itemTitle = itemsArray[position];

            if (itemTitle != null) {
                TextViewRobotoRegular itemTitleTextView = (TextViewRobotoRegular)
                        view.findViewById(R.id.itemRulesButtonList2_buttonTitle);
                itemTitleTextView.setText(itemTitle);

                view.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, Converter.dpTpPx(getContext(), 64)));
            }

            return view;
        }
    }
}
