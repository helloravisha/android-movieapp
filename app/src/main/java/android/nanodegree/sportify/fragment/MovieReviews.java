package android.nanodegree.sportify.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nanodegree.sportify.adapter.CustomGridViewAdapter;
import android.nanodegree.sportify.adapter.ReviewGridAdapter;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.util.JasonMovieUtil;
import android.nanodegree.sportify.util.MovieParser;
import android.nanodegree.sportify.vo.*;
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
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieReviews.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieReviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieReviews extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private JasonMovieUtil jasonMovieUtil = new JasonMovieUtil();
    private MovieParser movieParser = new MovieParser();
    private View view = null;
    private Movie movie = null;

    private ReviewGridAdapter reviewGridAdapter;

    ArrayList<android.nanodegree.sportify.vo.Review> gridArray = new ArrayList<android.nanodegree.sportify.vo.Review>();



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieReviews.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieReviews newInstance(String param1, String param2) {
        MovieReviews fragment = new MovieReviews();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieReviews() {
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

    public void reviewWebserviceCall(){
        OnRequestCompletedGenericListener genericListenerJasonObject = new OnRequestCompletedGenericListener() {
            @Override
            public void onRequestCompleted(Object movieInfo,String key) {
                movieParser.loadReviews(movieInfo, key, null);
                loadReview(movieParser.getReviewList());
            }
        };
        long movieID = movie.getMovieId();
        String URL = MovieConstants.MOVIEDB_URL_PREFIX+"movie/"+movieID+"/reviews"+MovieConstants.API_KEY;
        requestJASONObject(genericListenerJasonObject, URL);

    }

    public void  loadReview(Map<Long,List<String>> reviewsInfo){

        GridView gridView = (GridView) view.findViewById(R.id.reviewGridView);

        List<String> movieReviews = reviewsInfo.get(movie.getMovieId());
        for(String reviewInfo:movieReviews){
            gridArray.add(new android.nanodegree.sportify.vo.Review(reviewInfo));
        }
        reviewGridAdapter = new ReviewGridAdapter(this.getActivity(), R.layout.review, gridArray);

        gridView.setAdapter(reviewGridAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        movie = this.getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        movie = this.getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
        if((movie != null ) && !getResources().getBoolean(R.bool.isTablet) ) {
            reviewWebserviceCall();
        }
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void updateReview(Movie movie){
        this.movie = movie;
        gridArray.clear();
        reviewWebserviceCall();
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

    private void requestJASONObject(OnRequestCompletedGenericListener genericListenerJason,String URL) {
        try {
            jasonMovieUtil.makeJsonObjectRequest(URL, genericListenerJason);
        } catch (Exception exception) {

        }
    }

}
