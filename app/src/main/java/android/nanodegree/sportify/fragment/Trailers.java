package android.nanodegree.sportify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nanodegree.sportify.adapter.CustomGridViewAdapter;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.util.JasonMovieUtil;
import android.nanodegree.sportify.util.MovieParser;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.util.OnRequestCompletedGenericListener;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.nanodegree.R;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Trailers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Trailers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Trailers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view = null;

    CustomGridViewAdapter customGridAdapter;

    ArrayList<android.nanodegree.sportify.vo.Trailers> gridArray = new ArrayList<android.nanodegree.sportify.vo.Trailers>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Movie movie = null;
    private JasonMovieUtil jasonMovieUtil = new JasonMovieUtil();
    private MovieParser movieParser = new MovieParser();

    private OnFragmentInteractionListener mListener;
    private Intent sharedIntent;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Trailers.
     */
    // TODO: Rename and change types and number of parameters
    public static Trailers newInstance(String param1, String param2) {
        Trailers fragment = new Trailers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Trailers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void trailersWebserviceCall(){
        OnRequestCompletedGenericListener genericListenerJasonObject = new OnRequestCompletedGenericListener() {
            @Override
            public void onRequestCompleted(Object movieInfo,String key) {

                movieParser.loadTrailer(movieInfo, key, null);
                loadTrailers(movieParser.getMovieTrailersList());
            }
        };
        long movieID = movie.getMovieId();
        String URL = MovieConstants.MOVIEDB_URL_PREFIX+"movie/"+movieID+"/videos"+MovieConstants.API_KEY;
        System.out.println("URL.Trailers.Data...."+URL);
        requestJASONObject(genericListenerJasonObject, URL);

    }

    public void  loadTrailers(Map<Long,List<String>> trailersInfo){

        GridView gridView = (GridView) view.findViewById(R.id.gridView1);
        Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.youtube);
        List<String> movieTrailers = trailersInfo.get(movie.getMovieId());
        this.getActivity().getIntent().getExtras().putString("trailer", "value");

        int x = 0;
        for(String trailerId:movieTrailers){
            if( x == 0){// Temporary variable for having the first video URL.as shaed intent.
                if ( sharedIntent != null )
                sharedIntent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v="+trailerId);
            }
            x++;
            gridArray.add(new android.nanodegree.sportify.vo.Trailers(homeIcon, trailerId));
        }
        customGridAdapter = new CustomGridViewAdapter(this.getActivity(), R.layout.rowgrid, gridArray);
        gridView.setAdapter(customGridAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_trailers, container, false);
          movie = this.getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
        return view;


        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_trailers, container, false);
    }
    private boolean status = false;
    public void setComplete(boolean status){
        this.status = status;
    }

    public boolean getComplete(){
        return status;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void updateTrailers(Movie movie){
        this.movie = movie;
        gridArray.clear();
        trailersWebserviceCall();
    }

    public void setSharedTrailInfo(Intent sharedIntent){
       this.sharedIntent = sharedIntent;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        movie = this.getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
        if(movie != null && !getResources().getBoolean(R.bool.isTablet) )
          trailersWebserviceCall();
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    Map<String,List<String>> movieTrailerList = new HashMap<>();
    public void setMovieInfo( Map<String,List<String>> movieInfo){
        this.movieTrailerList = movieInfo;
    }

    public Map<String,List<String>> getMovieInfo(){
        return movieTrailerList;
    }

    private void requestJASONObject(OnRequestCompletedGenericListener genericListenerJason,String URL){
        try{
            jasonMovieUtil.makeJsonObjectRequest(URL,genericListenerJason);
        }catch(Exception exception){

        }

    }



}
