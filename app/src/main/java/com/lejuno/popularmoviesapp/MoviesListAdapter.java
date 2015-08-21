package com.lejuno.popularmoviesapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nomoto on 8/18/15.
 */
public class MoviesListAdapter extends CursorAdapter {
    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView logoView;
        public final TextView textView1;
        public final TextView textView2;

        public ViewHolder(View view) {
            logoView = (ImageView) view.findViewById(R.id.list_item_movielogo);
            textView1 = (TextView) view.findViewById(R.id.list_item_textview1);
            textView2 = (TextView) view.findViewById(R.id.list_item_textview2);
        }
    }

    public MoviesListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // NOMOTO CHECK LATER
//        viewHolder.logoView.setImageResource(Utility.getIconResourceForWeatherCondition(
//                cursor.getInt(MoviestListFragment.COL_WEATHER_CONDITION_ID)));

        // Read weather forecast from cursor
        String text1 = cursor.getString(MoviesListFragment.COL_MOVIE_TITLE);
        // Find TextView and set weather forecast on it
        viewHolder.textView1.setText(text1);

        // Read weather forecast from cursor
        String text2 = cursor.getString(MoviesListFragment.COL_MOVIE_SYNOPSIS);
        // Find TextView and set weather forecast on it
        viewHolder.textView1.setText(text2);
    }
}