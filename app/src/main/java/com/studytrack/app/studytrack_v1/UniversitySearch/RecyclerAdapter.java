package com.studytrack.app.studytrack_v1.UniversitySearch;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.studytrack.app.studytrack_v1.R;

import java.io.File;
import java.util.List;

import Entities.University;

/**
 * Created by vadim on 18.01.16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private List<University> universities;
    private View.OnClickListener item_listener;

    public RecyclerAdapter(Activity _activity, List<University> universities, View.OnClickListener ocl) {
        this.universities = universities;
        context = _activity;
        item_listener = ocl;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(context).inflate(R.layout.unisearch_list_item, parent, false);
            v.setOnClickListener(item_listener);
            return new RecyclerItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        if (!isPositionHeader(pos)) {
            University uni = universities.get(pos - 1);
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            holder.name.setText(uni.getName());
            holder.location.setText(uni.getTown().getName());
            holder.average_mark.setText(uni.getViewableMark());
            File image = new File(uni.getLogoPath());
            // TODO: 15.03.2016 переделать на норм лого если нет лого
            if(image.exists()) {
                Picasso.with(context).load(image).resize(150, 150).into(holder.logo);
            }
            else {
                Picasso.with(context).load(R.drawable.mgimo_logo).resize(150, 150).into(holder.logo);
            }
            holder.cost.setText(uni.getViewableMeanPrice());
        }
    }

    @Override
    public int getItemCount() {
        return universities.size() + 1; //count + header
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public void addItems(List<University> universities) {
        int start = this.universities.size();
        int count = universities.size();

        this.universities.addAll(universities);
        //notifyDataSetChanged();
        //notifyItemRangeInserted(start, count);
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
