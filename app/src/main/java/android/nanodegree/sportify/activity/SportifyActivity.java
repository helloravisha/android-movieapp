package android.nanodegree.sportify.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nanodegree.dao.MovieDAO;
import android.nanodegree.sportify.util.AppStatus;
import android.nanodegree.sportify.util.JasonMovieDataParser;
import android.nanodegree.sportify.util.Util;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.R;
import android.nanodegree.sportify.adapter.SportifyRecycleViewAdapter;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.util.OnRequestCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SportifyActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  recycleLayoutManager;
    ArrayList<Movie> moviesList = null;
    MovieDAO movieDAO = null;
    Bundle movieBundle = new Bundle();
    private JasonMovieDataParser jasonMovieDataParser = new JasonMovieDataParser();
    private final String  URI = "content://android.nanodegree.contentprovider.MovieContentProvider/favmovies";
    private ArrayList<Movie> favMovieList = new ArrayList<Movie>();
    private CursorLoader cursorLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_sportify);
        getSupportLoaderManager().initLoader(1, null, this);
        recyclerView = ( RecyclerView)findViewById(R.id.recycleView);
        recycleLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(recycleLayoutManager);
        final SportifyRecycleViewAdapter sportifyRecycleViewAdapter = new SportifyRecycleViewAdapter(this);
        recyclerView.setAdapter(sportifyRecycleViewAdapter);
        moviesList = null;
        if(movieDAO == null ) {
            movieDAO = new MovieDAO(getApplicationContext());
            movieDAO.open();
        }
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(ArrayList<Movie> moviesList,String moviesCategory) {
                System.out.println("##################Call Back..");
                if(MovieConstants.HIGH_RATED_MOVIES.equals(moviesCategory)){
                    sportifyRecycleViewAdapter.setMovieList(moviesList);
                    recyclerView.setHasFixedSize(true);
                   // movieBundle.putParcelableArrayList(moviesCategory, moviesList);
                }else if(MovieConstants.POPULAR_MOVIES.equals(moviesCategory)){
                    System.out.println("Popular Movie..in Call Back..");
                    sportifyRecycleViewAdapter.setMovieList(moviesList);
                    recyclerView.setHasFixedSize(true);
                   // movieBundle.putParcelableArrayList(moviesCategory,moviesList);
                }

            }
        };
        String sortOrder = intent.getStringExtra(String.valueOf(R.string.sortOrder));
       /*
        if(!Util.haveNetworkConnection(getApplicationContext())) {
            Toast noNetworkToast = Toast.makeText(getApplicationContext(), "No Network Connectivity,Showing Fav Movies", Toast.LENGTH_SHORT);
            noNetworkToast.show();
        } */
        // If there is no network or when you select fav movies , display Fav movies..
        if(MovieConstants.FAV_MOVIES.equals(sortOrder  )  || !Util.haveNetworkConnection(getApplicationContext())  ){
            System.out.println("Checking for Fav Movies..");
            setTitle(MovieConstants.FAV_MOVIES);
            sportifyRecycleViewAdapter.setMovieList(favMovieList);
            recyclerView.setHasFixedSize(true);
        } else if(null == sortOrder){// if there is no option selcted, show Popular Movies
            setTitle(MovieConstants.POPULAR_MOVIES);
            jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.MOST_POPULAR_MOVIES_URL, listener);
        }
        else{ // if the selected option is either Popular or high rated and if there is network connectivity this condition executes.
            setTitle(Html.fromHtml(sortOrder));
            if(MovieConstants.POPULAR_MOVIES.equals(sortOrder)) {
                jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.MOST_POPULAR_MOVIES_URL, listener);
            }else{
                jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.HIGH_RATED_MOVIES_URL, listener);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu_sportify_steamer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        switch (item.getItemId()){

            case R.id.popular:
                finish();
                intent.putExtra(String.valueOf(R.string.sortOrder), MovieConstants.POPULAR_MOVIES);
                startActivity(intent);
                break;
            case R.id.highRated:
                finish();
                intent.putExtra(String.valueOf(R.string.sortOrder), MovieConstants.HIGH_RATED_MOVIES);
                startActivity(intent);
                break;
            case R.id.favourites:
                finish();
                intent.putExtra(String.valueOf(R.string.sortOrder), MovieConstants.FAV_MOVIES);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
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
            favMovieList.add(movie);
            cursor.moveToNext();
        }
        cursor.close();


    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
