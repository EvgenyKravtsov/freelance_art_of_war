package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;

public class AddObjectiveDialogFragment extends DialogFragment {
    private AddObjectiveDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (AddObjectiveDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddObjectiveDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string._add_objective);
        builder.setMessage(R.string._add_objective_description);

        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onAddDialogPositiveClick();
            }
        });

        return builder.create();
    }

    public interface AddObjectiveDialogListener {
        public void onAddDialogPositiveClick();
    }
}
