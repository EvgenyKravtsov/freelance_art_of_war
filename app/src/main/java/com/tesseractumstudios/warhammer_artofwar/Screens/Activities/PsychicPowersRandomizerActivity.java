package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.AddPsychicDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.AddPsychicPowersDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.AddSpecialPsykerDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.DeleteAllPsykersDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.DeletePsykerDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.Screens.Fragments.ManualDialogFragment;
import com.tesseractumstudios.warhammer_artofwar.adapters.PsykersAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.Coteaz;
import com.tesseractumstudios.warhammer_artofwar.models.KairosFateweaver;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;
import com.tesseractumstudios.warhammer_artofwar.tools.AppDelegate;
import com.tesseractumstudios.warhammer_artofwar.tools.JsonWorker;
import com.tesseractumstudios.warhammer_artofwar.tools.PowersGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PsychicPowersRandomizerActivity extends ActionBarActivity implements
        AddPsychicDialogFragment.AddPsychicDialogListener,
        DeletePsykerDialogFragment.DeletePsykerInterface,
        DeleteAllPsykersDialogFragment.DeleteAllPsykersListener {
    private static final String TAG                         = "PsychicPowersRandomizerActivity";
    private static final String PREFERENCES_FILENAME        = "filename";
    private static final String PSYKERS_KEY                 = "psykers";

    private Button                                  addButton;
    private ImageView                               manualButton;
    private ImageView                               deleteAllPsykersButton;
    private ListView                                psychicsList;
    private Psyker                                  tempPsyker;
    private ArrayList<Psyker>                       psykers;
    private PsykersAdapter                          adapter;
    private Map<String, ArrayList<PsykerPower>>     allPowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychic_powers_randomizer);

        findViews();
        prepareData();
        adapter = new PsykersAdapter(this, psykers);
        psychicsList.setAdapter(adapter);
        psychicsList.setFooterDividersEnabled(false);
        setOnclickListeners();
    }

    @Override
    public void showPowersDialog() {
        DialogFragment dialog = new AddPsychicPowersDialogFragment();

        dialog.show(getFragmentManager(), "tag2");
    }

    @Override
    public void onAddPsychicClick() {
        psykers.add(tempPsyker);
        tempPsyker = null;
        savePsykers();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPsykerDialogDismiss() {
        tempPsyker = null;
    }

    @Override
    public void onPsykerPowersDialogStop() {
        AddPsychicDialogFragment fragment = (AddPsychicDialogFragment) getFragmentManager()
                                                    .findFragmentByTag("AddPsychicDialogFragment");
        fragment.initViews();
    }

    @Override
    public void onPsykerPowerDialogOk() {
        generatePsykerPowers();
    }

    @Override
    public Map<String, ArrayList<PsykerPower>> getAllPowersList() {
        return allPowers;
    }

    @Override
    public void onDeleteOkClick(Psyker psyker) {
        psykers.remove(psyker);
        savePsykers();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAddSpecialPsykerDialog() {
        DialogFragment dialog = AddSpecialPsykerDialogFragment.newInstance();

        dialog.show(getFragmentManager(), "AddSpecialPsykerDialogFragment");
    }

    @Override
    public Psyker getTempPsyker() {
        return tempPsyker;
    }

    @Override
    public void setTempPsyker(Psyker psyker) {
        tempPsyker = psyker;
    }

    @Override
    public void onSpecialPsykerSelected() {
        AddPsychicDialogFragment fragment = (AddPsychicDialogFragment) getFragmentManager()
                                            .findFragmentByTag("AddPsychicDialogFragment");

        fragment.onSelectSpecialPsyker();
    }

    @Override
    public void onDeleteAllClick() {
        psykers.clear();
        savePsykers();
        adapter.notifyDataSetChanged();
    }

    private void findViews() {
        addButton               = (Button) findViewById(R.id.psychic_powers_add_button);
        deleteAllPsykersButton  = (ImageView) findViewById(R.id.psychic_powers_delete_all);
        manualButton            = (ImageView) findViewById(R.id.psychic_powers_manual_button);
        psychicsList            = (ListView) findViewById(R.id.psychic_powers_list);
    }

    private void prepareData() {
        try {
            JSONObject powersData = new JSONObject(AppDelegate.getInstance()
                    .readStringFromAssets("app_data/randomizer/powers.json"));

            allPowers = JsonWorker.getPowersMapFromJson(powersData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loadPsykers();
    }

    private void setOnclickListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new AddPsychicDialogFragment();

                dialog.show(getFragmentManager(), "AddPsychicDialogFragment");
                tempPsyker = new Psyker();
            }
        });
        psychicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragment dialog = DeletePsykerDialogFragment
                        .newInstance(adapter.getItem(position));

                dialog.show(getFragmentManager(), "tag");
            }
        });
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new ManualDialogFragment();

                dialog.show(getFragmentManager(), "ManualDialogFragment");
            }
        });
        deleteAllPsykersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new DeleteAllPsykersDialogFragment();

                dialog.show(getFragmentManager(), "DeleteAllPsykersDialogFragment");
            }
        });
    }

    private void savePsykers() {
        JSONArray                   jsonPsykers = JsonWorker.getJsonArrayPsykers(psykers);
        SharedPreferences.Editor    editor      = getSharedPreferences(PREFERENCES_FILENAME,
                                                        MODE_PRIVATE).edit();

        editor.putString(PSYKERS_KEY, jsonPsykers.toString());
        editor.apply();
    }

    private void loadPsykers() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
        JSONArray jsonPsykers;

        try {
            jsonPsykers = new JSONArray(preferences.getString(PSYKERS_KEY, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
            psykers = new ArrayList<>();

            return;
        }

        if ( jsonPsykers.length() == 0 ) {
            psykers = new ArrayList<>();
        } else {
            psykers = JsonWorker.getPsykersFromJson(jsonPsykers);
        }
    }

    private int getRandomNumber(int limit) {
        return ((Double) (Math.random() * limit)).intValue();
    }

    private void generatePsykerPowers() {
        PowersGenerator generator = PowersGenerator.getInstance();
        Map<String, ArrayList<PsykerPower>> generatedPowers = generator
                .generatePowers(tempPsyker.getMasteryLvl(), tempPsyker.getPowerCounters());

        if ( getString(R.string._mephiston__lord_of_death).equals(tempPsyker.getName()) ) {
            generatedPowers.put("Known powers", ((SpecialPsyker)tempPsyker).getKnownPowers());
        }

        tempPsyker.setPowers(generatedPowers);
    }
}
