package android.nanodegree.sportify.adapter;

import android.content.Context;
import android.content.Intent;
import android.nanodegree.R;
import android.nanodegree.sportify.activity.DetailedMovieActivity;
import android.nanodegree.sportify.activity.SportifyTabActivity;
import android.nanodegree.sportify.fragment.DetailedMovieFragment;
import android.nanodegree.sportify.fragment.MoviesList;
import android.nanodegree.sportify.util.JasonMovieUtil;
import android.nanodegree.sportify.util.MovieParser;
import android.nanodegree.sportify.util.Util;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.util.OnRequestCompletedGenericListener;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  android.nanodegree.sportify.constants.MovieConstants;


/**
 * Adapter class for holding movie info with recycle view .
 * Created by ravisha on 10/6/15.
 */
public class SportifyRecycleViewAdapter extends RecyclerView.Adapter<SportifyRecycleViewAdapter.MovieViewHolder> {


    private LayoutInflater layoutInflater;
    private static ArrayList<Movie>  movieList = new ArrayList<Movie>();
    private  Context context;
    private JasonMovieUtil jasonMovieUtil = new JasonMovieUtil();
    private MovieParser movieParser = new MovieParser();
    private String movieCategory;
    private MoviesList.MovieSelectionListener  movieSelectionListener = null;


    public SportifyRecycleViewAdapter(Context context){
        layoutInflater =   LayoutInflater.from(context);
        this.context = context;
    }

    public void setMovieList(ArrayList<Movie> movieList ){
        this.movieList = movieList;
        notifyItemRangeChanged(0, movieList.size());
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public void setMovieSelectionListener(MoviesList.MovieSelectionListener  movieSelectionListener){
        this.movieSelectionListener = movieSelectionListener;

    }

    public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieImage;


        public MovieViewHolder(View movieView,MoviesList.MovieSelectionListener  movieSelectionListener) {

            super(movieView);
            movieImage = ( ImageView )movieView.findViewById(R.id.movieImage);
            movieImage.setOnClickListener(this);

        }








       @Override
       public void onClick(View v) {
           Bundle movieBundleObject = new Bundle();
           Movie movie = movieList.get((Integer)v.getTag());
           Intent intent = null;

           if(v.getResources().getBoolean(R.bool.isTablet)){
               movieSelectionListener.setMovie(movie);

           }else {
                intent = new Intent(context, DetailedMovieActivity.class);
               movieBundleObject.putParcelable(MovieConstants.MOVIE_PARCELABLE_KEY,movie);
               intent.putExtras(movieBundleObject);
               context.startActivity(intent);
           }
       }
   }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.movie,viewGroup,false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view,movieSelectionListener);
        return movieViewHolder;
    }


    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int index) {
        Movie currentMovie = movieList.get(index);
        Context context1 = viewHolder.movieImage.getContext();
        viewHolder.movieImage.setTag(index);
        if(!Util.haveNetworkConnection(context)){
            System.out.println("No Network Connection..");
        }

        if(currentMovie.getBackdropPath() != null ) {
            Picasso.with(context1).load(currentMovie.getBackdropPath()).error(R.drawable.noposter).resize(250,350)
                    .into(viewHolder.movieImage);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();

    }
}
