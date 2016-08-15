package android.nanodegree.contentprovider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.nanodegree.dao.MovieDAO;
import android.nanodegree.dao.MySQLiteHelper;
import android.nanodegree.sportify.constants.MovieAttributes;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {
 MovieDAO movieDAO = null;
 private SQLiteDatabase database;

 static final String PROVIDER_NAME = "android.nanodegree.contentprovider.MovieContentProvider";
 static final String URL = "content://" + PROVIDER_NAME + "/favmovies";
 static final Uri CONTENT_URI = Uri.parse(URL);

 static final String id = "id";
 static final String name = "name";
 static final int uriCode = 1;
 static final UriMatcher uriMatcher;
 private static HashMap<String, String> values;

 static {
  uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  uriMatcher.addURI(PROVIDER_NAME, "favmovies", uriCode);
 }


 @Override
 public String getType(Uri uri) {
  switch (uriMatcher.match(uri)) {
   case uriCode:
    return "vnd.android.cursor.dir/favmovies";

   default:
    throw new IllegalArgumentException("Unsupported URI: " + uri);
  }
 }

 @Override
 public Uri insert(Uri uri, ContentValues values) {
   return null;
 }

 @Override
 public int delete(Uri uri, String selection, String[] selectionArgs) {
  return 0;
 }

 @Override
 public boolean onCreate() {
  Context context = getContext();


  if (movieDAO == null) {
     movieDAO = new MovieDAO(context);
     movieDAO.open();
     database = movieDAO.getDataBase(context);
   return true;
  }
  return false;
 }

 @Override
 public Cursor query(Uri uri, String[] projection, String selection,
                     String[] selectionArgs, String sortOrder) {
  SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
  qb.setTables(MySQLiteHelper.TABLE_FAVOURITE);

  switch (uriMatcher.match(uri)) {
   case uriCode:
    qb.setProjectionMap(values);
    break;
   default:
    throw new IllegalArgumentException("Unknown URI " + uri);
  }
  if (sortOrder == null || sortOrder == "") {
   sortOrder = MovieAttributes.TITLE;
  }

  Cursor cursor = qb.query(database, projection, selection, selectionArgs, null,
          null, sortOrder);
  cursor.setNotificationUri(getContext().getContentResolver(), uri);
  return cursor;
 }

 @Override
 public int update(Uri uri, ContentValues values, String selection,
                   String[] selectionArgs) {
  return 0;
 }
}
