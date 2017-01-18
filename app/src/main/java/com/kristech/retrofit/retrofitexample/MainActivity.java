package com.kristech.retrofit.retrofitexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.kristech.retrofit.adapter.MoviesAdapter;
import com.kristech.retrofit.model.Movie;
import com.kristech.retrofit.model.MoviesResponse;
import com.kristech.retrofit.rest.ApiClient;
import com.kristech.retrofit.rest.ApiInterface;
import com.kristech.retrofit.rest.ItemEventHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Reference URL: http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
 */

public class MainActivity extends AppCompatActivity implements Callback<MoviesResponse>, ItemEventHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "fb70501c8e30a2cee6ca71c91d53a796";
    private RecyclerView recyclerView;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();

            return;
        }

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClientInstance().create(ApiInterface.class);

        Call<MoviesResponse> topRatedMoviesCall = apiService.getTopRatedMovies(API_KEY);
        topRatedMoviesCall.enqueue(this);

        //For making multiple calls in a single activity, make use of the following way mentioned below

//        Call<MoviesResponse> topRatedMoviesCall = apiService.getTopRatedMovies(API_KEY);
//        topRatedMoviesCall.enqueue(new Callback<MoviesResponse>() {
//            @Override
//            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MoviesResponse> call, Throwable t) {
//
//            }
//        });
//
//        Call<MovieDetails> movieDetailsCall = apiService.getMovieDetails(278, API_KEY);
//        movieDetailsCall.enqueue(new Callback<MovieDetails>() {
//            @Override
//            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieDetails> call, Throwable t) {
//
//            }
//        });

    }

    @Override
    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
        List<Movie> movies = response.body().getResults();
        Log.d(TAG, "Number of movies received: " + movies.size());
        recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext(), this));

    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {
        Log.e(TAG, t.toString());
    }

    @Override
    public void recyclerViewItemEvent(Movie movie) {
        Log.d(TAG, "Movie Details:::::" + movie);
    }
}
