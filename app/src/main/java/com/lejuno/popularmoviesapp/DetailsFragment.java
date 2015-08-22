package com.lejuno.popularmoviesapp;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lejuno.popularmoviesapp.data.MoviesContract.MoviesEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private String mMovies;
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            MoviesEntry.TABLE_NAME + "." + MoviesEntry._ID,
            MoviesEntry.COLUMN_MOVIES_ONLINE_ID,
            MoviesEntry.COLUMN_MOVIES_TITLE,
            MoviesEntry.COLUMN_MOVIES_RELEASE,
            MoviesEntry.COLUMN_MOVIES_SYNOPSIS,
            MoviesEntry.COLUMN_MOVIES_VOTE_AVERAGE,
            MoviesEntry.COLUMN_MOVIES_POSTER_URL,
            MoviesEntry.COLUMN_MOVIES_POPULARITY,
            MoviesEntry.COLUMN_MOVIES_VOTE_COUNT,
            MoviesEntry.COLUMN_MOVIES_INCOMING_POSITION
    };

    // These indices are tied to MOVIE_COLUMNS.  If MOVIE_COLUMNS changes, these
    // must change.
    static final int COL_MOVIE_ID = 0;
    static final int COL_MOVIE_ONLINE_ID = 1;
    static final int COL_MOVIE_TITLE = 2;
    static final int COL_MOVIE_RELEASE = 3;
    static final int COL_MOVIE_SYNOPSIS = 4;
    static final int COL_MOVIE_VOTE_AVERAGE = 5;
    static final int COL_MOVIE_POSTER_URL = 6;
    static final int COL_MOVIE_POPULARITY = 7;
    static final int COL_MOVIE_VOTE_COUNT = 8;
    static final int COL_MOVIE_INCOMING_POSITION = 9;

    //title, release date, movie poster, vote average, and plot synopsis
    private ImageView mPosterView;
    private TextView mMovieTitleView;
    private TextView mMovieReleaseView;
    private TextView mMovieVoteAverateView;
    private TextView mMovieVoteCountView;
    private TextView mMoviePopularityView;
    private TextView mMovieSynopsisView;

    public DetailsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DetailsFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        mPosterView = (ImageView) rootView.findViewById(R.id.details_poster);
        mMovieTitleView = (TextView) rootView.findViewById(R.id.details_title);
        mMovieReleaseView = (TextView) rootView.findViewById(R.id.details_release);
        mMovieVoteAverateView = (TextView) rootView.findViewById(R.id.details_vote_average);
        mMovieVoteCountView = (TextView) rootView.findViewById(R.id.details_vote_count);
        mMoviePopularityView = (TextView) rootView.findViewById(R.id.details_popularity);
        mMovieSynopsisView = (TextView) rootView.findViewById(R.id.details_synopsis);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            // Read weather condition ID from cursor
            int moviesPosterURL = data.getInt(COL_MOVIE_POSTER_URL);

            // Use weather art image
//            mPosterView.setImageResource(Utility.getPosterByUrl(moviesPosterURL));
            mPosterView.setImageResource(R.mipmap.ic_launcher);

            String title = data.getString(COL_MOVIE_TITLE);
            mMovieTitleView.setText(title);

            String release = data.getString(COL_MOVIE_RELEASE);
            mMovieReleaseView.setText(release);

            String synopsis = data.getString(COL_MOVIE_SYNOPSIS);
            mMovieSynopsisView.setText(synopsis);

            int voteCount = data.getInt(COL_MOVIE_VOTE_COUNT);
            mMovieVoteCountView.setText(Integer.toString(voteCount));

            float voteAverage = data.getFloat(COL_MOVIE_VOTE_AVERAGE);
            mMovieVoteAverateView.setText(Float.toString(voteAverage));

            float popularity = data.getFloat(COL_MOVIE_POPULARITY);
            mMoviePopularityView.setText(Float.toString(popularity));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}