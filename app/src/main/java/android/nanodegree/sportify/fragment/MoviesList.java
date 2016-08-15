package android.nanodegree.sportify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.nanodegree.dao.MovieDAO;
import android.nanodegree.sportify.adapter.SportifyRecycleViewAdapter;
import android.nanodegree.sportify.constants.MovieConstants;
import android.nanodegree.sportify.util.JasonMovieDataParser;
import android.nanodegree.sportify.util.Util;
import android.nanodegree.sportify.vo.Movie;
import android.nanodegree.util.OnRequestCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.nanodegree.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  recycleLayoutManager;
    private JasonMovieDataParser jasonMovieDataParser = new JasonMovieDataParser();
    ArrayList<Movie> moviesList = null;
    MovieDAO movieDAO = null;
    View view = null;
    private final String  URI = "content://android.nanodegree.contentprovider.MovieContentProvider/favmovies";
    private ArrayList<Movie> favMovieList = new ArrayList<Movie>();
    private CursorLoader cursorLoader;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MovieSelectionListener movieSelectionListener;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //cursorLoader= new CursorLoader(context, Uri.parse("content://com.example.contentproviderexample.MyProvider/cte"), null, null, null, null);
        cursorLoader= new CursorLoader(getActivity().getApplicationContext(), Uri.parse(URI), null, null, null, null);
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


    public interface MovieSelectionListener{
        public void setMovie(Movie movie);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment movies_list.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesList newInstance(String param1, String param2) {
        MoviesList fragment = new MoviesList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MoviesList() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getLoaderManager().initLoader(1, null, this);

        view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        recyclerView = ( RecyclerView)view.findViewById(R.id.recycleViewTab);
        recycleLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(recycleLayoutManager);
        final SportifyRecycleViewAdapter sportifyRecycleViewAdapter = new SportifyRecycleViewAdapter(getActivity());
        recyclerView.setAdapter(sportifyRecycleViewAdapter);

        Intent intent = getActivity().getIntent();
        moviesList = null;
        if(movieDAO == null ) {
            movieDAO = new MovieDAO(getActivity().getApplicationContext());
            movieDAO.open();
        }
       final String sortOrder = intent.getStringExtra(String.valueOf(R.string.sortOrder));
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(ArrayList<Movie> moviesList,String moviesCategory) {

                if(MovieConstants.HIGH_RATED_MOVIES.equals(moviesCategory)){
                    sportifyRecycleViewAdapter.setMovieList(moviesList);
                    sportifyRecycleViewAdapter.setMovieCategory(sortOrder);
                    sportifyRecycleViewAdapter.setMovieSelectionListener(movieSelectionListener);
                    recyclerView.setHasFixedSize(true);
                    // movieBundle.putParcelableArrayList(moviesCategory, moviesList);
                }else if(MovieConstants.POPULAR_MOVIES.equals(moviesCategory)){

                    sportifyRecycleViewAdapter.setMovieList(moviesList);
                    sportifyRecycleViewAdapter.setMovieCategory(sortOrder);
                    sportifyRecycleViewAdapter.setMovieSelectionListener(movieSelectionListener);
                    recyclerView.setHasFixedSize(true);
                    // movieBundle.putParcelableArrayList(moviesCategory,moviesList);
                }

            }
        };


        if(MovieConstants.FAV_MOVIES.equals(sortOrder  )  || !Util.haveNetworkConnection(getActivity().getApplicationContext())  ){

            //moviesList = movieDAO.getAllMovies();
            getActivity().setTitle(MovieConstants.FAV_MOVIES);
            sportifyRecycleViewAdapter.setMovieList(favMovieList);
            sportifyRecycleViewAdapter.setMovieCategory(sortOrder);
            sportifyRecycleViewAdapter.setMovieSelectionListener(movieSelectionListener);
            recyclerView.setHasFixedSize(true);
        } else if(null == sortOrder){// if there is no option selcted, show Popular Movies
            getActivity().setTitle(MovieConstants.POPULAR_MOVIES);
            jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.MOST_POPULAR_MOVIES_URL, listener);
        }
        else{ // if the selected option is either Popular or high rated and if there is network connectivity this condition executes.
            getActivity().setTitle(Html.fromHtml(sortOrder));
            if(MovieConstants.POPULAR_MOVIES.equals(sortOrder)) {
                jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.MOST_POPULAR_MOVIES_URL, listener);
            }else{
                jasonMovieDataParser.makeJsonObjectRequest(MovieConstants.HIGH_RATED_MOVIES_URL, listener);
            }
        }

        return view;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (movieSelectionListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            movieSelectionListener = (MovieSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        movieSelectionListener = null;
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

}
