package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;

import java.util.List;

public class SpecialPsykerAdapter extends BaseAdapter {
    private List<SpecialPsyker> list;
    private LayoutInflater inflater;

    public SpecialPsykerAdapter(Context context, List<SpecialPsyker> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SpecialPsyker getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecialPsyker   psyker  = getItem(position);
        View            view    = convertView;
        ViewHolder      holder;

        if ( view == null ) {
             holder = new ViewHolder();

            view        = inflater.inflate(R.layout.item_special_psyker, parent, false);
            holder.name = (TextView) view.findViewById(R.id.item_special_psyker_name);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(psyker.getName());

        return view;
    }

    private class ViewHolder {
        private TextView name;
    }
}
