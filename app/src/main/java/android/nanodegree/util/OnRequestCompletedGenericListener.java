package android.nanodegree.util;

import android.nanodegree.sportify.vo.Movie;

import java.util.ArrayList;

/**
 * Created by ravisha on 1/5/16.
 */
public interface OnRequestCompletedGenericListener {
    void onRequestCompleted(Object moviesList, String key);
}
