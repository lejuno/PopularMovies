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
import android.widget.AdapterView;
import android.widget.ListView;

import com.lejuno.popularmoviesapp.data.MoviesContract;
import com.lejuno.popularmoviesapp.data.MoviesContract.MoviesEntry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String LOG_TAG = MoviesListFragment.class.getSimpleName();

    MoviesListAdapter mMoviesListAdapter;
    private ListView mListView;

    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    private static final int MOVIES_LOADER = 0;
    // For the forecast view we're showing only a small subset of the stored data.
    // Specify the columns we need.
    private static final String[] MOVIES_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            MoviesContract.MoviesEntry.TABLE_NAME + "." + MoviesContract.MoviesEntry._ID,
            MoviesEntry.COLUMN_MOVIES_ONLINE_ID,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_TITLE,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_RELEASE,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_SYNOPSIS,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_VOTE_AVERAGE,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_POSTER_URL,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_POPULARITY,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_VOTE_COUNT,
            MoviesContract.MoviesEntry.COLUMN_MOVIES_INCOMING_POSITION
    };


    // These indices are tied to MOVIE_COLUMNS.  If MOVIE_COLUMNS changes, these
    // must change.
    static final int COL_MOVIE_ID = 0;
    static final int COL_MOVIE_ONLINE_DATABASE_ID = 1;
    static final int COL_MOVIE_TITLE = 2;
    static final int COL_MOVIE_RELEASE = 3;
    static final int COL_MOVIE_SYNOPSIS = 4;
    static final int COL_MOVIE_VOTE_AVERAGE = 5;
    static final int COL_MOVIE_POSTER_URL = 6;
    static final int COL_MOVIE_POPULARITY = 7;
    static final int COL_MOVIE_VOTE_COUNT = 8;
    static final int COL_MOVIE_INCOMING_POSITION = 9;

    public MoviesListFragment() {
        // Required empty constructor.
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

/*  // NOMOTO TO CHECK
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The ForecastAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mMoviesListAdapter = new MoviesListAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(mMoviesListAdapter);
        // We'll call our MainActivity
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
   //                 ((Callback) getActivity())
   //                         .onItemSelected(MoviesContract.WeatherEntry.buildWeatherLocationWithDate(
   //                                 cursor.getLong(COL_WEATHER_DATE)
   //                         ));
                    ((Callback) getActivity())
                            .onItemSelected(MoviesEntry.buildMoviesUri(cursor.getLong(COL_MOVIE_ID))
                            );
                }
                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    // since we read the location when we create the loader, all we need to do is restart things
    void onLocationChanged( ) {
        updateMovies();
        getLoaderManager().restartLoader(MOVIES_LOADER, null, this);
    }

    private void updateMovies() {
        //MoviesSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // To only show current and future dates, filter the query to return weather only for
        // dates after or including today.

        // Sort order:  Ascending, by date.
        // NOMOTO - NEED TO CHECK
        // MAY NEED TO INPUT AN ORDER IN DATA BASE
        String sortOrder = MoviesEntry.COLUMN_MOVIES_INCOMING_POSITION + " ASC";

        Uri moviesListUri = MoviesContract.MoviesEntry.buildMoviesUri(COL_MOVIE_ID);

        return new CursorLoader(getActivity(),
                moviesListUri,
                MOVIES_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMoviesListAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMoviesListAdapter.swapCursor(null);
    }
}
