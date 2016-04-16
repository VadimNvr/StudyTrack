package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.R;

import java.util.ArrayList;
import java.util.Arrays;
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

    protected SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.filter_item_points, container, false);
        leftScoreValue  = (TextView) view.findViewById(R.id.score_left_text);
        rightScoreValue = (TextView) view.findViewById(R.id.score_right_text);
        leftPriceValue  = (TextView) view.findViewById(R.id.price_left_text);
        rightPriceValue = (TextView) view.findViewById(R.id.price_right_text);

        scoreRangeBar = (RangeBar) view.findViewById(R.id.scores_rangebar);
        priceRangeBar = (RangeBar) view.findViewById(R.id.price_rangebar);

        scoreRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                leftScoreValue.setText(s);
                rightScoreValue.setText(s1);
            }
        });

        priceRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                leftPriceValue.setText(s);
                rightPriceValue.setText(s1);
            }
        });

        pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int scores[] = new int[2];
        int prices[] = new int[2];

        scores[0] = pref.getInt("filter_score_from", 0);
        scores[1] = pref.getInt("filter_score_to", 300);

        prices[0] = pref.getInt("filter_price_from", 0);
        prices[1] = pref.getInt("filter_price_to", 500);

        scoreRangeBar.setRangePinsByValue(scores[0]>0?scores[0]:0, scores[1]<300?scores[1]:300);
        priceRangeBar.setRangePinsByValue(prices[0]>0?prices[0]:0, prices[1]<500?prices[1]:500);
//        rangeBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE) {
//                    leftPointsValue.setText(rangeBar.getLeftPinValue());
//                    rightPointsValue.setText(rangeBar.getRightPinValue());
//                }
//                return false;
//            }
//        });
//        this.leftPointsValue = (TextView) view.findViewById(R.id.curentLeftValuePoints);
//        this.rightPointsValue = (TextView) view.findViewById(R.id.curentRightValuePoints);
//        this.pref =  getActivity().getPreferences(Context.MODE_PRIVATE);
//        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox2);
//        RangeBar rangebar = (RangeBar) view.findViewById(R.id.points_range_bar);
//        Set<String> point = getActivity().getPreferences(Context.MODE_PRIVATE).getStringSet("points", null);
//        boolean flag = pref.getBoolean("flag", false);
//        this.checkBox.setChecked(flag);
//        if(point != null && !point.isEmpty()) {
//            List<Integer> points = new ArrayList<>();
//            for (String s : point) {
//                Double value = Double.parseDouble(s);
//                points.add(value.intValue());
//            }
//            Collections.sort(points);
//            rangebar.setThumbIndices(points.get(0) / 10, points.get(1) / 10);
//            leftPointsValue.setText(Double.toString(points.get(0)));
//            rightPointsValue.setText(Double.toString(points.get(1)));
//        }
//        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
//            @Override
//            public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
//                points.clear();
//                leftPointsValue.setText(Double.toString(100.0 * i / 10));
//                points.add(0, Double.toString(100.0 * i / 10));
//                rightPointsValue.setText(Double.toString(100.0 * i1 / 10));
//                points.add(1, Double.toString(100.0 * i1 / 10));
//            }
//        });

        return view;
    }

    @Override
    public void initAccept() {
        pref.edit()
            .putInt("filter_score_from", Integer.parseInt(scoreRangeBar.getLeftPinValue()))
            .putInt("filter_score_to", Integer.parseInt(scoreRangeBar.getRightPinValue()))
            .putInt("filter_price_from", Integer.parseInt(priceRangeBar.getLeftPinValue()))
            .putInt("filter_price_to", Integer.parseInt(priceRangeBar.getRightPinValue()))
            .apply();
    }
}
