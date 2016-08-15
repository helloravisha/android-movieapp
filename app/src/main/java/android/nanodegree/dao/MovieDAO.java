package android.nanodegree.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.nanodegree.sportify.constants.MovieAttributes;
import android.nanodegree.sportify.vo.FavouriteMovie;
import android.nanodegree.sportify.vo.Movie;

public class MovieDAO {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;





  private String[] allColumns = {
          MovieAttributes.ID,MovieAttributes.TITLE,MovieAttributes.RELEASE_DTAE,MovieAttributes.OVERVIEW,MovieAttributes.VOTE_AVERAGE,MovieAttributes.VOTE_COUNT,MovieAttributes.ORIGINAL_LANGUAGE,
          MovieAttributes.BACKDROP_PATH,MovieAttributes.POPULARITY};

  public MovieDAO(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public String[] getFavTableColoumns(){
    return allColumns;

  }



  public void close() {
    dbHelper.close();
  }


  public SQLiteDatabase getDataBase(Context context){
    if(database == null ) {
      dbHelper = new MySQLiteHelper(context);
      database = dbHelper.getWritableDatabase();
    }
    return database;
  }

  public Movie getMovie(Movie movie){
    String getMovie = "SELECT  * FROM " + MySQLiteHelper.TABLE_FAVOURITE + " WHERE "
            + MovieAttributes.ID + " = ";
    getMovie += movie.getMovieId();
    System.out.println("Movie Query.."+getMovie);
    Cursor cursor = database.rawQuery(getMovie, null);
    cursor.moveToFirst();
    if (!cursor.isAfterLast()) {
      System.out.println("Intresting Data Present...");
      return cursorToFavMovie(cursor);
    }
    return null;

  }


  public long  insertMovie(Movie movie) {
    ContentValues values = new ContentValues();
    values.put(MovieAttributes.ID, movie.getMovieId());
    values.put(MovieAttributes.TITLE,movie.getTitle());
    values.put(MovieAttributes.BACKDROP_PATH,movie.getBackdropPath());
    values.put(MovieAttributes.VOTE_AVERAGE,movie.getVoteAverage());
    values.put(MovieAttributes.OVERVIEW,movie.getOverview());
    values.put(MovieAttributes.POPULARITY,movie.getPopularity());
    values.put(MovieAttributes.ORIGINAL_LANGUAGE,movie.getLanguage());
    values.put(MovieAttributes.RELEASE_DTAE,movie.getReleaseDate());
    values.put(MovieAttributes.VOTE_COUNT, movie.getVoteCount());

    return database.insert(MySQLiteHelper.TABLE_FAVOURITE, null,
        values);

  }


  public void deleteMovie(Movie movie) {
    long id = movie.getMovieId();
    System.out.println("Movie deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_FAVOURITE, MovieAttributes.ID
            + " = " + id, null);
  }

  public ArrayList<Movie> getAllMovies() {
    ArrayList<Movie> movieList = new ArrayList<Movie>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_FAVOURITE,
        allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Movie movie = cursorToFavMovie(cursor);
      movieList.add(movie);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return movieList;
  }


  private Movie cursorToFavMovie(Cursor cursor) {
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
