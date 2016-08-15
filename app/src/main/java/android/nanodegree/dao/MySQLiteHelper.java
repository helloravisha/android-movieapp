package android.nanodegree.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nanodegree.sportify.constants.MovieAttributes;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_FAVOURITE = "movie";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_MOVIEID = "movieid";


  private static final String DATABASE_NAME = "movie.db";
  private static final int DATABASE_VERSION = 1;

  private static final  String TITLE = "title";
  private static final  String RELEASE_DATE = "releaseDate";
  private static final  String OVERVIEW = "overview";
  private static final  String VOTE_AVERAGE = "voteAverage";
  private static final  String VOTE_COUNT =  "voteCount";
  private static final  String LANGUAGE = "language";
  private static final  String BACKDROP_PATH = "backdropPath";
  private static final  String  POPULARITY = "popularity";
  private static final String MOVIE_ID = "movieId";



  // Table creation sql statement


  private static final String TABLE_MOVIE = "create table movie("+ MovieAttributes.ID+" integer,"+ MovieAttributes.TITLE+" text,"+MovieAttributes.RELEASE_DTAE+" text,"
           +MovieAttributes.OVERVIEW+" text,"+MovieAttributes.VOTE_AVERAGE+" real,"+MovieAttributes.VOTE_COUNT+" real,"+MovieAttributes.ORIGINAL_LANGUAGE+" text,"+
           MovieAttributes.BACKDROP_PATH+" text,"+MovieAttributes.POPULARITY+" real);";



  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(TABLE_MOVIE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
    onCreate(db);
  }

} 