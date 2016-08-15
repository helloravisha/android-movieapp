package android.nanodegree.sportify.util;

import android.content.Context;
import android.database.Cursor;
import android.nanodegree.sportify.vo.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ravisha on 2/4/16.
 */
public class Util {

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public static Movie cursorToFavMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setMovieId(cursor.getInt(0));
        movie.setLanguage(cursor.getString(6));
        movie.setPopularity(cursor.getDouble(8));
        movie.setBackdropPath(cursor.getString(7));
        movie.setVoteCount(cursor.getInt(5));
        movie.setVoteAverage(cursor.getDouble(4));
        movie.setTitle(cursor.getString(1));
        movie.setReleaseDate(cursor.getString(2));
        movie.setOverview(cursor.getString(3));
        return movie;
    }
}
