package com.studytrack.app.studytrack_v1.UniversitySearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studytrack.app.studytrack_v1.R;
import com.studytrack.app.studytrack_v1.myFragment;

/**
 * Created by yudzh_000 on 17.04.2016.
 */
public class SendErrorFragment extends myFragment {

    protected View fragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup _container,
                             Bundle savedInstanceState) {
        return fragment = inflater.inflate(R.layout.send_error, _container, false);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }
}
