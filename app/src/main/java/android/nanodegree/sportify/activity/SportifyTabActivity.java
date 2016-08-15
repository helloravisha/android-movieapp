package android.nanodegree.sportify.activity;

import android.content.Intent;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.fragment.DetailedMovieFragment;
import android.nanodegree.sportify.fragment.MovieReviews;
import android.nanodegree.sportify.fragment.MovieTrailers;
import android.nanodegree.sportify.fragment.MoviesList;
import android.nanodegree.sportify.vo.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.nanodegree.R;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class SportifyTabActivity extends FragmentActivity implements MoviesList.OnFragmentInteractionListener,DetailedMovieFragment.OnFragmentInteractionListener,MovieTrailers.OnFragmentInteractionListener,MovieReviews.OnFragmentInteractionListener, android.nanodegree.sportify.fragment.Trailers.OnFragmentInteractionListener,MoviesList.MovieSelectionListener{

    Bundle movieBundle = new Bundle();
    Movie movie = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportify_tab);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu_sportify_steamer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        ArrayList<Movie> moviesList = intent.getExtras().getParcelableArrayList(MovieConstants.POPULAR_MOVIES);


        switch (item.getItemId()){

            case R.id.popular:
                finish();
                intent.putExtra(String.valueOf(R.string.sortOrder), MovieConstants.POPULAR_MOVIES);
                movieBundle.putParcelableArrayList(MovieConstants.POPULAR_MOVIES, moviesList);
                intent.putExtras(movieBundle);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setMovie(Movie movie) {
          this.movie = movie;
            DetailedMovieFragment detailedMovieFragment = (DetailedMovieFragment)getSupportFragmentManager().findFragmentById(R.id.detailedMovieFragment);
        detailedMovieFragment.updateMovieInfo(movie);


    }
}
