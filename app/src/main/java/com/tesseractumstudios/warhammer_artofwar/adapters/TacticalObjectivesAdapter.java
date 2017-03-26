package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.TacticalObjective;

import java.util.ArrayList;

public class TacticalObjectivesAdapter extends BaseAdapter {
    private ArrayList<TacticalObjective>    dataList;
    private LayoutInflater                  inflater;

    public TacticalObjectivesAdapter(Context context, ArrayList<TacticalObjective> dataList) {
        this.dataList = dataList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public TacticalObjective getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TacticalObjective   objective   = getItem(position);
        View                view        = convertView;
        ViewHolder          holder;

        if ( view == null ) {
            view                = inflater.inflate(R.layout.item_objective, parent, false);
            holder              = new ViewHolder();
            holder.title        = (TextView) view.findViewById(R.id.objective_title);
            holder.type         = (TextView) view.findViewById(R.id.objective_type);
            holder.description  = (TextView) view.findViewById(R.id.objective_description);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(objective.getTitle());
        holder.type.setText(objective.getType());
        holder.description.setText(objective.getDescription());

        return view;
    }

    public ArrayList<TacticalObjective> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<TacticalObjective> dataList) {
        this.dataList = dataList;
    }

    private class ViewHolder {
        private TextView title;
        private TextView type;
        private TextView description;
    }
}
