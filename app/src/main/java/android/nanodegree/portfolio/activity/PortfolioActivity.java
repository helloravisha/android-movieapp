package android.nanodegree.portfolio.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nanodegree.R;
import android.nanodegree.sportify.activity.SportifyActivity;
import android.nanodegree.sportify.activity.SportifyTabActivity;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.util.JasonMovieDataParser;
import android.nanodegree.sportify.util.JasonMovieUtil;
import android.nanodegree.sportify.util.MovieParser;
import android.nanodegree.sportify.util.Util;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.util.OnRequestCompletedGenericListener;
import android.nanodegree.util.OnRequestCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Class acts as a base activity for all the apps that comes as
 * a part of this nano degree project
 * @author ravisha
 */
public class PortfolioActivity extends Activity implements View.OnClickListener {

    private Button sportifySteamerBtn;
    private Button scoredAppBtn;
    private Button libraryAppBtn;
    private Button buidItBiggerBtn;
    private Button xyzReaderBtn;
    private Button capsstoneBtn;

    private int duration = Toast.LENGTH_SHORT;
    private JasonMovieDataParser  jasonMovieDataParser = new JasonMovieDataParser();
    private JasonMovieUtil  jasonMovieUtil = new JasonMovieUtil();

    private MovieParser movieParser = new MovieParser();
   // List<Movie> highRatedMovieList = null;
    //List<Movie> mostPopularMovieList = null;
    Bundle movieBundle = new Bundle();

    /**
     * Initilaize all the ui components here.
     */
    private void initUIComponents(){
       // getActionBar().setBackgroundDrawable(new
         //       ColorDrawable(Color.parseColor(MovieConstants.ACTION_BAR_BGCOLOR)));
      //  setTitle(R.string.app_name);
       // getActionBar().setTitle(Html.fromHtml("<font color=\"#f1f1f1\">" + getString(R.string.app_name) + "</font>"));
        sportifySteamerBtn = (Button) findViewById(R.id.sportifySteamerBtn);
        scoredAppBtn = (Button) findViewById(R.id.scoredAppBtn);
        libraryAppBtn = (Button) findViewById(R.id.libraryAppBtn);
        buidItBiggerBtn = (Button) findViewById(R.id.buidItBiggerBtn);
        capsstoneBtn = (Button) findViewById(R.id.capsstoneBtn);
        xyzReaderBtn = (Button) findViewById(R.id.xyzReaderBtn);

        sportifySteamerBtn.setOnClickListener(this);
        scoredAppBtn.setOnClickListener(this);
        libraryAppBtn.setOnClickListener(this);
        buidItBiggerBtn.setOnClickListener(this);
        capsstoneBtn.setOnClickListener(this);
        xyzReaderBtn.setOnClickListener(this);

    }


    /**
     * used for starting the activity with all the required components.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(ArrayList<Movie> moviesList,String moviesCategory) {
                  if(MovieConstants.HIGH_RATED_MOVIES.equals(moviesCategory)){

                      movieBundle.putParcelableArrayList(moviesCategory, moviesList);
                     // movieRepositry.setHighRatedMovies(moviesList);

                  }else if(MovieConstants.POPULAR_MOVIES.equals(moviesCategory)){
                      movieBundle.putParcelableArrayList(moviesCategory,moviesList);
                      //movieRepositry.setPopularMovies(moviesList);
                  }

            }
        };
       // initData(listener);

        initUIComponents();

        System.out.println("Tab .."+getResources().getBoolean(R.bool.isTablet));


    }



    /**
     * For making a webservice request.
     * @param listener
     */
    private void initData(OnRequestCompletedListener listener){
        try {

            if(Util.haveNetworkConnection(getApplicationContext()))
            jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.HIGH_RATED_MOVIES_URL,listener);
            if(Util.haveNetworkConnection(getApplicationContext()))
            jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.MOST_POPULAR_MOVIES_URL,listener);
        }catch(Exception exception){
            exception.printStackTrace();
        }

    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_portfolio, menu);
        return true;
    }


    /**
     * Listener method for all the button events.
     * @param v
     */
    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();

        switch (v.getId()) {
            case R.id.sportifySteamerBtn:
                Toast sportifyToast = Toast.makeText(context, MovieConstants.SPORTIFY_APP_BUTTON_TEXT, duration);
                sportifyToast.show();
                Intent intent = null;
                if(getResources().getBoolean(R.bool.isTablet)){
                    intent = new Intent(PortfolioActivity.this, SportifyTabActivity.class);
                }else {
                    intent =  new Intent(PortfolioActivity.this, SportifyActivity.class);
                }
                intent.putExtras(movieBundle);
                startActivity(intent);
                break;
            case R.id.scoredAppBtn:
                Toast scoredAppToast = Toast.makeText(context,MovieConstants.SCORED_APP_BUTTON_TEXT, duration);
                scoredAppToast.show();
                break;
            case R.id.libraryAppBtn:
                Toast libarayAppToast = Toast.makeText(context,MovieConstants.LIBRARY_APP_BUTTON_TEXT, duration);
                libarayAppToast.show();
                break;
            case R.id.buidItBiggerBtn:
                Toast buidItBiggerToast = Toast.makeText(context, MovieConstants.BUILD_APP_BUTTON_TEXT, duration);
                buidItBiggerToast.show();
                break;
            case R.id.capsstoneBtn:
                Toast capsstoneToast = Toast.makeText(context, MovieConstants.CAPSSTONE_APP_BUTTON_TEXT, duration);
                capsstoneToast.show();
                break;
            case R.id.xyzReaderBtn:
                Toast xyzReaderToast = Toast.makeText(context,MovieConstants.XYZREADER_APP_BUTTON_TEXT, duration);
                xyzReaderToast.show();
                break;


        }
    }

}



