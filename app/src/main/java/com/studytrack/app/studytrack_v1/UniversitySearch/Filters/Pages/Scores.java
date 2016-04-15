package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Scores extends FilterPageFragment {
    TextView leftPointsValue;
    TextView rightPointsValue;
    CheckBox checkBox;

    protected SharedPreferences pref;
    List<String> points = new ArrayList<>(2);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.filter_item_points, container, false);
        this.leftPointsValue = (TextView) view.findViewById(R.id.curentLeftValuePoints);
        this.rightPointsValue = (TextView) view.findViewById(R.id.curentRightValuePoints);
        this.pref =  getActivity().getPreferences(Context.MODE_PRIVATE);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox2);
        RangeBar rangebar = (RangeBar) view.findViewById(R.id.points_range_bar);
        Set<String> point = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet("points", null);
        boolean flag = pref.getBoolean("flag", false);
        this.checkBox.setChecked(flag);
        if(point != null && !point.isEmpty()) {
            List<Integer> points = new ArrayList<>();
            for (String s : point) {
                Double value = Double.parseDouble(s);
                points.add(value.intValue());
            }
            Collections.sort(points);
            rangebar.setThumbIndices(points.get(0) / 10, points.get(1) / 10);
            leftPointsValue.setText(Double.toString(points.get(0)));
            rightPointsValue.setText(Double.toString(points.get(1)));
        }
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
                points.clear();
                leftPointsValue.setText(Double.toString(100.0 * i / 10));
                points.add(0, Double.toString(100.0 * i / 10));
                rightPointsValue.setText(Double.toString(100.0 * i1 / 10));
                points.add(1, Double.toString(100.0 * i1 / 10));
            }
        });

        return view;
    }

    @Override
    public void initAccept() {
        pref.edit()
                .putStringSet("points", new HashSet<>(points)).putBoolean("flag", checkBox.isChecked())
                .apply();
    }
}
