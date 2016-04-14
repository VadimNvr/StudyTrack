package com.studytrack.app.studytrack_v1.UniversitySearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studytrack.app.studytrack_v1.R;
/**
 * Created by vadim on 18.01.16.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public ImageView logo;
    public TextView location;
    public TextView cost;
    public TextView average_mark;

    public RecyclerItemViewHolder(final View parent) {
        super(parent);

        name = (TextView) parent.findViewById(R.id.name);
        logo = (ImageView) parent.findViewById(R.id.logo);
        location = (TextView) parent.findViewById(R.id.location);
        average_mark = (TextView) parent.findViewById(R.id.mark);
        cost = (TextView) parent.findViewById(R.id.cost);
    }
}
