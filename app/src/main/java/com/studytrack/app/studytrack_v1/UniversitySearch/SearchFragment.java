package com.studytrack.app.studytrack_v1.UniversitySearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.rey.material.widget.ProgressView;
import com.studytrack.app.studytrack_v1.Fab;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.FilterFragment;
import com.studytrack.app.studytrack_v1.UniversitySearch.University.UniversityFragment;
import com.studytrack.app.studytrack_v1.Utils.Animator;
import com.studytrack.app.studytrack_v1.myFragment;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import Entities.University;
import Requests.FilterRequest;
import Requests.Filters.Filter;
import Requests.GetFavoriteRequest;
import Requests.Request;

/**
 * Created by vadim on 03.01.16.
 */
public class SearchFragment extends myFragment {
    protected AppCompatActivity activity;
    protected View fragment;
    protected myFragment cur_frag;
    protected Toolbar toolbar;
    protected ActionBar actionbar;
    protected ProgressView progress;

    Filter filter = null;

    protected Fab fab;
    protected MaterialSheetFab materialSheetFab;
    protected View[] sheetItems;

    protected RecyclerView university_recycler;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private boolean was_error;

    boolean isFavorite;
    int curCount = 20;
    int curOffset = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public SearchFragment() {
        isFavorite = false;
    }

    public SearchFragment(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup _container,
                             Bundle savedInstanceState) {
        return fragment = inflater.inflate(R.layout.fragment_search, _container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivity();
        initProgress();
        initToolbar();
        initSheetFab();
        initRecycler();
        initProgress();
        if(!isFavorite || was_error) {
            try {
                isFavorite = false;
                this.filter = getFilter();
                was_error = false;
            }
           catch (InvalidParameterException e) {
                isFavorite = true;
                was_error = true;
           }
        }
        loading = false;
        curOffset = 0; // TODO: 23.03.2016 Write it normal
        new LoadDataTask(filter, 20, 0).execute();

    }

    private void initActivity() {
        activity = (AppCompatActivity) getActivity();
    }

    private void initToolbar() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionbar.setTitle("Университеты");

        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setTranslationY(0);
        Animator.animAlpha(toolbar, 150, View.VISIBLE);

        //Animator.animY(toolbar, 150, 0, 0 - (int) toolbar.getTranslationY(), true);
    }

    private void initSheetFab() {
        View sheetView = fragment.findViewById(R.id.fab_sheet);
        View overlay = fragment.findViewById(R.id.overlay);
        int сolor = getResources().getColor(R.color.backgroundColorPrimary);

        fab = (Fab) fragment.findViewById(R.id.fab);
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, сolor, сolor);

        sheetItems = new View[2];
        sheetItems[0] = fragment.findViewById(R.id.fab_sheet_item_filter);
        sheetItems[1] = fragment.findViewById(R.id.fab_sheet_item_sort);

        sheetItems[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialSheetFab.hideSheet();
                cur_frag = new FilterFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, cur_frag)
                        .commit();
            }
        });
    }

    private void initRecycler() {
        university_recycler = (RecyclerView) fragment.findViewById(R.id.university_list);
        university_recycler.setLayoutManager(new LinearLayoutManager(activity));

        university_recycler.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                toolbar.setTranslationY(Math.min(
                        0.0f,
                        Math.max(toolbar.getTranslationY() - dy, -3 * getResources().getDimensionPixelSize(R.dimen.toolbar_height))
                ));
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) university_recycler.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                linearLayoutManager.findFirstVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && !isFavorite) {
                    // End has been reached
                    // Do something

                    loading = true;
                    loadMoreData();
                }
            }

            @Override
            public void onHide() {
                fab.hide_down();
            }

            @Override
            public void onShow() {
                fab.show_up();
            }
        });
    }

    private void loadMoreData() {
        new LoadDataTask(filter, curCount, curOffset).execute();
    }

    private void initProgress() {
        progress = (ProgressView) activity.findViewById(R.id.progress);
    }

    private Filter getFilter() {
        Filter filter = new Filter();
        SharedPreferences pref = activity.getPreferences(Context.MODE_PRIVATE);
        Set<String> town_names = pref.getStringSet("cities_filter", null);
        Set<String> pointsSet = pref.getStringSet("points", null);
        boolean flag = pref.getBoolean("flag", true);
        if(town_names != null) {
            List<String> towns = new ArrayList<>(town_names);
            filter.addTownsFilter(towns);
        }
        if(pointsSet != null && !pointsSet.isEmpty()) {
            List<Integer> points = new ArrayList<>();
            for (String s : pointsSet) {
                Double value = Double.parseDouble(s);
                points.add(value.intValue());
            }
            Collections.sort(points);
            filter.addPointsFilter(points, flag);
        }
        return filter;
    }


    @Override
    public boolean onBackPressed() {
        if ((cur_frag != null) && cur_frag.isAdded() && cur_frag.onBackPressed()) {
            return true;
        } else if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
            return true;
        } else return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                cur_frag = new FilterFragment();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, cur_frag)
                        .commit();
                return true;
        }

        return false;
    }

    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        ArrayList<University> listItems = new ArrayList<>();
        int count;
        int offset;
        Request<University> request;
        RecyclerAdapter recyclerAdapter;
        Filter filter;

        public LoadDataTask(Filter filter, int count, int offset) {
            this.count = count;
            this.offset = offset;
            this.filter = filter;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.start();
            if(!isFavorite) {
                this.request = new FilterRequest(activity, offset, count, filter);
            } else {
                this.request = new GetFavoriteRequest(activity);
            }
            this.request.execute();
            recyclerAdapter = (RecyclerAdapter) university_recycler.getAdapter();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<University> universities = null;
            try {
                universities = this.request.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
                listItems.addAll(universities);

            if (recyclerAdapter != null) {
                recyclerAdapter.addItems(universities);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (university_recycler.getAdapter() == null) {
                View.OnClickListener ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cur_frag = new UniversityFragment();

                        int itemPosition = university_recycler.getChildAdapterPosition(view);
                        University university = listItems.get(itemPosition - 1);

                        putData(cur_frag, university);
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .addToBackStack(null)
                                .replace(R.id.main_fragment, cur_frag)
                                .commit();
                    }
                };

                recyclerAdapter = new RecyclerAdapter(activity, listItems, ocl);
                university_recycler.setAdapter(recyclerAdapter);
            }
            else {
                try {
                    recyclerAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {

                }
            }

            progress.stop();
            curOffset += count;
            SearchFragment.this.loading = false;
        }

        private void putData(myFragment frag, University data) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            frag.setArguments(bundle);
        }
    }
}


