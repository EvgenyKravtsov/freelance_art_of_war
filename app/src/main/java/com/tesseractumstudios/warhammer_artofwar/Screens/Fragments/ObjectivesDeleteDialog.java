package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;

public class ObjectivesDeleteDialog extends DialogFragment {
    private ObjectiveDeleteDialogListener holder;
    private int position;


    public static ObjectivesDeleteDialog newInstance(int position) {
        ObjectivesDeleteDialog dialog = new ObjectivesDeleteDialog();

        dialog.position = position;

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            holder = (ObjectiveDeleteDialogListener) activity;
        } catch ( ClassCastException e ) {
            throw new ClassCastException(activity.getLocalClassName()
                    + " must implement ObjectiveDeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string._do_you_want_to_delete_this_objective_);
        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string._delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                holder.onObjectiveDeleteClick(position);
            }
        });

        return builder.create();
    }

    public interface ObjectiveDeleteDialogListener {
        void onObjectiveDeleteClick(int position);
    }
}