package com.lejuno.popularmoviesapp.data;

/**
 * Created by nomoto on 8/21/15.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class MoviesContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.lejuno.popularmoviesapp";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MOVIES = "movies";

    /* Inner class that defines the table contents of the weather table */
    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE_NAME = "movies";
        // Online Id stored as integer
        public static final String COLUMN_MOVIES_ONLINE_ID = "online_id";
        // Title stored as string
        public static final String COLUMN_MOVIES_TITLE = "title";
        // Release date stored as string - 2015-08-21
        public static final String COLUMN_MOVIES_RELEASE = "release";
        // Synopsis stored as string
        public static final String COLUMN_MOVIES_SYNOPSIS = "synopsis";
        // Vote average stored as float
        public static final String COLUMN_MOVIES_VOTE_AVERAGE = "vote_average";
        // Poster URL stored as string
        public static final String COLUMN_MOVIES_POSTER_URL = "poster_url";
        // Popularity stored as float
        public static final String COLUMN_MOVIES_POPULARITY = "popularity";
        // Vote count stored as integer
        public static final String COLUMN_MOVIES_VOTE_COUNT = "vote_count";
        // Incoming position stored as integer
        public static final String COLUMN_MOVIES_INCOMING_POSITION = "incoming_position";

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

