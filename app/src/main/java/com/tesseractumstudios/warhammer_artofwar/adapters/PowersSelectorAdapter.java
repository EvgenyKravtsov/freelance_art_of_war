package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;

import java.util.ArrayList;
import java.util.Map;

public class PowersSelectorAdapter extends BaseAdapter {
    private static final int MAX_LVL_POWER = 4;
    private static final int MAX_LVL_SPEC_POWER = 3;

    private Map<String, Integer>    powerCounters;
    private LayoutInflater          inflater;
    private ArrayList<String>       powers;
    private PowerSelectorListener   listener;
    private Psyker                  psyker;

    public PowersSelectorAdapter(Context context, Map<String, Integer> powerCounters,
                                 Fragment wraper, Psyker psyker) {
        this.inflater           = (LayoutInflater) context.getSystemService(
                                        Context.LAYOUT_INFLATER_SERVICE);
        this.powerCounters      = powerCounters;
        this.powers             = new ArrayList<>(powerCounters.keySet());
        this.listener           = (PowerSelectorListener) wraper;
        this.psyker             = psyker;
    }

    @Override
    public int getCount() {
        return powers.size();
    }

    @Override
    public String getItem(int position) {
        return powers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String    power           = getItem(position);
        Integer         counter         = powerCounters.get(power);
        View            view            = convertView;
        ViewHolder      holder;
        boolean         isTotalNotMax   = listener.getTotalPowerCounter() < psyker.getMasteryLvl();

        if ( view == null ) {
            view    = inflater.inflate(R.layout.item_power_school_selector, parent, false);
            holder  = new ViewHolder();

            holder.power            = (TextView) view.findViewById(R.id.power_school_name);
            holder.powerDecrease    = (Button) view.findViewById(R.id.add_power_decrease_button);
            holder.powerCounter     = (TextView) view.findViewById(R.id.add_power_counter);
            holder.powerIncrease    = (Button) view.findViewById(R.id.add_power_increase_button);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.power.setText(power);
        holder.power.setPaintFlags(holder.power.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.powerCounter.setText(Integer.toString(counter));
        holder.powerDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePower(power, false);
            }
        });
        holder.powerIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePower(power, true);
            }
        });
        if ( psyker instanceof SpecialPsyker
                && !((SpecialPsyker) psyker).getGenerateFrom().contains(power.toLowerCase()) ) {

            holder.powerDecrease.setEnabled(false);
            holder.powerIncrease.setEnabled(false);
        } else {
            holder.power.setPaintFlags(holder.power.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            holder.powerDecrease.setEnabled(counter != 0);
            setPowerIncreaseState(power, holder, isTotalNotMax, counter);
        }

        return view;
    }

    private void setPowerIncreaseState(String power, ViewHolder holder, boolean isTotalNotMax,
                                       Integer counter) {
        if ( "Change".equals(power)
                || "Plague".equals(power)
                || "Nurgle".equals(power)
                || "Tzeentch".equals(power) ) {
            holder.powerIncrease.setEnabled(isTotalNotMax && counter < MAX_LVL_SPEC_POWER);
        } else {
            holder.powerIncrease.setEnabled(isTotalNotMax && counter < MAX_LVL_POWER);
        }

    }

    private void changePower(String key, boolean isIncrement) {
        int count   = powerCounters.get(key);
        int delta   = isIncrement ? 1 : -1;

        powerCounters.put(key, count + delta);
        listener.changeTotalScore(delta);
    }

    private class ViewHolder {
        private TextView power;
        private Button powerDecrease;
        private TextView powerCounter;
        private Button powerIncrease;
    }

    public interface PowerSelectorListener{
        public void changeTotalScore(int delta);
        public int getTotalPowerCounter();
    }
}
