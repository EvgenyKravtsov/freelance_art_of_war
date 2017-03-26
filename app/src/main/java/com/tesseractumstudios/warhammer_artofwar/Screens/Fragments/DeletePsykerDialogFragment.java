package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;

public class DeletePsykerDialogFragment extends DialogFragment {
    private DeletePsykerInterface listener;
    private Psyker psyker;

    public static DeletePsykerDialogFragment newInstance(Psyker psyker) {
        DeletePsykerDialogFragment fragment = new DeletePsykerDialogFragment();

        fragment.psyker = psyker;

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (DeletePsykerInterface) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string._delete_selected_psyker);

        builder.setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string._delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDeleteOkClick(psyker);
            }
        });

        return builder.create();
    }

    public interface DeletePsykerInterface {
        public void onDeleteOkClick(Psyker psyker);
    }
}
