package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tesseractumstudios.warhammer_artofwar.models.Champion;

import java.util.ArrayList;

import art.of.war.tesseractumstudios.R;

public class ChampionsAdapter extends BaseAdapter {
    private ArrayList<Champion> champions;
    private LayoutInflater inflater;

    public ChampionsAdapter(Context context, ArrayList<Champion> champions) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.champions = champions;
    }

    @Override
    public int getCount() {
        return champions.size();
    }

    @Override
    public Champion getItem(int position) {
        return champions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View        view            = convertView;
        Champion    champion        = getItem(position);
        ViewHolder  holder;

        if ( convertView == null ) {
            view                = inflater.inflate(R.layout.item_champion, parent, false);
            holder              = new ViewHolder();
            holder.name         = (TextView) view.findViewById(R.id.champion_name);
            holder.rewardsList  = (LinearLayout) view.findViewById(R.id.champion_rewards);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(champion.getName());
        holder.rewardsList.removeAllViews();
        for ( String rewardText : champion.getRewards() ) {
            TextView reward = (TextView) inflater.inflate(R.layout.item_champion_reward, null);

            reward.setText(rewardText);
            holder.rewardsList.addView(reward);
        }

        return view;
    }

    private class ViewHolder {
        private TextView name;
        private LinearLayout rewardsList;
    }
}
