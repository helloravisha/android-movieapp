package android.nanodegree.sportify.constants;

/**
 * Interface Holding all the constants required for movie related activities.
 * Created by ravisha on 11/6/15.
 */
public interface MovieConstants {
    String POPULAR_MOVIES = "Popular Movies";
    String HIGH_RATED_MOVIES = "High Rated Movies";
    String FAV_MOVIES = "Favourite Movies";
    String MOST_POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=a6788f0e05197c7bcdff747e397dd62d";
    String HIGH_RATED_MOVIES_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=a6788f0e05197c7bcdff747e397dd62d";
    String MOVIEDB_URL_PREFIX = "http://api.themoviedb.org/3/";
    String API_KEY = "?api_key=a6788f0e05197c7bcdff747e397dd62d";
    String TRAILER_URL = "http://api.themoviedb.org/3/movie/157336/videos?api_key=a6788f0e05197c7bcdff747e397dd62d";
    String MOVIE_PARCELABLE_KEY = "movie";
    String MOVIE_POSTER_PREFIX = "http://image.tmdb.org/t/p/w500/";
    String DETAILED_MOVIE_DATE_PREFIX = "ReleaseDate: ";
    String DETAILED_MOVIE_VOTE_AVERAGE_PREFIX = "Vote Average: ";
     String prefix="This Button will launch my ";
     String SPORTIFY_APP_BUTTON_TEXT  = prefix+" Sportify Steamer App3!";
     String SCORED_APP_BUTTON_TEXT  = prefix+" Scored App!";
     String LIBRARY_APP_BUTTON_TEXT = prefix+" Library App!";
     String BUILD_APP_BUTTON_TEXT  = prefix+" Build it Bigger  App!";
     String CAPSSTONE_APP_BUTTON_TEXT  = prefix+" Capstone App!";
     String XYZREADER_APP_BUTTON_TEXT = prefix+" XYZ Reader App!";
     String MOVIE_DETAIL = "MovieDetail";
    String NOT_AVAILABLE = "Not Available";
    String POPULAR_MOVIE_STRING ="popularity";
    String ADD_FAVOURITE = "Add Favourite";
    String DELETE_FAVOURITE = "Delete Favourite";


}
