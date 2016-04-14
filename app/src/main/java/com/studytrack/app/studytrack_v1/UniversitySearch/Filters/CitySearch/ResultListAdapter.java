package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.studytrack.app.studytrack_v1.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import Entities.Town;
import Requests.GetTownsWithSpecial;

/**
 * Created by vadim on 12.04.16.
 */
public class ResultListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<String> chosenCities;

    public ResultListAdapter(Context ctx) {
        this.ctx = ctx;
        chosenCities = new ArrayList<>();
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chosenCities.size();
    }

    @Override
    public Object getItem(int position) {
        return chosenCities.get(position);
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
            view = lInflater.inflate(R.layout.filter_result_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.city_text)).setText(chosenCities.get(position));

        return view;
    }

    public void setData(HashSet<String> cities) {
        chosenCities = new ArrayList<>();
        chosenCities.addAll(cities);
        notifyDataSetChanged();
    }
}
