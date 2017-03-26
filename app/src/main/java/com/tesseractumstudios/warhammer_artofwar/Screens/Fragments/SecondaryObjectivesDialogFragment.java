package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import art.of.war.tesseractumstudios.R;

public class SecondaryObjectivesDialogFragment extends DialogFragment {
    private static final int OBJECTIVE_BUTTON_PRESSED_COLOR = 0x00e138;

    private SecondaryObjectivesDialogListener   listener;
    private boolean                             secondaryObjective1, secondaryObjective2,
                                                        secondaryObjective3;

    public static SecondaryObjectivesDialogFragment newInstance(boolean secondaryObjective1,
                                                                boolean secondaryObjective2,
                                                                boolean secondaryObjective3) {
        SecondaryObjectivesDialogFragment fragment = new SecondaryObjectivesDialogFragment();

        fragment.secondaryObjective1 = secondaryObjective1;
        fragment.secondaryObjective2 = secondaryObjective2;
        fragment.secondaryObjective3 = secondaryObjective3;

        return fragment;
    }

    public SecondaryObjectivesDialogFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SecondaryObjectivesDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SecondaryObjectivesDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder     = new AlertDialog.Builder(getActivity());
        LayoutInflater      inflater    = getActivity().getLayoutInflater();
        View                view        = inflater.inflate(
                R.layout.dialog_additional_objectives, null);
        Button              objective1  = (Button) view.findViewById(R.id.additional_objectives_1);
        Button              objective2  = (Button) view.findViewById(R.id.additional_objectives_2);
        Button              objective3  = (Button) view.findViewById(R.id.additional_objectives_3);

        if ( listener.isObjectiveDoneNumber(1) ) {
            objective1.getBackground().setColorFilter(
                    new LightingColorFilter(0x000000, OBJECTIVE_BUTTON_PRESSED_COLOR));
        }
        if ( listener.isObjectiveDoneNumber(2) ) {
            objective2.getBackground().setColorFilter(
                    new LightingColorFilter(0x000000, OBJECTIVE_BUTTON_PRESSED_COLOR));
        }
        if ( listener.isObjectiveDoneNumber(3) ) {
            objective3.getBackground().setColorFilter(
                    new LightingColorFilter(0x000000, OBJECTIVE_BUTTON_PRESSED_COLOR));
        }

        objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSecondaryObjectiveClick(1);
                getDialog().dismiss();
            }
        });
        objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSecondaryObjectiveClick(2);
                getDialog().dismiss();
            }
        });
        objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSecondaryObjectiveClick(3);
                getDialog().dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }

    public interface SecondaryObjectivesDialogListener {
        boolean isObjectiveDoneNumber(int objectiveNumber);
        void onSecondaryObjectiveClick(int objectiveNumber);
    }
}
