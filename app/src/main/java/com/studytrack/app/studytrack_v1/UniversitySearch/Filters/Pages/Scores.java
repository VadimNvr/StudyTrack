package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;
import com.rey.material.widget.Switch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Scores extends FilterPageFragment {
    RangeBar scoreRangeBar;
    RangeBar priceRangeBar;
    TextView leftScoreValue;
    TextView rightScoreValue;
    TextView leftPriceValue;
    TextView rightPriceValue;
    Switch checkBox;
    protected SharedPreferences pref;
    List<String> points = new ArrayList<>(2);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.filter_item_points, container, false);
        leftScoreValue  = (TextView) view.findViewById(R.id.score_left_text);
        rightScoreValue = (TextView) view.findViewById(R.id.score_right_text);
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        scoreRangeBar = (RangeBar) view.findViewById(R.id.scores_rangebar);
        checkBox = (Switch) view.findViewById(R.id.checkBox);
        final Set<String> point = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet("points", new HashSet<String>());
        boolean flag = pref.getBoolean("flag", false);
        checkBox.setChecked(flag);
        if(!point.isEmpty()){
            ArrayList<Integer> p = new ArrayList<>();
            for (String s : point) {
                p.add(Integer.parseInt(s));
            }
            Collections.sort(p);
            leftScoreValue.setText(p.get(0).toString());
            rightScoreValue.setText(p.get(1).toString());
            scoreRangeBar.setRangePinsByValue(p.get(0), p.get(1));
        } else {
            this.points.add("0");
            this.points.add("100");
        }
        scoreRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                leftScoreValue.setText(s);
                rightScoreValue.setText(s1);
                points.clear();
                points.add(0, s);
                points.add(1, s1);
            }

        });

        if(this.points.isEmpty()) {
            this.points.add("0");
            this.points.add("100");
        }
        return view;
    }

    @Override
    public void initAccept() {
        pref.edit()
                .putStringSet("points", new HashSet<>(points))
                .putBoolean("flag", checkBox.isChecked())
                .apply();
    }
}
