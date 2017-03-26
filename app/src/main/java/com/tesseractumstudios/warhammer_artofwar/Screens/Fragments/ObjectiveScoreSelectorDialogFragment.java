package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.TacticalObjective;

public class ObjectiveScoreSelectorDialogFragment extends DialogFragment {
    private ScoreSelectorDialogListener listener;
    private TacticalObjective           objective;
    private int                         objectivePosition;

    public ObjectiveScoreSelectorDialogFragment() {
    }

    public static ObjectiveScoreSelectorDialogFragment newInstance(TacticalObjective objective,
                                                                   int objectivePosition) {
        ObjectiveScoreSelectorDialogFragment fragment = new ObjectiveScoreSelectorDialogFragment();

        fragment.setObjective(objective);
        fragment.objectivePosition = objectivePosition;

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ScoreSelectorDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScoreSelectorDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder     = new AlertDialog.Builder(getActivity());
        LayoutInflater      inflater    = getActivity().getLayoutInflater();
        View                view        = inflater.inflate(
                R.layout.dialog_objective_score_selector, null);
        Button              oneVP       = (Button) view.findViewById(R.id.score_selector_one_vp);
        Button              d3          = (Button) view.findViewById(R.id.score_selector_d3);
        Button              dePlus3     = (Button) view.findViewById(R.id.score_selector_d3_plus_3);
        TextView            description = (TextView) view.findViewById(R.id.score_selector_description);

        oneVP.setEnabled(objective.isOneVp());
        d3.setEnabled(objective.isD3());
        dePlus3.setEnabled(objective.isD3Plus3());
        description.setText(objective.getDescription());
        builder.setTitle(R.string._select_your_reward);

        oneVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOneVPClick(objectivePosition);
                getDialog().dismiss();
            }
        });
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onD3Click(objectivePosition);
                getDialog().dismiss();
            }
        });
        dePlus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onD3Plus3Click(objectivePosition);
                getDialog().dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }

    public void setObjective(TacticalObjective objective) {
        this.objective = objective;
    }

    public interface ScoreSelectorDialogListener {
        public void onOneVPClick(int position);
        public void onD3Click(int position);
        public void onD3Plus3Click(int position);
    }
}
