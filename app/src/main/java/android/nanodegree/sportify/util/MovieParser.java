package android.nanodegree.sportify.util;

import android.nanodegree.sportify.constants.MovieAttributes;
import android.nanodegree.sportify.vo.Movie;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ravisha on 1/5/16.
 */
public class MovieParser {
    private Map<Long,List<String>> movieTrailerList = new HashMap<>();
    private Map<Long,List<String>> movieReviewList = new HashMap<>();


    private String review = null;
    public void loadTrailer(Object movieData,String key,Bundle movieBundle){
        try {
            if (true) {
                JSONObject jsonObject = (JSONObject) movieData;
                Long name = jsonObject.getLong(MovieAttributes.ID);
                JSONArray jsonArray = jsonObject.getJSONArray(MovieAttributes.RESULTS);
                if(!movieTrailerList.containsKey(name)) {
                    List<String> trailerKeys = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movieObject = (JSONObject) jsonArray.get(i);
                        trailerKeys.add(movieObject.getString(MovieAttributes.KEY));
                    }
                    System.out.println("Trailers Keys in Movie Parser..!!!!!"+trailerKeys);
                    this.movieTrailerList.put(name,trailerKeys);
                }
            }
        }catch(Exception exception){

        }

    }

    public void loadReviews(Object movieData,String key,Bundle movieBundle){
        try {
            if (true) {
                JSONObject jsonObject = (JSONObject) movieData;
                Long name = jsonObject.getLong(MovieAttributes.ID);
                JSONArray jsonArray = jsonObject.getJSONArray(MovieAttributes.RESULTS);
                if(!movieTrailerList.containsKey(name)) {
                    List<String> reviews = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movieObject = (JSONObject) jsonArray.get(i);
                        reviews.add(movieObject.getString(MovieAttributes.CONTENT));
                    }
                    this.movieReviewList.put(name, reviews);

                }
            }
        }catch(Exception exception){

        }

    }

    public Map<Long,List<String>> getMovieTrailersList(){
        return movieTrailerList;
    }

    public Map<Long,List<String>> getReviewList(){
        return movieReviewList;
    }
}
