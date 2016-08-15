package android.nanodegree.sportify.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.nanodegree.sportify.adapter.CustomGridViewAdapter;
import android.nanodegree.sportify.fragment.DetailedMovieFragment;
import android.nanodegree.sportify.fragment.MovieReviews;
import android.nanodegree.sportify.fragment.MovieTrailers;
import android.nanodegree.sportify.util.JasonMovieUtil;
import android.nanodegree.sportify.util.MovieParser;
import android.nanodegree.R;
import android.nanodegree.sportify.util.Util;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.sportify.vo.Trailers;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ShareActionProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for holding the detailed movie information, this will get triggered in the case of mobile.
 */
public class DetailedMovieActivity extends FragmentActivity implements MovieTrailers.OnFragmentInteractionListener,MovieReviews.OnFragmentInteractionListener, android.nanodegree.sportify.fragment.Trailers.OnFragmentInteractionListener,LoaderManager.LoaderCallbacks<Cursor>{

    GridView gridView;
    ArrayList<Trailers> gridArray = new ArrayList<Trailers>();
    CustomGridViewAdapter customGridAdapter;
    private JasonMovieUtil jasonMovieUtil = new JasonMovieUtil();
    private MovieParser movieParser = new MovieParser();
    private final String  URI = "content://android.nanodegree.contentprovider.MovieContentProvider/favmovies";
    private List<Movie>  movieList = new ArrayList<Movie>();
    private CursorLoader cursorLoader;

    Bundle movieBundle = new Bundle();
    private ShareActionProvider mShareActionProvider;
    android.nanodegree.sportify.fragment.Trailers trailersFragment = null;
    Intent mShareIntent  = new Intent();;

    /**
     * Start Detailed Movie activity with all the required initializations.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadTrailers();
        setContentView(R.layout.activity_detailed_movie);
        getSupportLoaderManager().initLoader(1, null, this);
        trailersFragment = (android.nanodegree.sportify.fragment.Trailers)getSupportFragmentManager().findFragmentById(R.id.trailersFragmentId);
        trailersFragment.setSharedTrailInfo(mShareIntent);
    }


    public void onFragmentInteraction(String id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.sharemenu,menu);
        MenuItem item = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        if (mShareActionProvider != null ){


            mShareIntent.setAction(Intent.ACTION_SEND);
            mShareIntent.setType("text/plain");

            //mShareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("key"));
            mShareActionProvider.setShareIntent(mShareIntent);
        }
        return true;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //cursorLoader= new CursorLoader(context, Uri.parse("content://com.example.contentproviderexample.MyProvider/cte"), null, null, null, null);
        cursorLoader= new CursorLoader(getApplicationContext(), Uri.parse(URI), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = Util.cursorToFavMovie(cursor);
            System.out.println("Movie info.."+movie.getTitle());
            movieList.add(movie);
            cursor.moveToNext();
        }
        cursor.close();


    }

    public List<Movie> getMovieList(){
        return movieList;
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
