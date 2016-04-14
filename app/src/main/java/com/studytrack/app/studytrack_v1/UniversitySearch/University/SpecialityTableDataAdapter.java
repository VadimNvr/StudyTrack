package com.studytrack.app.studytrack_v1.UniversitySearch.University;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import Entities.Speciality;
import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by yudzh_000 on 05.04.2016.
 */
public class SpecialityTableDataAdapter extends TableDataAdapter<Speciality> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();

    public SpecialityTableDataAdapter(Context context, List<Speciality> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Speciality speciality = getRowData(rowIndex);

        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderSpecialityName(speciality);
                break;
            case 1:
                renderedView = renderPrice(speciality);
                break;
            case 2:
                renderedView = renderPoints(speciality);
                break;
        }
        ((TextView)renderedView).setTextColor(Color.BLACK);
        return renderedView;
    }

    private View renderSubjects(Speciality speciality) {
        TextView textView = new TextView(getContext());
        textView.setText(speciality.getSubjects());
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

    private View renderPoints(Speciality speciality) {
        TextView textView = new TextView(getContext());
        if(speciality.getPoints() == 0) {
            textView.setText("-");
        } else {
            textView.setText(Double.toString(speciality.getPoints()));
        }
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private View renderPrice(Speciality speciality) {
        String priceString = PRICE_FORMATTER.format(speciality.getPrice()) + " R";
        TextView textView = new TextView(getContext());
        if(priceString.equals("0")) {
            textView.setText("-");
        } else {
            textView.setText(priceString);
        }
        textView.setTextSize(TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private View renderSpecialityName(Speciality speciality) {
        TextView textView = new TextView(getContext());
        textView.setText(speciality.getName());
        textView.setPadding(20, 10, 20, 10);
        textView.setGravity(Gravity.LEFT);
        return textView;
    }
}
