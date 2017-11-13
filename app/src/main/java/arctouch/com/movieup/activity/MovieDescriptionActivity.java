package arctouch.com.movieup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import arctouch.com.movieup.R;
import arctouch.com.movieup.model.Movie;
import arctouch.com.movieup.utils.ApplicationUtils;

public class MovieDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        hideToolbar();

        Movie movie = getMovieBundle();
        fillLayout(movie);
    }

    private void hideToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    private Movie getMovieBundle() {
        Bundle bundle = getIntent().getExtras();
        Gson gSonMovie = new Gson();
        return gSonMovie.fromJson((String) bundle.get(ApplicationUtils.CATALOG), Movie.class);
    }

    private void fillLayout(Movie movie) {
        TextView title = (TextView) findViewById(R.id.title);
        TextView genre = (TextView) findViewById(R.id.genre);
        JustifiedTextView description = (JustifiedTextView) findViewById(R.id.description);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        ImageView poster = (ImageView) findViewById(R.id.poster);

        if (title != null) {
            title.setText(movie.getTitle());
        }

        if (genre != null) {
            genre.setText(movie.getGenre());
        }

        if (description != null) {
            description.setText(movie.getDescription());
        }

        if (releaseDate != null) {
            releaseDate.setText(movie.getReleaseDate());
        }

        if (poster != null) {
            poster.setImageBitmap(movie.getPoster());
        }
    }
}
