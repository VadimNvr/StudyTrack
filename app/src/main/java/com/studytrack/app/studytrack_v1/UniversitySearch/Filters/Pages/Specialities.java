package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rey.material.widget.Button;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.SpecialitySearch.ResultListAdapter;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.SpecialitySearch.SpecialitiesSearchActivity;

import java.util.HashSet;

/**
 * Created by vadim on 14.01.16.
 */
public class Specialities extends FilterPageFragment {
    protected Button addButton;
    protected ListView specsList;
    protected HashSet<String> chosenSpecs;
    protected SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.filter_item_specialities, container, false);
        addButton = (Button) view.findViewById(R.id.add_button);
        specsList = (ListView) view.findViewById(R.id.specialities_list);

        specsList.setAdapter(new ResultListAdapter(getActivity()));
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        chosenSpecs = (HashSet<String>) pref.getStringSet("specialities_filter", new HashSet<String>());
        ((ResultListAdapter) specsList.getAdapter()).setData(chosenSpecs);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpecialitiesSearchActivity.class);
                intent.putExtra("chosen_specs", chosenSpecs);
                startActivityForResult(intent, 2);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        chosenSpecs = (HashSet<String>) data.getSerializableExtra("chosen_specs");
        ((ResultListAdapter) specsList.getAdapter()).setData(chosenSpecs);
    }

    @Override
    public void initAccept() {
        if (pref != null) {
            pref.edit()
                    .putStringSet("specialities_filter", chosenSpecs)
                    .apply();
        }
    }
}
