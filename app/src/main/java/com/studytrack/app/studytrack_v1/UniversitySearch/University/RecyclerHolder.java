package com.studytrack.app.studytrack_v1.UniversitySearch.University;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.studytrack.app.studytrack_v1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Entities.Speciality;
import de.codecrafters.tableview.TableView;

/**
 * Created by vadim on 28.01.16.
 */
public class RecyclerHolder {
    public static final int PAGES_COUNT = 4;
    public static final int OPTIONS = 0;
    public static final int SCORES = 2;
    public static final int CONTACTS = 3;
    public static final int SPECIALITIES = 1;

    public static ViewHolder newInstance(int page, ViewGroup viewGroup, boolean[] flags) {

        switch (page) {
            case OPTIONS:
                return new OptionsViewHolder(Inflate(R.layout.uni_info_page_options, viewGroup));
            case SPECIALITIES:
                if(flags[0]) {

                } else {
                    return new SpecialitiesViewHolder(Inflate(R.layout.uni_info_page_specialities, viewGroup));
                }
            case SCORES:
                if(flags[1]) {

                }else {
                    return new ScoresViewHolder(Inflate(R.layout.uni_info_page_scores, viewGroup));
                }
            case CONTACTS:
                if(flags[2]) {

                } else {
                    return new ContactViewHolder(Inflate(R.layout.uni_info_page_contact, viewGroup));
                }
            default:
                return null;
        }
    }

    private static View Inflate(int id, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    public static class OptionsViewHolder extends ViewHolder {
        public TextView web;
        public TextView map;
        public TextView favourites;

        OptionsViewHolder(View itemView) {
            super(itemView);

            web = (TextView) itemView.findViewById(R.id.web_button);

            map = (TextView) itemView.findViewById(R.id.map_button);
            favourites = (TextView) itemView.findViewById(R.id.favourites_button);
        }
    }

    public static class ScoresViewHolder extends ViewHolder {
         public HorizontalBarChart scores_chart;
         public HorizontalBarChart prices_chart;

         ScoresViewHolder(View itemView) {
             super(itemView);

             scores_chart = (HorizontalBarChart) itemView.findViewById(R.id.scores_chart);
             prices_chart = (HorizontalBarChart) itemView.findViewById(R.id.prices_chart);

             Normalize(scores_chart);
             Normalize(prices_chart);

             scores_chart.getAxisRight().setAxisMaxValue(100.0f);
             scores_chart.getAxisLeft().setAxisMaxValue(100.0f);

             prices_chart.getAxisRight().setAxisMaxValue(300000.0f);
             prices_chart.getAxisLeft().setAxisMaxValue(300000.0f);

             prices_chart.getAxisRight().setLabelCount(4, true);
             prices_chart.getAxisRight().setValueFormatter(new AxesPriceValueFormatter());
         }

         public void animate() {
             scores_chart.animateY(900);
             prices_chart.animateY(900);
         }

         public void setValues(float score, float price) {
             scores_chart.setData(makeData(score, "Средний балл", null));
             prices_chart.setData(makeData(price, "Стоимость обучения", new PriceValueFormatter()));
         }

         private BarData makeData(float value, String label, ValueFormatter vf) {
             ArrayList<BarEntry> entries = new ArrayList<>();
             entries.add(new BarEntry(value, 0));

             BarDataSet dataset = new BarDataSet(entries, "");
             ArrayList<String> labels = new ArrayList<>();
             labels.add(label);

             dataset.setColors(ColorTemplate.PASTEL_COLORS);
             BarData data = new BarData(labels, dataset);
             data.setValueTextSize(15);
             data.setValueTextColor(Color.WHITE);

             if (vf != null)
                 data.setValueFormatter(vf);

             return data;
         }

         private void Normalize(HorizontalBarChart chart) {
             chart.setDescription("");
             chart.setDrawGridBackground(false);
             chart.setScaleEnabled(false);
             chart.setDrawValueAboveBar(false);
             chart.setHighlightPerDragEnabled(false);
             chart.setHighlightPerTapEnabled(false);
             chart.getLegend().setEnabled(false);

             XAxis xl = chart.getXAxis();
             xl.setDrawLabels(true);
             xl.setDrawAxisLine(false);
             xl.setDrawGridLines(false);
             xl.setEnabled(false);

             YAxis yl = chart.getAxisLeft();
             yl.setDrawAxisLine(false);
             yl.setDrawGridLines(false);
             yl.setDrawLabels(false);

             YAxis yr = chart.getAxisRight();
             yr.setDrawAxisLine(true);
             yr.setDrawGridLines(false);
         }
     }

    public static class ContactViewHolder extends ViewHolder {
        public TextView address;
        public TextView phone;
        public TextView site;

        ContactViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.address);
            phone   = (TextView) itemView.findViewById(R.id.phone);
            site    = (TextView) itemView.findViewById(R.id.site);
        }
    }

    public static class SpecialitiesViewHolder extends ViewHolder {

        public TableView<Speciality> tableView;
        public Button button;

        public SpecialitiesViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.show_specialities_button);
            tableView = (TableView<Speciality>) itemView.findViewById(R.id.tableView);
        }
    }

    public static class PriceValueFormatter implements ValueFormatter {
        private DecimalFormat mFormat;

        public PriceValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + " р.";
        }
    }

    public static class AxesPriceValueFormatter implements YAxisValueFormatter {
        private DecimalFormat mFormat;

        public AxesPriceValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(value/1000);
        }
    }


}
