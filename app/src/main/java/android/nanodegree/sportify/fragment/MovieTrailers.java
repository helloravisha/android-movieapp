package android.nanodegree.sportify.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nanodegree.dao.MovieDAO;
import android.nanodegree.sportify.activity.DetailedMovieActivity;
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
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.nanodegree.R;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieTrailers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieTrailers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieTrailers extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Movie movie = null;
    MovieDAO movieDao = null;

    TextView txtOverview = null;
    Button btnFavourite = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private JasonMovieUtil jasonMovieUtil = new JasonMovieUtil();
    private MovieParser movieParser = new MovieParser();
    private View view = null;


    private String review = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieTrailers.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieTrailers newInstance(String param1, String param2) {
        MovieTrailers fragment = new MovieTrailers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieTrailers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (movieDao == null ){
            movieDao = new MovieDAO(getActivity().getApplicationContext());
            movieDao.open();
        }
    }


    public void updateMovieData(Movie movie){
        updateMovie(movie);


    }

    public void updateMovie(Movie movie){

        btnFavourite = (Button)view.findViewById(R.id.btnFavourite);

        this.movie = movie;
        ImageView movieImageView = (ImageView)view.findViewById(R.id.ivPosterImage);
        //movie = getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
        if(movie == null ){
            return;
        }
        Picasso.with(getActivity()).load(movie.getBackdropPath()).error(R.drawable.noposter).resize(300, 400)
                .into(movieImageView);
        TextView releaseDate = ( TextView )view.findViewById(R.id.releaseDate);
        releaseDate.setText(MovieConstants.DETAILED_MOVIE_DATE_PREFIX + movie.getReleaseDate());
        TextView overview = ( TextView )view.findViewById(R.id.overview);
        overview.setText(movie.getOverview().trim());
       // overview.setMovementMethod(new ScrollingMovementMethod());
        TextView voteAverage = ( TextView )view.findViewById(R.id.voteAverage);
        voteAverage.setText(MovieConstants.DETAILED_MOVIE_VOTE_AVERAGE_PREFIX + movie.getVoteAverage() + "");
        Button title = ( Button )view.findViewById(R.id.title);
        title.setText(movie.getTitle());
        txtOverview = (TextView)view.findViewById(R.id.overview);
        if (movieDao.getMovie(movie) != null ){
            btnFavourite.setText(MovieConstants.DELETE_FAVOURITE);
        }else{
            btnFavourite.setText(MovieConstants.ADD_FAVOURITE);
        }
        btnFavourite.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_movie_trailers, container, false);

        if (!getResources().getBoolean(R.bool.isTablet)) {
            movie = getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
            //getActivity().setTitle(MovieConstants.MOVIE_DETAIL);
            ImageView movieImageView = (ImageView) view.findViewById(R.id.ivPosterImage);
            movie = getActivity().getIntent().getExtras().getParcelable(MovieConstants.MOVIE_PARCELABLE_KEY);
            if (movie == null) {
                btnFavourite = (Button)view.findViewById(R.id.btnFavourite);
                return view;
            }
            updateMovie(movie);
        }
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

    @Override
    public void onClick(View v) {
        if (movieDao == null ){
            movieDao = new MovieDAO(getActivity().getApplicationContext());
            movieDao.open();
        }
        if (btnFavourite.getText()!= MovieConstants.DELETE_FAVOURITE) {

            movieDao.insertMovie(movie);
            btnFavourite.setText(MovieConstants.DELETE_FAVOURITE);
            List<Movie> movies = movieDao.getAllMovies();
        }else{
            movieDao.deleteMovie(movie);
            btnFavourite.setText(MovieConstants.ADD_FAVOURITE);
        }
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

    public void  loadReview(Map<Long,List<String>> trailersInfo){
       // String reviewLoaded =
    }

}
