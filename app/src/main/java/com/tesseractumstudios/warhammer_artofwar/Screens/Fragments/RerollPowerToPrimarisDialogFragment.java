package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;

import java.util.ArrayList;
import java.util.Map;

public class RerollPowerToPrimarisDialogFragment extends DialogFragment {
    private RerollDialogListener                        listener;
    private PsykerPower                                 power;
    private Map.Entry<String, ArrayList<PsykerPower>>   entry;

    public static RerollPowerToPrimarisDialogFragment newInstance(Fragment caller,
            PsykerPower power,
            Map.Entry<String, ArrayList<PsykerPower>> entry) {
        RerollPowerToPrimarisDialogFragment fragment = new RerollPowerToPrimarisDialogFragment();

        fragment.listener = (RerollDialogListener) caller;
        fragment.power = power;
        fragment.entry = entry;

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string._do_you_want_to_reroll_this_power_to_primaris_);

        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.rerollPower(power, entry);
            }
        });

        return builder.create();
    }

    public interface RerollDialogListener {
        public void rerollPower(PsykerPower power, Map.Entry<String, ArrayList<PsykerPower>> entry);
    }
}
