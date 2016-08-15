package android.nanodegree.sportify.util;

import android.nanodegree.sportify.constants.MovieAttributes;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.util.AppController;
import android.nanodegree.util.OnRequestCompletedGenericListener;
import android.nanodegree.util.OnRequestCompletedListener;
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
public class JasonMovieUtil {

    private String jsonResponse;
    private TextView txtResponse;
    private ArrayList<Movie> popularMovies = null;
    private JSONObject jsonObject = null;
    private ArrayList<Movie> highRatedMovies = null;

    public void makeJsonObjectRequest(final String URL,final OnRequestCompletedGenericListener genericListenerJason) {


        Listener listener = new Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    jsonObject =   parseJasonObject(response);
                    genericListenerJason.onRequestCompleted(jsonObject, URL);
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


    private JSONObject parseJasonObject(Object response) throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        JSONObject jsonObject = (JSONObject) response;
        return jsonObject;
    }
}






