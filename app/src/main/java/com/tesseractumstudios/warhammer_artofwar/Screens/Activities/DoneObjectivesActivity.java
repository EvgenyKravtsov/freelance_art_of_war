package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.adapters.TacticalObjectivesAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.TacticalObjective;
import com.tesseractumstudios.warhammer_artofwar.tools.JsonWorker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DoneObjectivesActivity extends ActionBarActivity {
    private ListView                        objectivesList;
    private TacticalObjectivesAdapter       adapter;
    private ArrayList<TacticalObjective>    doneObjectivesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_objectives);

        readUserDoneObjectivesListFromPreferences();
        findViews();

        adapter = new TacticalObjectivesAdapter(this, doneObjectivesList);
        objectivesList.setAdapter(adapter);
    }

    private void findViews() {
        objectivesList = (ListView) findViewById(R.id.done_objectives_list);
    }

    private void readUserDoneObjectivesListFromPreferences() {
        SharedPreferences   preferences = getSharedPreferences(
                ObjectivesRandomizerActivity.getPreferencesFilename(), MODE_PRIVATE);
        JSONArray           jsonArray;

        try {
            jsonArray           = new JSONArray(preferences.getString(
                    ObjectivesRandomizerActivity.getDoneObjectivesKey(), "[]"));
            doneObjectivesList   = JsonWorker.getObjectivesListFromJson(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            doneObjectivesList = new ArrayList<>();
        }
    }
}
