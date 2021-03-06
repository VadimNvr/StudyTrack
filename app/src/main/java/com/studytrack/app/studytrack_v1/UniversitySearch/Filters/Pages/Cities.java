package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch.CitiesSearchActivity;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch.ResultListAdapter;

import java.util.HashSet;

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

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,final View view,final int position, long id) {
                android.support.v7.widget.AppCompatButton  button = (android.support.v7.widget.AppCompatButton )
                        (view.findViewById(R.id.button_del));
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chosenCities.remove(((TextView) (view.findViewById(R.id.city_text))).getText().toString());
                        ((ResultListAdapter) (citiesList.getAdapter())).deleteItem(((TextView) (view.findViewById(R.id.city_text))).getText().toString());
                    }
                });
            }
        });

        pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        chosenCities = (HashSet<String>) pref.getStringSet("cities_filter", new HashSet<String>());
        ((ResultListAdapter) citiesList.getAdapter()).setData(chosenCities);
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
