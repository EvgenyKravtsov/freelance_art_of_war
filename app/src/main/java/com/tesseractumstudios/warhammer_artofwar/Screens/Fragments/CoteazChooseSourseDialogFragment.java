package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;

public class CoteazChooseSourseDialogFragment extends DialogFragment {
    CoteazeChooseListener listener;

    public static CoteazChooseSourseDialogFragment newInstance(Fragment caller) {
        CoteazChooseSourseDialogFragment fragment = new CoteazChooseSourseDialogFragment();

        fragment.listener = (CoteazeChooseListener) caller;

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string._select__coteaze_power_source);

        builder.setNeutralButton(R.string._known_powers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCoteazeSourceSelected(true);
            }
        });
        builder.setPositiveButton(R.string._generate_powers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCoteazeSourceSelected(false);
            }
        });

        return builder.create();
    }

    public interface CoteazeChooseListener {
        public void onCoteazeSourceSelected(boolean isKnown);
    }
}
