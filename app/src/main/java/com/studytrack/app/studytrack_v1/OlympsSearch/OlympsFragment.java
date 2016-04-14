package com.studytrack.app.studytrack_v1.OlympsSearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studytrack.app.studytrack_v1.myFragment;
import com.studytrack.app.studytrack_v1.R;

/**
 * Created by vadim on 03.01.16.
 */
public class OlympsFragment extends myFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_olymps, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Олимпиады");
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}