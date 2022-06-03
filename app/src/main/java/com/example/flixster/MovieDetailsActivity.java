package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
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

    // when a movie is clicked, this method runs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // initializes the containers for the title, overview blurb, average rating, and poster
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar)findViewById(R.id.rbVoteAverage);
        moviePoster = (ImageView)findViewById(R.id.moviePoster);

        // unwraps the parcel containing the movie and logs its information
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // adds the movie information to the holder

        // gets the title and overview blurb of the movie
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // edits the rating to work for the container: out of 5 rather than out of 2
        float averageRating = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(averageRating/2.0f);

        // based on the orientation of the phone, initializes the placeholder and image to the correct
        // styles (backdrop photo and backdrop placeholder for landscape, poster and portrait placeholder
        // for portrait)
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

        // adds the photo to the container with a placeholder image
        Glide.with(context).load(imageURL).placeholder(placeholder).into(moviePoster);

    }
}