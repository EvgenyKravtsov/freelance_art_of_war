package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import art.of.war.tesseractumstudios.R;


public class CancelDialogFragment extends DialogFragment {
    private CancelDialogListener    listener;
    private int                     messageStringIdsageId;

   public static CancelDialogFragment newInstance(int stringId) {
       CancelDialogFragment fragment = new CancelDialogFragment();

       fragment.messageStringIdsageId = stringId;

       return fragment;
   }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (CancelDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CancelObjectivesDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(messageStringIdsageId);
        builder.setPositiveButton(R.string._clear, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClearListClick();
            }
        });

        return builder.create();
    }

    public interface CancelDialogListener {
        public void onClearListClick();
    }
}
