package com.lejuno.popularmoviesapp.data;

/**
 * Created by nomoto on 8/21/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lejuno.popularmoviesapp.data.MoviesContract.MoviesEntry;
/**
 * Manages a local database for movies data.
 */
public class MoviesDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                MoviesEntry.COLUMN_MOVIES_ONLINE_ID + " INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_RELEASE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_SYNOPSIS + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_VOTE_AVERAGE + " REAL NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_POSTER_URL + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_POPULARITY + " REAL NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_VOTE_COUNT + " INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_INCOMING_POSITION + " INTEGER NOT NULL, ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
