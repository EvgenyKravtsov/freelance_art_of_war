package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.SecondRowPower;
import com.tesseractumstudios.warhammer_artofwar.models.SpectablePower;

import java.util.ArrayList;
import java.util.Map;

public class PsykersAdapter extends BaseAdapter {
    private ArrayList<Psyker>   psykers;
    private LayoutInflater      inflater;

    public PsykersAdapter(Context context, ArrayList<Psyker> psykers) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.psykers = psykers;
    }

    @Override
    public int getCount() {
        return psykers.size();
    }

    @Override
    public Psyker getItem(int position) {
        return psykers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View        view    = convertView;
        Psyker      psyker  = getItem(position);
        ViewHolder  holder;

        if ( view == null ) {
            view                = inflater.inflate(R.layout.item_psyker, parent, false);
            holder              = new ViewHolder();
            holder.name         = (TextView) view.findViewById(R.id.item_psyker_name);
            holder.masteryLvl   = (TextView) view.findViewById(R.id.item_psyker_mastery_lvl);
            holder.powersList   = (LinearLayout) view.findViewById(R.id.item_psyker_powers);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText("Name: " + psyker.getName());
        holder.masteryLvl.setText("Mastery level: " + Integer.toString(psyker.getMasteryLvl()));
        holder.powersList.removeAllViews();

        for ( Map.Entry<String, ArrayList<PsykerPower>> entry : psyker.getPowers().entrySet() ) {
            ArrayList<PsykerPower>  schoolPowers    = entry.getValue();
            String                  schoolName      = entry.getKey();
            TextView                schoolView      = (TextView) inflater.inflate(
                    R.layout.tem_psyker_power_school, null);

            schoolView.setText(capitalize(schoolName));
            holder.powersList.addView(schoolView);

            for ( PsykerPower power : schoolPowers ) {
                LinearLayout    powersLayout    = (LinearLayout) inflater.inflate(
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
                    TableRow        secondRow       = (TableRow) powersLayout
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

                if ( power instanceof SpectablePower ) {
                    TableLayout tableLayout = (TableLayout) powersLayout.findViewById(R.id.item_power_spec_table);

                    tableLayout.setVisibility(View.VISIBLE);
                }

                holder.powersList.addView(powersLayout);
            }
        }

        return view;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private class ViewHolder {
        private TextView name;
        private TextView masteryLvl;
        private LinearLayout powersList;
    }
}
