package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.adapters.SpecialPsykerAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;
import com.tesseractumstudios.warhammer_artofwar.tools.AppDelegate;
import com.tesseractumstudios.warhammer_artofwar.tools.JsonWorker;
import com.tesseractumstudios.warhammer_artofwar.tools.PowersGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddSpecialPsykerDialogFragment extends DialogFragment {
    List<SpecialPsyker>                                 specialPsykerList;
    SpecialPsykerAdapter                                adapter;
    AddPsychicDialogFragment.AddPsychicDialogListener   listener;

    public static AddSpecialPsykerDialogFragment newInstance() {
        AddSpecialPsykerDialogFragment fragment = new AddSpecialPsykerDialogFragment();

        try {
            JSONObject powersData = new JSONObject(AppDelegate.getInstance()
                    .readStringFromAssets("app_data/randomizer/psykers/special_psykers.json"));

            fragment.specialPsykerList = JsonWorker.getSpecialPsykersFromJson(powersData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (AddPsychicDialogFragment.AddPsychicDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.getClass().getName()
                    + "Must implement AddPsychicDialogFragment.AddPsychicDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder     builder     = new AlertDialog.Builder(getActivity());
        LayoutInflater          inflater    = getActivity().getLayoutInflater();
        View                    view        = inflater.inflate(R.layout.dialog_add_special_psyker,
                                                    null);
        final ListView          listView    = (ListView) view.findViewById(
                                                    R.id.dialog_add_special_psyker_list);

        adapter     = new SpecialPsykerAdapter(getActivity(), specialPsykerList);

        builder.setTitle(R.string._select_psyker);
        builder.setView(view);
        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpecialPsyker specialPsyker = specialPsykerList.get(position);

                listener.setTempPsyker(specialPsyker);
                listener.onSpecialPsykerSelected();
                dismiss();
            }
        });
        listView.setAdapter(adapter);

        return builder.create();
    }
}
