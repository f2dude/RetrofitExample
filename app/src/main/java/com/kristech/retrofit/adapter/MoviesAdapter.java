package com.kristech.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kristech.retrofit.model.Movie;
import com.kristech.retrofit.rest.ItemEventHandler;
import com.kristech.retrofit.retrofitexample.R;

import java.util.List;

/**
 * Created by mobileteam on 17/01/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private ItemEventHandler itemEventHandler;

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context, ItemEventHandler itemEventHandler) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.itemEventHandler = itemEventHandler;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, ":::::Item is clicked:::::" + this.getAdapterPosition());
            itemEventHandler.recyclerViewItemEvent(movies.get(this.getAdapterPosition()));
        }
    }
}
