package android.nanodegree.sportify.util;

import android.nanodegree.R;
import android.nanodegree.sportify.constants.MovieAttributes;
import android.nanodegree.util.AppController;
import android.nanodegree.util.OnRequestCompletedListener;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.vo.Movie;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;


/**
 * Jason parser for making webservice request and parsing the jason response.
 * Created by ravisha on 10/28/15.
 */
public class JasonMovieDataParser {

    private String jsonResponse;
    private TextView txtResponse;
    private ArrayList<Movie> popularMovies = null;
    private ArrayList<Movie> highRatedMovies = null;

    public void makeJsonObjectRequest(final String URL,final OnRequestCompletedListener requestListener) {

      System.out.println("Make Jason Request...");
        Listener listener = new Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    System.out.println("Make if out  Request...");
                    if(URL.contains(MovieConstants.POPULAR_MOVIE_STRING)){
                         popularMovies = new ArrayList<>();
                         popularMovies =   parseJasonObject(response);

                         requestListener.onRequestCompleted(popularMovies, MovieConstants.POPULAR_MOVIES);
                    }else{
                        System.out.println("Make else Request...");
                          highRatedMovies = new ArrayList<>();
                          highRatedMovies =   parseJasonObject(response);

                          requestListener.onRequestCompleted(highRatedMovies, MovieConstants.HIGH_RATED_MOVIES);
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }


        };

        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(GET, URL, (String) null, listener, errorListener);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    private ArrayList<Movie> parseJasonObject(Object response) throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        JSONObject jsonObject = (JSONObject) response;
        String name = jsonObject.getString(MovieAttributes.PAGE);
        JSONArray jsonArray = jsonObject.getJSONArray(MovieAttributes.RESULTS);
        for (int i = 0; i < jsonArray.length(); i++) {
            Movie movie = new Movie();
            JSONObject movieObject = (JSONObject) jsonArray.get(i);
            movie.setTitle(movieObject.getString(MovieAttributes.TITLE));
            String backdropPath =  movieObject.getString(MovieAttributes.BACKDROP_PATH);
            movie.setBackdropPath(MovieConstants.MOVIE_POSTER_PREFIX + backdropPath);
            String overview = movieObject.getString(MovieAttributes.OVERVIEW);
            if("null" == overview || overview == null )
              movie.setOverview(MovieConstants.NOT_AVAILABLE);
            else{
                movie.setOverview(overview);

            }
            movie.setVoteAverage(movieObject.getDouble(MovieAttributes.VOTE_AVERAGE));
            String releaseDate = movieObject.getString(MovieAttributes.RELEASE_DTAE);
            if("null" == releaseDate || releaseDate == null )
                movie.setReleaseDate(MovieConstants.NOT_AVAILABLE);
            else{
                movie.setReleaseDate(releaseDate);

            }
            movie.setLanguage(movieObject.getString(MovieAttributes.ORIGINAL_LANGUAGE));
            movie.setPopularity(movieObject.getDouble(MovieAttributes.POPULARITY));
            movie.setVoteAverage(movieObject.getDouble(MovieAttributes.VOTE_AVERAGE));
            movie.setVoteCount(movieObject.getLong(MovieAttributes.VOTE_COUNT));
            movie.setMovieId(movieObject.getLong(MovieAttributes.ID));
            movies.add(movie);
        }
        return movies;
    }
}






