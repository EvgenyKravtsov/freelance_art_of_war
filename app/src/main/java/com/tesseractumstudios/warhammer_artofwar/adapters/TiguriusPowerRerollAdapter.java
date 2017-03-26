package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.Tigurius;

import java.util.ArrayList;

public class TiguriusPowerRerollAdapter extends BaseAdapter {
    private ArrayList<PsykerPower> psykerPowers;
    private ArrayList<PsykerPower> selectedPowers;
    private LayoutInflater inflater;

    public TiguriusPowerRerollAdapter(Context context, Tigurius tigurius,
                                      ArrayList<PsykerPower> selectedPowers) {
        this.psykerPowers   = tigurius.getAllPowers();
        this.selectedPowers = selectedPowers;
        this.inflater       = (LayoutInflater) context.getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);

        deletePrimaris();
    }

    @Override
    public int getCount() {
        return psykerPowers.size();
    }

    @Override
    public PsykerPower getItem(int position) {
        return psykerPowers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PsykerPower power   = getItem(position);
        View        view    = convertView;
        final ViewHolder  holder;

        if ( view == null ) {
            view            = inflater.inflate(R.layout.item__reroll_power, parent, false);
            holder          = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.item_reroll_power_text);
            holder.checkBox = (CheckBox) view.findViewById(R.id.item_reroll_power_checkbox);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.checkBox.setClickable(false);
        holder.checkBox.setChecked(selectedPowers.contains(power));

        holder.textView.setText(power.getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isContains = selectedPowers.contains(power);

                if ( isContains ) {
                    selectedPowers.remove(power);
                } else {
                    selectedPowers.add(power);
                }

                holder.checkBox.setChecked(!isContains);
            }
        });

        return view;
    }

    private void deletePrimaris() {
        int size = psykerPowers.size();

        for ( int i = size - 1; i >= 0; i-- ) {
            PsykerPower power = psykerPowers.get(i);

            if ( power.isPrimaris() ) {
                psykerPowers.remove(i);
            }
        }
    }

    private class ViewHolder {
        private TextView textView;
        private CheckBox checkBox;
    }
}
