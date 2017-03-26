package com.tesseractumstudios.warhammer_artofwar.Screens.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.Coteaz;
import com.tesseractumstudios.warhammer_artofwar.models.KairosFateweaver;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.SecondRowPower;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;
import com.tesseractumstudios.warhammer_artofwar.models.SpectablePower;
import com.tesseractumstudios.warhammer_artofwar.models.Tigurius;
import com.tesseractumstudios.warhammer_artofwar.tools.InputFilterMinMax;
import com.tesseractumstudios.warhammer_artofwar.tools.PowersGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPsychicDialogFragment extends DialogFragment implements
        RerollPowerToPrimarisDialogFragment.RerollDialogListener,
        TiguriusPowerRerollDialog.TiguriusRerollListener,
        CoteazChooseSourseDialogFragment.CoteazeChooseListener {
    private AddPsychicDialogListener    listener;
    private AlertDialog                 dialog;
    private EditText                    masteryField;
    private EditText                    nameField;
    private Button                      addPsychicPowers, selectPsyker;
    private LinearLayout                powersList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (AddPsychicDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement AddPsychicDialogListener" );
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder             = new AlertDialog.Builder(getActivity());
        LayoutInflater      inflater            = getActivity().getLayoutInflater();
        View                view                = inflater.inflate(
                                                        R.layout.dialog_add_psychic, null);

        selectPsyker        = (Button) view.findViewById(
                                                        R.id.add_psychic_select_psyker);
        addPsychicPowers    = (Button) view.findViewById(R.id.add_psychic_powers);
        nameField           = (EditText) view.findViewById(R.id.add_psychic_name);
        masteryField        = (EditText) view.findViewById(R.id.add_psychic_mastery_lvl);
        powersList          = (LinearLayout) view.findViewById(R.id.add_psychic_powers_list);

        masteryField.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "4")});
        builder.setTitle("Set psyker params or choose one from psykers list");
        builder.setView(view);

        addPsychicPowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                listener.showPowersDialog();
                listener.getTempPsyker().setMasteryLvl(
                        Integer.valueOf(masteryField.getText().toString()));
            }
        });
        selectPsyker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                listener.showAddSpecialPsykerDialog();
            }
        });

        builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String  name;
                int     masteryLvl;
                Psyker  psyker      = listener.getTempPsyker();

                name        = nameField.getText().toString();
                masteryLvl  = Integer.valueOf(masteryField.getText().toString());

                psyker.setName(name);
                psyker.setMasteryLvl(masteryLvl);

                listener.onAddPsychicClick();
            }
        });

        dialog = builder.create();

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Button        buttonOk        = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        final Psyker        psyker          = listener.getTempPsyker();

        buttonOk.setEnabled(false);
        addPsychicPowers.setEnabled(psyker.getMasteryLvl() > 0 && psyker.getPowerCounters() == null);

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonOk.setEnabled(s.length() > 0
                        && masteryField.getText().length() > 0
                        && listener.getTempPsyker().getPowerCounters() != null);
            }
        });
        masteryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonOk.setEnabled(s.length() > 0
                        && nameField.getText().length() > 0
                        && listener.getTempPsyker().getPowerCounters() != null);
                addPsychicPowers.setEnabled(s.length() > 0 && psyker.getPowerCounters() == null);
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onPsykerDialogDismiss();
    }

    @Override
    public void rerollPower(PsykerPower power, Map.Entry<String, ArrayList<PsykerPower>> entry) {
        entry.getValue().remove(power);
        entry.getValue().add(foundPrimaris(
                listener.getAllPowersList().get(entry.getKey())));
        initViews();
    }

    public void initViews() {
        final Button                            button      = dialog.getButton(
                                                                DialogInterface.BUTTON_POSITIVE);
        final Psyker                            psyker      = listener.getTempPsyker();
        Map<String, ArrayList<PsykerPower>>     powers      = psyker.getPowers();
        LayoutInflater                          inflater    = getActivity().getLayoutInflater();

        button.setEnabled(nameField.getText().length() > 0 && masteryField.getText().length() > 0
                && psyker.getPowerCounters() != null);
        addPsychicPowers.setEnabled(masteryField.getText().length() > 0
                && psyker.getPowerCounters() == null);
        masteryField.setFocusable(psyker.getPowerCounters() == null && !(psyker instanceof SpecialPsyker));

        powersList.removeAllViews();
        if ( powers != null ) {
            powersList.setVisibility(View.VISIBLE);

            for ( final Map.Entry<String, ArrayList<PsykerPower>> entry : powers.entrySet() ) {
                final ArrayList<PsykerPower>    schoolPowers    = entry.getValue();
                final PsykerPower               primarisPower   = foundPrimaris(schoolPowers);
                final String                    schoolName      = entry.getKey();
                TextView                        schoolView      = (TextView) inflater.inflate(
                                                                    R.layout.tem_psyker_power_school,
                                                                    null);

                schoolView.setText(capitalize(schoolName));
                powersList.addView(schoolView);

                for ( final PsykerPower power : schoolPowers ) {
                    final LinearLayout    powersLayout    = (LinearLayout) inflater.inflate(
                            R.layout.item_psyker_power, null);
                    TextView        title           = (TextView) powersLayout.findViewById(
                            R.id.item_power_title);
                    TextView        description     = (TextView) powersLayout.findViewById(
                            R.id.item_power_description);
                    TableLayout     table           = (TableLayout) powersLayout.findViewById(
                            R.id.item_power_table);

                    title.setText(power.getTitle());
                    description.setText(power.getDescription());

                    if ( power.isHaveTable() ) {
                        TextView range  = (TextView) powersLayout.findViewById(R.id.item_power_range);
                        TextView s      = (TextView) powersLayout.findViewById(R.id.item_power_s);
                        TextView ap     = (TextView) powersLayout.findViewById(R.id.item_power_ap);
                        TextView type   = (TextView) powersLayout.findViewById(R.id.item_power_type);

                        range.setText(power.getRange());
                        s.setText(power.getS());
                        ap.setText(power.getAp());
                        type.setText(power.getType());

                        table.setVisibility(View.VISIBLE);
                    }
                    if ( power instanceof SecondRowPower) {
                        TableRow secondRow       = (TableRow) powersLayout
                                .findViewById(R.id.item_power_second_row);
                        TextView        headerTitle     = (TextView) powersLayout
                                .findViewById(R.id.item_power_header_title);
                        TextView        rowTitle        = (TextView) powersLayout
                                .findViewById(R.id.item_power_row_title);
                        TextView        secondRowTitle  = (TextView) powersLayout
                                .findViewById(R.id.item_power_row2_title);
                        TextView        range2          = (TextView) powersLayout
                                .findViewById(R.id.item_power_range2);
                        TextView        s2              = (TextView) powersLayout
                                .findViewById(R.id.item_power_s2);
                        TextView        ap2             = (TextView) powersLayout
                                .findViewById(R.id.item_power_ap2);
                        TextView        type2           = (TextView) powersLayout
                                .findViewById(R.id.item_power_type2);
                        SecondRowPower powerSecondRow  = (SecondRowPower) power;

                        rowTitle.setText(powerSecondRow.getRowTitle());
                        secondRowTitle.setText(powerSecondRow.getSecondRowTitle());
                        range2.setText(powerSecondRow.getRange2());
                        s2.setText(powerSecondRow.getS2());
                        ap2.setText(powerSecondRow.getAp2());
                        type2.setText(powerSecondRow.getType2());

                        headerTitle.setVisibility(View.VISIBLE);
                        rowTitle.setVisibility(View.VISIBLE);
                        secondRowTitle.setVisibility(View.VISIBLE);
                        secondRow.setVisibility(View.VISIBLE);
                    }
                    if ( power instanceof SpectablePower) {
                        TableLayout tableLayout = (TableLayout) powersLayout.findViewById(R.id.item_power_spec_table);

                        tableLayout.setVisibility(View.VISIBLE);
                    }

                    powersLayout.setFocusable(true);
                    powersLayout.setClickable(true);
                    if ( psyker instanceof Tigurius ) {
                        powersLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( !((Tigurius) psyker).isRelolled() ) {
                                    DialogFragment fragment = TiguriusPowerRerollDialog.newInstance(
                                            (Tigurius) psyker, AddPsychicDialogFragment.this);

                                    fragment.show(getFragmentManager(), "TiguriusPowerRerollDialog");
                                }
                            }
                        });
                    } else {
                        if ( primarisPower == null ) {
                            powersLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogFragment fragment = RerollPowerToPrimarisDialogFragment
                                            .newInstance(AddPsychicDialogFragment.this, power, entry);

                                    fragment.show(getFragmentManager(), "RerollPowerToPrimaris");
                                }
                            });
                        }
                    }

                    powersList.addView(powersLayout);
                }
            }
        }
    }

    public void onSelectSpecialPsyker() {
        nameField.setText(listener.getTempPsyker().getName());
        masteryField.setText(Integer.toString(listener.getTempPsyker().getMasteryLvl()));

        if ( listener.getTempPsyker() instanceof Coteaz ) {
            CoteazChooseSourseDialogFragment fragment = CoteazChooseSourseDialogFragment
                    .newInstance(this);

            fragment.show(getFragmentManager(), "CoteazChooseSourseDialogFragment");
        } else if ( listener.getTempPsyker() instanceof KairosFateweaver ) {
            KairosFateweaver                    psyker          = (KairosFateweaver)
                                                                    listener.getTempPsyker();
            PowersGenerator                     generator       = PowersGenerator.getInstance();
            String                              schoolName      = getString(R.string._change);
            Map<String, ArrayList<PsykerPower>> generatedPowers;

            psyker.preparePowerCounters();
            generatedPowers = generator.generatePowers(
                    psyker.getMasteryLvl(), psyker.getPowerCounters());
            generatedPowers.put(schoolName, generator.getAllPowers().get(schoolName));
            psyker.setPowers(generatedPowers);

            initViews();
            selectPsyker.setEnabled(false);
            addPsychicPowers.setEnabled(false);
        } else {
            initViews();
            selectPsyker.setEnabled(false);
        }
    }

    private PsykerPower foundPrimaris(ArrayList<PsykerPower> schoolPowers) {
        for ( PsykerPower power : schoolPowers ) {
            if ( power.isPrimaris() ) {
                return power;
            }
        }

        return null;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            imm.hideSoftInputFromWindow(nameField.getWindowToken(), 0);
        } catch (NullPointerException e){
            //NOP
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public void alreadyRerolled() {
        initViews();
    }

    @Override
    public void onCoteazeSourceSelected(boolean isKnown) {
        if ( isKnown ) {
            Map<String, ArrayList<PsykerPower>> knownPowers = new HashMap<>();
            Psyker                              tempPsyker  = listener.getTempPsyker();

            knownPowers.put("Known powers", ((Coteaz) tempPsyker).getKnownPowers());
            tempPsyker.setPowers(knownPowers);
            tempPsyker.setPowerCounters(new HashMap<String, Integer>());
        }

        initViews();
        if ( isKnown ) {
            addPsychicPowers.setEnabled(false);
        }
        selectPsyker.setEnabled(false);
    }

    public interface AddPsychicDialogListener {
        public void                                     onAddPsychicClick();
        public void                                     showPowersDialog();
        public void                                     showAddSpecialPsykerDialog();
        public void                                     onPsykerDialogDismiss();
        public void                                     onPsykerPowersDialogStop();
        public void                                     onPsykerPowerDialogOk();
        public Map<String, ArrayList<PsykerPower>>      getAllPowersList();
        public Psyker                                   getTempPsyker();
        public void                                     setTempPsyker(Psyker psyker);
        public void                                     onSpecialPsykerSelected();
    }
}
