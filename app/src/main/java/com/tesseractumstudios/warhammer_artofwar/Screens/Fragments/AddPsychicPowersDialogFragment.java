package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.adapters.PowersSelectorAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;
import com.tesseractumstudios.warhammer_artofwar.tools.PowersGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddPsychicPowersDialogFragment extends DialogFragment implements
        PowersSelectorAdapter.PowerSelectorListener {
    private static final String TAG = "AddPsychicPowersDialogFragment";

    private AddPsychicDialogFragment.AddPsychicDialogListener listener;

    private PowersSelectorAdapter       adapter;
    private AlertDialog                 dialog;
    private ArrayList<String>           avaliablePowers = null;
    private Button                      positiveButton;
    private HashMap<String, Integer>    powerCounters;
    private ListView                    powersList;
    private Psyker                      psyker;
    private int                         totalPowersCounter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (AddPsychicDialogFragment.AddPsychicDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddPsychicDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder     = new AlertDialog.Builder(getActivity());
        LayoutInflater      inflater    = getActivity().getLayoutInflater();
        View                view        = inflater.inflate(R.layout.dialog_add_psychic_powers, null);

        psyker = listener.getTempPsyker();

        findViews(view);
        prepareData();
        initViews();
        adapter = new PowersSelectorAdapter(getActivity(), powerCounters, this,
                psyker);
        powersList.setAdapter(adapter);

        builder.setTitle(getString(R.string._select_powers));
        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                psyker.setPowerCounters(powerCounters);
                listener.onPsykerPowerDialogOk();
            }
        });

        builder.setView(view);

        dialog = builder.create();

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        positiveButton.setEnabled(totalPowersCounter == psyker.getMasteryLvl());
    }

    @Override
    public void onStop() {
        super.onStop();
        listener.onPsykerPowersDialogStop();
    }

    private void initViews() {
        if ( positiveButton != null ) {
            positiveButton.setEnabled(totalPowersCounter == psyker.getMasteryLvl());
        }
    }

    private void prepareData() {
        totalPowersCounter = 0;

        if ( psyker.getPowerCounters() == null ) {
            Set<String> powers = PowersGenerator.getInstance().getAllPowers().keySet();

            powerCounters = new HashMap<>();
            for ( String power : powers ) {
                powerCounters.put(power, 0);
            }
        } else {
            powerCounters = new HashMap<>(psyker.getPowerCounters());
            for (Integer powerCount : powerCounters.values()) {
                totalPowersCounter += powerCount;
            }
        }

        if ( psyker instanceof SpecialPsyker) {
            avaliablePowers = ((SpecialPsyker) psyker).getKnownSchools();
        }
    }

    private void findViews(View view) {
        powersList = (ListView) view.findViewById(R.id.add_psyker_powers_list);
    }

    private void changePower(String key, boolean isIncrement) {
        int count   = powerCounters.get(key);
        int delta   = isIncrement ? 1 : -1;

        powerCounters.put(key, count + delta);
        totalPowersCounter += delta;
        initViews();
    }

    @Override
    public void changeTotalScore(int delta) {
        totalPowersCounter += delta;
        adapter.notifyDataSetChanged();
        initViews();
    }

    @Override
    public int getTotalPowerCounter() {
        return totalPowersCounter;
    }
}
