package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.SpecialitySearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.studytrack.app.studytrack_v1.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by vadim on 16.04.16.
 */
public class ResultListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<String> chosenSpecs;

    public ResultListAdapter(Context ctx) {
        this.ctx = ctx;
        chosenSpecs = new ArrayList<>();
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chosenSpecs.size();
    }

    @Override
    public Object getItem(int position) {
        return chosenSpecs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.filter_speciality_result_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.speciality_text)).setText(chosenSpecs.get(position));

        return view;
    }

    public void setData(HashSet<String> cities) {
        chosenSpecs = new ArrayList<>();
        chosenSpecs.addAll(cities);
        notifyDataSetChanged();
    }
}
