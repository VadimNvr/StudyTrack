package com.studytrack.app.studytrack_v1.UniversitySearch.University;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.RecyclerHolder.ContactViewHolder;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.RecyclerHolder.OptionsViewHolder;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.SpecialityExtendet.SpecialityFragment;

import Entities.University;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;


/**
 * Created by vadim on 29.01.16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    University data;
    AppCompatActivity activity;
    int itemsCount = 4;
    boolean[] flags = new boolean[3];

    RecyclerAdapter(University data, Activity activity) {
        this.data = data;
        this.activity = (AppCompatActivity)activity;
        if(data.getSpecialities().isEmpty()) {
            itemsCount--;
            flags[0] = true;
        }
        if(data.getMeanPrice() == 0 || data.getMeanPrice() == 0) {
            itemsCount--;
            flags[1] = true;
        }
        if(data.getPhone().equals("null")) {
            itemsCount--;
            flags[2] = true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int page) {
        return RecyclerHolder.newInstance(page, parent, flags);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int page) {
        switch (page) {
            case RecyclerHolder.OPTIONS:
                final OptionsViewHolder options_holder = (OptionsViewHolder) viewHolder;
                options_holder.web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(data.getSite()));
                        activity.startActivity(intent);
                    }
                });

                options_holder.favourites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.inverseLiked(RecyclerAdapter.this.activity);

                        if(data.getLiked() == 1) {
                            Drawable img = activity.getApplicationContext().getResources().getDrawable(R.drawable.star_checked_dark);
                            options_holder.favourites.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                        } else {
                            Drawable img = activity.getApplicationContext().getResources().getDrawable(R.drawable.star_unchecked_dark);
                            options_holder.favourites.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);

                        }
                    }
                });

                if(data.getLiked() == 1) {
                    Drawable img = activity.getApplicationContext().getResources().getDrawable(R.drawable.star_checked_dark);
                    options_holder.favourites.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                } else {
                    Drawable img = activity.getApplicationContext().getResources().getDrawable(R.drawable.star_unchecked_dark);
                    options_holder.favourites.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);
                }

                options_holder.map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + data.getAddress());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        activity.startActivity(mapIntent);
                    }
                });
                break;

            case RecyclerHolder.SPECIALITIES:
                if(flags[0]) {

                } else {
                    RecyclerHolder.SpecialitiesViewHolder holder1 = (RecyclerHolder.SpecialitiesViewHolder) viewHolder;
                    int count = data.getSpecialities().size() > 7 ? 7 : data.getSpecialities().size();
                    holder1.tableView.setDataAdapter(new SpecialityTableDataAdapter(activity.getApplicationContext(), data.getSpecialities().subList(0, count)));
                    holder1.tableView.setHeaderAdapter(new SpecialityTableHeaderAdapter(activity.getApplicationContext()));
                    holder1.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            android.support.v4.app.Fragment fragment = new SpecialityFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", data);
                            fragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.main_fragment, fragment)
                                    .commit();
                        }
                    });
                    holder1.tableView.setColumnWeight(0,4 );
                    holder1.tableView.setColumnWeight(1,2);
                    holder1.tableView.setColumnWeight(2,2);
                    int colorEvenRows = activity.getResources().getColor(R.color.white);
                    int colorOddRows = activity.getResources().getColor(R.color.gray);
                    holder1.tableView.setDataRowColoriser(TableDataRowColorizers.alternatingRows(colorEvenRows, colorOddRows));
                    break;
                }

            case RecyclerHolder.SCORES:
                if(flags[1]) {

                } else {
                    ((RecyclerHolder.ScoresViewHolder) viewHolder).setValues((float) data.getMeanPoints()
                            , (float) data.getMeanPrice());
                    ((RecyclerHolder.ScoresViewHolder) viewHolder).animate();
                    break;
                }


            case RecyclerHolder.CONTACTS:
                if(flags[2]) {

                }else {
                    ContactViewHolder holder = (ContactViewHolder) viewHolder;
                    holder.address.setText(data.getAddress());
                    holder.phone.setText(data.getPhone());
                    holder.site.setText(data.getSite());
                    break;
                }
        }
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}