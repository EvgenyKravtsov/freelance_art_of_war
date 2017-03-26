package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.adapters.SearchResultAdapter;
import com.tesseractumstudios.warhammer_artofwar.tools.FileSearcher;

import java.util.ArrayList;

public class SearchResultActivity extends ActionBarActivity {
    private static final String QUERY_TAG = "query_tag";

    private EditText            searchField;
    private ListView            resultsList;
    private SearchResultAdapter adapter;
    private ArrayList<String>   searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        findViews();

        adapter = new SearchResultAdapter(SearchResultActivity.this, searchResults);
        resultsList.setAdapter(adapter);

        setListeners();

        searchField.setText(getIntent().getStringExtra(QUERY_TAG));
    }

    public static String getQueryTag() {
        return QUERY_TAG;
    }

    private void findViews() {
        searchField = (EditText) findViewById(R.id.search_result_search_field);
        resultsList = (ListView) findViewById(R.id.search_result_list);
    }

    private void setListeners() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( s.length() > 2 ) {
                    searchResults = FileSearcher.searchFiles(
                            getFilesDir().getAbsolutePath() + "/app_data",
                            s.toString());

                    adapter.setSearchResults(searchResults);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyboard();

                return true;
            }
        });

        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, Viewer.class);

                intent.putExtra("path", searchResults.get(position));
                startActivity(intent);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ignored){}
    }
}
