package com.studytrack.app.studytrack_v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by vadim on 06.01.16.
 */
public abstract class myFragment extends Fragment {
    protected Bundle data = null;

    public abstract boolean onBackPressed();
}
