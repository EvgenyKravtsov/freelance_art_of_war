package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.AddObjectiveDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.CancelDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.ObjectiveScoreSelectorDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.ObjectivesDeleteDialog;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.SecondaryObjectivesDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.adapters.TacticalObjectivesAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.TacticalObjective;
import com.tesseractumstudios.warhammer_artofwar.tools.JsonWorker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ObjectivesRandomizerActivity extends ActionBarActivity implements
        AddObjectiveDialogFragment.AddObjectiveDialogListener,
        CancelDialogFragment.CancelDialogListener,
        ObjectiveScoreSelectorDialogFragment.ScoreSelectorDialogListener,
        SecondaryObjectivesDialogFragment.SecondaryObjectivesDialogListener,
        ObjectivesDeleteDialog.ObjectiveDeleteDialogListener {
    private static final String TAG                         = "RandomizerActivity";
    private static final String ADD_DIALOG_TAG              = "AddDialog";
    private static final String CANCEL_DIALOG_TAG           = "CancelDialog";
    private static final String DELETE_DIALOG_TAG           = "DeleteDialog";
    private static final String VP_SELECTOR_DIALOG_TAG      = "VpSelectorDialogTag";
    private static final String SECONDARY_OBJECTIVES_TAG    = "SecondaryObjectivesDialogTag";
    private static final String PREFERENCES_FILENAME        = "filename";
    private static final String OBJECTIVES_KEY              = "objectives";
    private static final String DONE_OBJECTIVES_KEY         = "doneObjectives";
    private static final String DELETED_OBJECTIVES_KEY      = "deletedObjectives";
    private static final String SCORE_KEY                   = "score";
    private static final String SECONDARY_1_KEY             = "secondary 1";
    private static final String SECONDARY_2_KEY             = "secondary 2";
    private static final String SECONDARY_3_KEY             = "secondary 3";
    private static final int CURRENT_OBJECTIVE_STATUS_CODE  = 0;
    private static final int DELETED_OBJECTIVE_STATUS_CODE  = 1;
    private static final int DONE_OBJECTIVE_STATUS_CODE     = 2;

    private ToggleButton                    currentObjectives, doneObjectives, deletedObjectives;
    private ImageView                       cancelAll, addObjective, defaultOptions;
    private ListView                        objectivesList;
    private ArrayList<TacticalObjective>    userObjectiveList, allObjectiveList, doneObjectiveList,
                                            deletedObjectiveList;
    private TacticalObjectivesAdapter       adapter;
    private TextView                        scoreView;
    private Integer                         score;
    private boolean                         secondaryObjective1, secondaryObjective2,
                                            secondaryObjective3;
    private int                             screenObjectiveStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives_randomizer);

        prepareListAllObjectives();
        readUserObjectivesListFromPreferences();

        findViews();
        setListeners();
        initViews();

        adapter = new TacticalObjectivesAdapter(this, userObjectiveList);
        objectivesList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onAddDialogPositiveClick() {
        int                 objectivesSize  = allObjectiveList.size();
        TacticalObjective   randomObjective = allObjectiveList.get(getRandomNumber(objectivesSize));

        while ( userObjectiveList.contains(randomObjective)
                || doneObjectiveList.contains(randomObjective)
                || deletedObjectiveList.contains(randomObjective) ) {
            randomObjective = allObjectiveList.get(getRandomNumber(objectivesSize));
        }

        userObjectiveList.add(randomObjective);
        adapter.notifyDataSetChanged();
        saveSelectedObjectives();
    }

    @Override
    public void onObjectiveDeleteClick(int position) {
        deletedObjectiveList.add(userObjectiveList.get(position));
        userObjectiveList.remove(position);

        saveSelectedObjectives();

        adapter.notifyDataSetChanged();
        initViews();
    }

    @Override
    public void onClearListClick() {
        userObjectiveList.clear();
        doneObjectiveList.clear();
        score = 0;
        adapter.notifyDataSetChanged();
        secondaryObjective1 = false;
        secondaryObjective2 = false;
        secondaryObjective3 = false;
        saveSelectedObjectives();
        initViews();
    }

    @Override
    public void onOneVPClick(int position) {
        score += 1;
        doneObjectiveList.add(userObjectiveList.get(position));
        userObjectiveList.remove(position);
        saveSelectedObjectives();
        adapter.notifyDataSetChanged();
        initViews();
    }

    @Override
    public void onD3Click(int position) {
        score += getRandomNumber(3) + 1;
        doneObjectiveList.add(userObjectiveList.get(position));
        userObjectiveList.remove(position);
        saveSelectedObjectives();
        adapter.notifyDataSetChanged();
        initViews();
    }

    @Override
    public void onD3Plus3Click(int position) {
        score += getRandomNumber(3) + 4;
        doneObjectiveList.add(userObjectiveList.get(position));
        userObjectiveList.remove(position);
        saveSelectedObjectives();
        adapter.notifyDataSetChanged();
        initViews();
    }

    @Override
    public void onSecondaryObjectiveClick(int objectiveNumber) {
        if ( objectiveNumber == 1 ) {
            if ( secondaryObjective1 ) {
                score--;
            } else {
                score++;
            }
            secondaryObjective1 = !secondaryObjective1;
        } else if ( objectiveNumber == 2 ) {
            if ( secondaryObjective2 ) {
                score--;
            } else {
                score++;
            }
            secondaryObjective2 = !secondaryObjective2;
        } else if ( objectiveNumber == 3 ) {
            if ( secondaryObjective3 ) {
                score--;
            } else {
                score++;
            }
            secondaryObjective3 = !secondaryObjective3;
        }

        saveSelectedObjectives();
        initViews();
    }

    @Override
    public boolean isObjectiveDoneNumber(int objectiveNumber) {
        if ( objectiveNumber == 1 ) {
            return secondaryObjective1;
        } else if ( objectiveNumber == 2 ) {
            return secondaryObjective2;
        } else if ( objectiveNumber == 3 ) {
            return secondaryObjective3;
        }

        return false;
    }

    public static String getDoneObjectivesKey() {
        return DONE_OBJECTIVES_KEY;
    }

    public static String getPreferencesFilename() {
        return PREFERENCES_FILENAME;
    }

    private void findViews() {
        currentObjectives   = (ToggleButton) findViewById(R.id.objectives_randomizer_curent_objectives);
        deletedObjectives   = (ToggleButton) findViewById(R.id.objecives_randomizer_deleted_objectives);
        doneObjectives      = (ToggleButton) findViewById(R.id.objecives_randomizer_done_objectives);
        cancelAll           = (ImageView) findViewById(R.id.objectives_randomizer_cancel_all_button);
        defaultOptions      = (ImageView) findViewById(R.id.objecives_randomizer_default_options);
        addObjective        = (ImageView) findViewById(R.id.objecives_randomizer_add_objective);
        objectivesList      = (ListView) findViewById(R.id.objecives_randomizer_objectives_list);
        scoreView           = (TextView) findViewById(R.id.randomizer_score);
    }

    private void setListeners() {
        addObjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddObjectiveDialog();
            }
        });
        cancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = CancelDialogFragment.newInstance(
                        R.string._clear_your_objectives_list_);

                dialog.show(getFragmentManager(), CANCEL_DIALOG_TAG);
            }
        });
        objectivesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( screenObjectiveStatus != CURRENT_OBJECTIVE_STATUS_CODE ) {
                    return;
                }

                DialogFragment dialog = ObjectiveScoreSelectorDialogFragment
                        .newInstance(userObjectiveList.get(position), position);

                dialog.show(getFragmentManager(), VP_SELECTOR_DIALOG_TAG);
            }
        });
        objectivesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if ( screenObjectiveStatus != CURRENT_OBJECTIVE_STATUS_CODE ) {
                    return true;
                }

                DialogFragment dialog = ObjectivesDeleteDialog.newInstance(position);

                dialog.show(getFragmentManager(), DELETE_DIALOG_TAG);
                return true;
            }
        });
        defaultOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = SecondaryObjectivesDialogFragment
                        .newInstance(secondaryObjective1, secondaryObjective2, secondaryObjective3);

                dialog.show(getFragmentManager(), SECONDARY_OBJECTIVES_TAG);
            }
        });
        doneObjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDataList(doneObjectiveList);
                adapter.notifyDataSetChanged();
                screenObjectiveStatus = DONE_OBJECTIVE_STATUS_CODE;
                setButtonsStatus();
            }
        });
        deletedObjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDataList(deletedObjectiveList);
                adapter.notifyDataSetChanged();
                screenObjectiveStatus = DELETED_OBJECTIVE_STATUS_CODE;
                setButtonsStatus();
            }
        });
        currentObjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDataList(userObjectiveList);
                adapter.notifyDataSetChanged();
                screenObjectiveStatus = CURRENT_OBJECTIVE_STATUS_CODE;
                setButtonsStatus();
            }
        });
    }

    private void initViews() {
        currentObjectives.setChecked(true);
        scoreView.setText(getString(R.string._score_) + " " + score);
    }

    private void prepareListAllObjectives() {
        try {
            JSONArray jsonObjectives = new JSONArray(readJsonStringFromAssets());

            allObjectiveList = JsonWorker.getObjectivesListFromJson(jsonObjectives);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readJsonStringFromAssets() {
        String jsonString;

        try {
            InputStream is      = getAssets().open("app_data/randomizer/tactical_objectives.json");
            int         size    = is.available();
            byte[]      buffer  = new byte[size];

            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

            return "[]";
        }

        return jsonString;
    }

    private void readUserObjectivesListFromPreferences() {
        SharedPreferences   preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
        JSONArray           jsonArray;

        try {
            jsonArray               = new JSONArray(preferences.getString(OBJECTIVES_KEY, "[]"));
            userObjectiveList       = JsonWorker.getObjectivesListFromJson(jsonArray);
            jsonArray               = new JSONArray(preferences.getString(DONE_OBJECTIVES_KEY, "[]"));
            doneObjectiveList       = JsonWorker.getObjectivesListFromJson(jsonArray);
            jsonArray               = new JSONArray(preferences.getString(DELETED_OBJECTIVES_KEY, "[]"));
            deletedObjectiveList    = JsonWorker.getObjectivesListFromJson(jsonArray);
            score                   = preferences.getInt(SCORE_KEY, 0);
            secondaryObjective1     = preferences.getBoolean(SECONDARY_1_KEY, false);
            secondaryObjective2     = preferences.getBoolean(SECONDARY_2_KEY, false);
            secondaryObjective3     = preferences.getBoolean(SECONDARY_3_KEY, false);
        } catch (JSONException e) {
            e.printStackTrace();
            userObjectiveList       = new ArrayList<>();
            doneObjectiveList       = new ArrayList<>();
            deletedObjectiveList    = new ArrayList<>();
            score = 0;
        }
    }

    private void saveSelectedObjectives() {
        JSONArray           jsonObjectives          = JsonWorker.getJsonArrayObjectives(
                                                        userObjectiveList);
        JSONArray           jsonDoneObjectives      = JsonWorker.getJsonArrayObjectives(
                                                        doneObjectiveList);
        JSONArray           jsonDeletedObjectives   = JsonWorker.getJsonArrayObjectives(
                                                        deletedObjectiveList);
        SharedPreferences   preferences             = getSharedPreferences(PREFERENCES_FILENAME,
                                                                    MODE_PRIVATE);
        Editor              editor                  = preferences.edit();

        editor.putString(OBJECTIVES_KEY, jsonObjectives.toString());
        editor.putString(DONE_OBJECTIVES_KEY, jsonDoneObjectives.toString());
        editor.putString(DELETED_OBJECTIVES_KEY, jsonDeletedObjectives.toString());
        editor.putInt(SCORE_KEY, score);
        editor.putBoolean(SECONDARY_1_KEY, secondaryObjective1);
        editor.putBoolean(SECONDARY_2_KEY, secondaryObjective2);
        editor.putBoolean(SECONDARY_3_KEY, secondaryObjective3);
        editor.apply();
    }

    private int getRandomNumber(int limit) {
        return ((Double) (Math.random() * limit)).intValue();
    }

    private void createAddObjectiveDialog() {
        DialogFragment dialog = new AddObjectiveDialogFragment();

        dialog.show(getFragmentManager(), ADD_DIALOG_TAG);
    }

    private void setButtonsStatus() {
        currentObjectives.setChecked(screenObjectiveStatus == CURRENT_OBJECTIVE_STATUS_CODE);
        deletedObjectives.setChecked(screenObjectiveStatus == DELETED_OBJECTIVE_STATUS_CODE);
        doneObjectives.setChecked(screenObjectiveStatus == DONE_OBJECTIVE_STATUS_CODE);
    }
}
