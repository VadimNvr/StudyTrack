package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch.CitiesSearchActivity;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch.ResultListAdapter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vadim on 14.01.16.
 */
public class Cities extends FilterPageFragment {
    protected Button addButton;
    protected ListView citiesList;
    protected HashSet<String> chosenCities;
    protected SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.filter_item_cities, container, false);
        addButton = (Button) view.findViewById(R.id.add_button);
        citiesList = (ListView) view.findViewById(R.id.cities_list);

        citiesList.setAdapter(new ResultListAdapter(getActivity()));
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        chosenCities = (HashSet<String>) pref.getStringSet("cities_filter", new HashSet<String>());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CitiesSearchActivity.class);
                intent.putExtra("chosen_cities", chosenCities);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        chosenCities = (HashSet<String>) data.getSerializableExtra("chosen_cities");
        ((ResultListAdapter) citiesList.getAdapter()).setData(chosenCities);
    }

    @Override
    public void initAccept() {
        pref.edit()
            .putStringSet("cities_filter", chosenCities)
            .apply();
    }
}
