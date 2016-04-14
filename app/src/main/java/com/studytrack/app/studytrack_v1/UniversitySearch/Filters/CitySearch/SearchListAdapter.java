package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.studytrack.app.studytrack_v1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import Entities.Town;
import Entities.University;
import Requests.GetTownsWithSpecial;
import Requests.GetUniversitiesRequest;

/**
 * Created by vadim on 10.04.16.
 */
public class SearchListAdapter extends BaseAdapter{
    AppCompatActivity ctx;
    LayoutInflater lInflater;
    List<Town> cities;
    Set<String> chosenCities;

    SearchListAdapter(AppCompatActivity ctx, Set<String> chosenCities) {
        this.ctx = ctx;
        this.cities = new ArrayList<Town>();
        this.chosenCities = chosenCities;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
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

        String cityName = cities.get(position).getName();
        ((TextView) view.findViewById(R.id.city_text)).setText(cityName);

        if (chosenCities.contains(cityName)) {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.gray));
        }
        else {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.backgroundColorPrimary));
        }

        return view;
    }

    public void Filter(CharSequence constraint) {
        new Filterer(constraint).execute();
    }

    private class Filterer extends AsyncTask<Void, Void, Void> {
        GetTownsWithSpecial request;
        CharSequence constraint;
        List<Town> results;

        Filterer(CharSequence constraint) {
            this.constraint = constraint;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            request = new GetTownsWithSpecial(ctx, constraint.toString());
            request.execute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                results = request.get();
            } catch (InterruptedException | ExecutionException e) {
                results = null;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (results != null) {
                if (results.size() > 4)
                    cities = results.subList(0, 4);
                else
                    cities = results;

                notifyDataSetChanged();
            }
//            Town vor = new Town("Воронеж");
//            results = new ArrayList<Town>();
//            for (int i = 0; i < 4; ++ i)
//                results.add(vor);
//
//            cities = results;
//
//            notifyDataSetChanged();
        }
    }
}
