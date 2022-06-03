package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView moviePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar)findViewById(R.id.rbVoteAverage);
        moviePoster = (ImageView)findViewById(R.id.moviePoster);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        float averageRating = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(averageRating/2.0f);

        String imageURL;
        int placeholder;
        Context context = this;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageURL = movie.getBackdropPath();
            placeholder = R.drawable.flicks_backdrop_placeholder;
        }
        else{
            imageURL = movie.getPosterPath();
            placeholder = R.drawable.flicks_movie_placeholder;
        }

        // Glide.with(context).load(movie.getPosterPath()).placeholder(R.drawable.movie_placeholder).into(ivPoster);
        Glide.with(context).load(imageURL).placeholder(placeholder).into(moviePoster);

    }
}