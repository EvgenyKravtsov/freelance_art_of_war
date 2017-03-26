package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;

public class DeleteAllPsykersDialogFragment extends DialogFragment {
    private DeleteAllPsykersListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (DeleteAllPsykersListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName()
                    + "must implement DeleteAllPsykersListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string._delete_all_psykers);

        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string._delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDeleteAllClick();
            }
        });

        return builder.create();
    }

    public interface DeleteAllPsykersListener {
        public void onDeleteAllClick();
    }
}
