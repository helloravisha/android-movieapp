package android.nanodegree.util;

/**
 * Created by ravisha on 11/1/15.
 */

import android.nanodegree.sportify.vo.Movie;

import java.util.ArrayList;


public interface OnRequestCompletedListener {
    void onRequestCompleted(ArrayList<Movie> moviesList, String moviesCategory);
}
