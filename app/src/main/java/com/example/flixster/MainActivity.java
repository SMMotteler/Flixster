package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public String CURRENTLY_PLAYING_URL;
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());

       View view = binding.getRoot();
       setContentView(view);

        CURRENTLY_PLAYING_URL = String.format("https://api.themoviedb.org/3/movie/now_playing?api_key=%s", getString(R.string.movie_key));
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        // was RecyclerView rvMovies = findViewById(R.id.rvMovies); - replaced with binding
        movies = new ArrayList<>();

        // create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set the adapter on the recycler view
        binding.rvMovies.setAdapter(movieAdapter);

        // set a Layout Manager on the recycler view
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // gets the information from the API with asynchronous network requests
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CURRENTLY_PLAYING_URL, new JsonHttpResponseHandler() {

            // if the information can be fetched, return a success message and add
            // the information of the movies to the movies ArrayList, then call movieAdapter
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");

                JSONObject jsonObject = json.jsonObject;
                // If the JSON Array "results" exists, return a message with the
                // results and continue with the code
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+ results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: "+ results.toString());
                }
                // If the array doesn't exist, catch the exception and return a message logging
                // the error and the stack trace
                catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            // if the information can't be fetched, return a failure message
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}