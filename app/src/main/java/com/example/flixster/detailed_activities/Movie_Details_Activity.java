package com.example.flixster.detailed_activities;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class Movie_Details_Activity extends AppCompatActivity {

    // the movie that is to be displayed
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetails", String.format("Showing details for '%s'", movie.getTitle()));
    }
}