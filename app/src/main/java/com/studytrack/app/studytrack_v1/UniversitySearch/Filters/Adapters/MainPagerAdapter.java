package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.studytrack.app.studytrack_v1.FilterPageFragment;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages.Cities;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages.Scores;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages.Specialities;
import com.studytrack.app.studytrack_v1.UniversitySearch.Filters.Pages.Studies;

/**
 * Created by vadim on 10.01.16.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private final int PAGES_COUNT = 4;
    protected FilterPageFragment pages[];

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        pages = new FilterPageFragment[] {
                new Cities(),
                new Scores(),
                new Specialities(),
                new Studies()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
//        switch (position) {
//            case 0:
//                return new Cities();
//
//            case 1:
//                return new Scores();
//
//            case 2:
//                return new Specialities();
//
//            case 3:
//            default:
//                return  new Studies();
//        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Города";

            case 1:
                return "Специальности";

            case 2:
                return "Баллы ЕГЭ";

            case 3:
            default:
                return "Обучение";
        }
    }

    public void initAccept() {
        for (FilterPageFragment page : pages) {
            page.initAccept();
        }
    }
}