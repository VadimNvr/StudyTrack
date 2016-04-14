package com.studytrack.app.studytrack_v1.UniversitySearch.University;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.codecrafters.tableview.TableHeaderAdapter;

/**
 * Created by yudzh_000 on 05.04.2016.
 */
public class SpecialityTableHeaderAdapter extends TableHeaderAdapter {

    public SpecialityTableHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView(int columnIndex, ViewGroup parentView) {
        TextView renderedView = new TextView(getContext());
        renderedView.setPadding(20, 10, 20, 10);
        renderedView.setTextSize(18);
        renderedView.setTextColor(Color.WHITE);
        switch (columnIndex) {
            case 0:
                renderedView.setText("Название");
                break;
            case 1:
                renderedView.setText("Цена");
                break;
            case 2:
                renderedView.setText("Баллы");
                break;
        }
        renderedView.setGravity(Gravity.CENTER);
        return renderedView;
    }
}
