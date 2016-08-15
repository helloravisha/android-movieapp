package android.nanodegree.contentprovider;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.nanodegree.dao.MySQLiteHelper;
import android.nanodegree.sportify.vo.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravisha on 2/11/16.
 */
public class LoadManagerImpl extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context = null;
    private String URI = null;
    private List<Movie>  movieList = new ArrayList<Movie>();

    public void init(){
        //getLoaderManager()
    }


    public void setContext(Context context,String URI){
        this.context = context;
        this.URI = URI;

    }

    CursorLoader cursorLoader;
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //cursorLoader= new CursorLoader(context, Uri.parse("content://com.example.contentproviderexample.MyProvider/cte"), null, null, null, null);
        cursorLoader= new CursorLoader(context, Uri.parse(URI), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToFavMovie(cursor);
            movieList.add(movie);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();


    }

    public List<Movie>  getMovieList(){
        return movieList;
    }

    private Movie cursorToFavMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setMovieId(cursor.getInt(0));
        movie.setLanguage(cursor.getString(6));
        movie.setPopularity(cursor.getDouble(8));
        movie.setBackdropPath(cursor.getString(7));
        movie.setVoteCount(cursor.getInt(5));
        movie.setVoteAverage(cursor.getDouble(4));
        movie.setTitle(cursor.getString(1));
        movie.setReleaseDate(cursor.getString(2));
        movie.setOverview(cursor.getString(3));
        return movie;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
