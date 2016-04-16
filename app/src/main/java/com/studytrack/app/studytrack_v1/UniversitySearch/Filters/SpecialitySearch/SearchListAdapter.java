package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.SpecialitySearch;

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
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import Entities.Speciality;
import Entities.Town;
import Requests.GetTownsWithSpecial;

/**
 * Created by vadim on 16.04.16.
 */
public class SearchListAdapter extends BaseAdapter {
    AppCompatActivity ctx;
    LayoutInflater lInflater;
    List<Town> specs; //TODO List<Speciality>
    Set<String> chosenSpecs;

    SearchListAdapter(AppCompatActivity ctx, Set<String> chosenSpecs) {
        this.ctx = ctx;
        this.specs = new ArrayList<Town>(); //TODO Speciality
        this.chosenSpecs = chosenSpecs;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return specs.size();
    }

    @Override
    public Object getItem(int position) {
        return specs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.filter_speciality_result_item, parent, false);
        }

        String specName = specs.get(position).getName();
        ((TextView) view.findViewById(R.id.speciality_text)).setText(specName);

        if (chosenSpecs.contains(specName)) {
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
        GetTownsWithSpecial request; //TODO GetSpecsWitSpecial
        CharSequence constraint;
        List<Town> results; //TODO Specialities

        Filterer(CharSequence constraint) {
            this.constraint = constraint;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            request = new GetTownsWithSpecial(ctx, constraint.toString()); //TODO Specialities
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
                    specs = results.subList(0, 4);
                else
                    specs = results;

                notifyDataSetChanged();
            }
        }
    }
}
