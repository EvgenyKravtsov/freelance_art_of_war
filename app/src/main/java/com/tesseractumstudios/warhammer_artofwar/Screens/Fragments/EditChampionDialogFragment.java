package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.Champion;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import static art.of.war.tesseractumstudios.R.id.add_champion_reward;
import static art.of.war.tesseractumstudios.R.id.useLogo;

public class EditChampionDialogFragment extends DialogFragment {
    private static final int MAX_TRY_COUNT = 36;
    private AddChampionDialogListener   listener;
    private Champion                    champion;
    private EditText                    nameField;
    private ArrayAdapter<String>        adapter;
    private String[]                    allRewards;
    private boolean                     editMode;

    public static EditChampionDialogFragment newInstance(Champion champion) {
        EditChampionDialogFragment fragment = new EditChampionDialogFragment();

        if ( champion != null ) {
            fragment.champion = champion;
            fragment.editMode = true;
        } else {
            fragment.champion = new Champion("");
            fragment.editMode = false;
        }

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (AddChampionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddChampionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder         = new AlertDialog.Builder(getActivity());
        LayoutInflater      inflater        = getActivity().getLayoutInflater();
        View                view            = inflater.inflate(R.layout.dialog_add_champion, null);
        final ImageView   addReward         = (ImageView) view.findViewById(add_champion_reward);
        final ImageView   deleteReward      = (ImageView) view.findViewById(R.id.delete_last_champion_reward);
        final ListView      rewards         = (ListView) view.findViewById(
                                                    R.id.add_champion_rewards_list);

        nameField                           = (EditText) view.findViewById(R.id.add_champion_name);
        allRewards                          = getActivity().getResources().getStringArray(
                                                    R.array.chaos_boons_rewards);

        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        if ( !champion.getName().equals("") ) {
            nameField.setText(champion.getName());
        }

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                champion.getRewards());
        rewards.setFooterDividersEnabled(false);
        rewards.setAdapter(adapter);

        addReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reward = allRewards[getRandomNumber(allRewards.length)];
                int tryCounter = 0;

                for ( ; isRewardInList(reward) && tryCounter < MAX_TRY_COUNT;
                      tryCounter++ ) {
                    reward = allRewards[getRandomNumber(allRewards.length)];
                }

                if ( isRewardInList(reward) ) {
                    return;
                }

                champion.getRewards().add(reward);
                adapter.notifyDataSetChanged();
            }
        });
        deleteReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rewardCount = champion.getRewards().size();

                if ( rewardCount > 0 ) {
                    champion.getRewards().remove(rewardCount - 1);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setView(view);

        return builder.create();
    }

    private boolean isRewardInList(String newReward) {
        ArrayList<String> rewards = champion.getRewards();

        for ( String reward : rewards ) {
            if ( reward.equals(newReward) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog dialog = (AlertDialog) getDialog();

        if ( dialog != null ) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( "".equals(nameField.getText().toString()) ) {
                        Toast.makeText(getActivity(), "Can't create champion without name",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    champion.setName(nameField.getText().toString());

                    if ( editMode ) {
                        listener.onEditChampionClick(champion);
                    } else {
                        listener.onAddButtonClick(champion);
                    }
                    dialog.dismiss();
                }
            });
        }
    }

    private int getRandomNumber(int limit) {
        int number = ((Double) (Math.random() * limit)).intValue();

        return number;
    }

    public interface AddChampionDialogListener {
        public void onAddButtonClick(Champion champion);
        public void onEditChampionClick(Champion champion);
    }
}
