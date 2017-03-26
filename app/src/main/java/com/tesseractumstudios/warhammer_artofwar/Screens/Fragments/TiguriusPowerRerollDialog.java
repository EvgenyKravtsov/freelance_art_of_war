package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.adapters.TiguriusPowerRerollAdapter;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.Tigurius;

import java.util.ArrayList;

public class TiguriusPowerRerollDialog extends DialogFragment {
    private Tigurius                tigurius;
    private TiguriusRerollListener  listener;
    private ArrayList<PsykerPower>  selectedPowers;

    public static TiguriusPowerRerollDialog  newInstance(Tigurius tigurius, Fragment listener) {
        TiguriusPowerRerollDialog fragment = new TiguriusPowerRerollDialog();

        fragment.tigurius = tigurius;
        fragment.listener = (TiguriusRerollListener) listener;
        fragment.selectedPowers = new ArrayList<>();

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder     = new AlertDialog.Builder(getActivity());
        View                view        = View.inflate(getActivity(),
                                                R.layout.dialog_tigurius_reroll, null);
        ListView            powersList  = (ListView) view.findViewById(
                                                R.id.dialog_tigurius_reroll_list);
        TiguriusPowerRerollAdapter adapter = new TiguriusPowerRerollAdapter(getActivity(), tigurius,
                selectedPowers);

        powersList.setAdapter(adapter);

        builder.setTitle(R.string._select_powers_for_reroll);
        builder.setView(view);

        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tigurius.rerollPowers(selectedPowers);
                listener.alreadyRerolled();
            }
        });

        return builder.create();
    }

    public interface TiguriusRerollListener {
        public void alreadyRerolled();
    }
}
