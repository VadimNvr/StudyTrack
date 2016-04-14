package com.studytrack.app.studytrack_v1.Utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by vadim on 30.01.16.
 */
public class Animator {

    public static void animY(View v, int duration, int fromDelta, int toDelta, boolean withTranslation) {
        if (withTranslation) {
            v.setTranslationY(v.getTranslationY() + toDelta);
            fromDelta -= toDelta;
            toDelta = 0;
        }

        TranslateAnimation anim = new TranslateAnimation(0, 0, fromDelta, toDelta);
        anim.setDuration(duration);
        v.startAnimation(anim);
    }

    public static void animAlpha(View v, int duration, int visibility) {
        float target = (visibility == View.VISIBLE) ? 1f : 0f;

        AlphaAnimation alphaAnimation = new AlphaAnimation(v.getAlpha(), target);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
