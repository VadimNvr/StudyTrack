package com.studytrack.app.studytrack_v1.UniversitySearch.University.SpecialityExtendet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studytrack.app.studytrack_v1.myFragment;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.SpecialityTableDataAdapter;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.SpecialityTableHeaderAdapter;

import Entities.Speciality;
import Entities.University;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;

public class SpecialityFragment extends myFragment {

    private AppCompatActivity activity;
    private Toolbar mToolbar;
    private View fragment;
    private University university;
    private TableView<Speciality> tableView;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivity();
        loadData();
        initToolbar();
        bindData();
    }

    private void loadData() {
        university = (University) getArguments().getSerializable("data");
    }


    private void initActivity() {
        activity = (AppCompatActivity) getActivity();
        tableView = (TableView<Speciality>) fragment.findViewById(R.id.tableView);
        tableView.setColumnWeight(0,3);
        int colorEvenRows = activity.getResources().getColor(R.color.white);
        int colorOddRows = activity.getResources().getColor(R.color.gray);
        tableView.setDataRowColoriser(TableDataRowColorizers.alternatingRows(colorEvenRows, colorOddRows));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return fragment = inflater.inflate(R.layout.fragment_speciality_extendet, container, false);
    }


    private void initToolbar() {
        mToolbar = (Toolbar) fragment.findViewById(R.id.toolbar);
        mToolbar.setTitle(university.getName());
        mToolbar.setNavigationIcon(R.drawable.arrow_left);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
    }

    private void bindData() {
        tableView.setHeaderAdapter(new SpecialityTableHeaderAdapter(activity.getApplicationContext()));
        tableView.setDataAdapter(new SpecialityTableDataAdapter(activity.getApplicationContext(),university.getSpecialities()));
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
