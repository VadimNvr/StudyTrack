package com.studytrack.app.studytrack_v1.UniversitySearch.Filters;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Adapters.MainPagerAdapter;
import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.myFragment;

/**
 * Created by vadim on 09.01.16.
 */
public class FilterFragment extends myFragment {
    protected View toolbar;
    protected ViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        toolbar = getActivity().findViewById(R.id.main_toolbar);

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(getActivity().getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setTranslationY(toolbar.getTranslationY() + getResources().getDimensionPixelSize(R.dimen.toolbar_height));

        tabs.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"),
                Typeface.BOLD);

        tabs.setViewPager(pager);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.

        //searchView.setMenuItem(search);

        //search.setVisible(true);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_accept).setVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Фильтры");

        menu.findItem(R.id.action_accept).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ((MainPagerAdapter) pager.getAdapter()).initAccept();
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        //if (searchView.isSearchOpen()) {
        //    searchView.closeSearch();
        //    return true;
        //}
        //else
            return false;
    }
}
