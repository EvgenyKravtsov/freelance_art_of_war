package com.tesseractumstudios.warhammer_artofwar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import art.of.war.tesseractumstudios.R;

import java.io.File;
import java.util.ArrayList;

public class SearchResultAdapter extends BaseAdapter {
    ArrayList<String>   searchResults;
    LayoutInflater      inflater;

    public SearchResultAdapter(Context context, ArrayList<String> searchResults) {
        this.inflater       = (LayoutInflater) context.getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);
        this.searchResults  = searchResults;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public String getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String      fileName    = getItem(position);
        View        view        = convertView;
        ViewHolder  holder;

        if ( view == null ) {
            view        = inflater.inflate(R.layout.item_rules_button_list, parent, false);
            holder      = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.item_rules_button_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(cutFilePath(fileName));

        return view;
    }

    public void setSearchResults(ArrayList<String> searchResults) {
        this.searchResults = searchResults;
    }

    private String cutFilePath(String fileName) {
        int indexOfSlash    = fileName.lastIndexOf(File.separator);
        int indexOfDot      = fileName.lastIndexOf(".");

        return fileName.substring(indexOfSlash + 1, indexOfDot);
    }

    private class ViewHolder {
        private TextView text;
    }
}
