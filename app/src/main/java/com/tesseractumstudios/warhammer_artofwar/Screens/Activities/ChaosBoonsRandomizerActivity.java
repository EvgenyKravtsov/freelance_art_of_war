package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.CancelDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.EditChampionDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.adapters.ChampionsAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.Champion;
import com.tesseractumstudios.warhammer_artofwar.tools.JsonWorker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ChaosBoonsRandomizerActivity extends ActionBarActivity implements
        EditChampionDialogFragment.AddChampionDialogListener,
        CancelDialogFragment.CancelDialogListener {
    private static final String PREFERENCES_FILENAME        = "filename";
    private static final String CHAMPIONS_KEY               = "champions_key";

    private ArrayList<Champion> champions;
    private ChampionsAdapter    adapter;
    private ImageView           cancelButton, addButton;
    private ListView            list;
    ImageView question_mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaos_boons_randomizer);

        findViews();
        setOnclickListeners();

        champions = new ArrayList<>();
        loadChampions();
        adapter = new ChampionsAdapter(this, champions);
        list.setAdapter(adapter);
    }

    @Override
    public void onAddButtonClick(Champion champion) {
        champions.add(champion);
        saveChampions();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditChampionClick(Champion champion) {
        adapter.notifyDataSetChanged();
        saveChampions();
    }

    @Override
    public void onClearListClick() {
        champions.clear();
        adapter.notifyDataSetChanged();
        saveChampions();
    }

    private void findViews() {
        addButton       = (ImageView) findViewById(R.id.chaos_boons_add);
        cancelButton    = (ImageView) findViewById(R.id.chaos_boons_cancel_button);
        list            = (ListView) findViewById(R.id.chaos_boons_list);
        question_mark =(ImageView) findViewById(R.id.question_mark);
    }

    private void setOnclickListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = EditChampionDialogFragment.newInstance(null);

                dialog.show(getFragmentManager(), "tag");
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragment dialog = EditChampionDialogFragment.newInstance(
                        champions.get(position));

                dialog.show(getFragmentManager(), "tag");
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = CancelDialogFragment.newInstance(
                        R.string._delete_all_your_champions_);

                dialog.show(getFragmentManager(), "tag");
            }
        });

        question_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });
    }

    private void saveChampions() {
        JSONArray                   championsList   = JsonWorker.getJsonArrayChampions(champions);
        SharedPreferences.Editor    editor          = getSharedPreferences(PREFERENCES_FILENAME,
                                                        MODE_PRIVATE).edit();

        editor.putString(CHAMPIONS_KEY, championsList.toString());
        editor.apply();
    }

    private void loadChampions() {
        try {
            SharedPreferences   preferences     = getSharedPreferences(PREFERENCES_FILENAME,
                                                        MODE_PRIVATE);
            JSONArray           jsonChampions   = new JSONArray(preferences.getString(CHAMPIONS_KEY,
                                                        "[]"));

            champions                           = JsonWorker.getChampionsListFromJson(jsonChampions);
        } catch (JSONException e) {
            e.printStackTrace();
            champions = new ArrayList<>();
        }
    }

    public void showDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(ChaosBoonsRandomizerActivity.this);
        builder.setMessage("To get a Chaos Boon for your Champion, press the “+” icon in the upper right corner, then input a name. Your champion will be created.\n" +
                "\n" +
                "Right next to your champion’s name you will see another “+” icon, which will randomly generate a Chaos Boon, and “x” icon, which will delete the Chaos boon.")
                .setTitle(R.string._manual)
                .setCancelable(false)
                .setNegativeButton(R.string._close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }
}
