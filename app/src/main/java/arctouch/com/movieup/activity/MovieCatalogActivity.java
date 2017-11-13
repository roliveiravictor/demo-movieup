package arctouch.com.movieup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import arctouch.com.movieup.R;
import arctouch.com.movieup.adapter.MovieCatalogAdapter;
import arctouch.com.movieup.model.Movie;
import arctouch.com.movieup.task.MovieInfoParser;
import arctouch.com.movieup.utils.ApplicationUtils;
import arctouch.com.movieup.utils.IntentStarterUtils;

public class MovieCatalogActivity extends AppCompatActivity {


    private MovieCatalogAdapter movieAdapter;
    private List<Movie> movieCatalog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_catalog);

        displayToolBar();
        loadMovieCatalog();
    }

    private void displayToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadMovieCatalog() {
        loadMovieView();
        loadMovieDB();
    }

    private void loadMovieView() {
        ListView movieCatalogListView = (ListView) findViewById(R.id.movieCatalogView);
        movieAdapter = new MovieCatalogAdapter(this, R.layout.content_movie_row, movieCatalog);
        movieCatalogListView.setAdapter(movieAdapter);

        //Fixed movie selection - movieCatalog.get(0) - Here should apply the rule "not be limited to show only the first 20 movies as returned by the API."
        movieCatalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle movieCatalogBundle = new Bundle();
                movieCatalogBundle.putSerializable(ApplicationUtils.CATALOG, new Gson().toJson(movieCatalog.get(0)));
                IntentStarterUtils.goFromWithExtraBundleTo(MovieCatalogActivity.this, MovieDescriptionActivity.class, movieCatalogBundle);
            }
        });
    }

    private void loadMovieDB() {
        MovieInfoParser movieParser = new MovieInfoParser(movieAdapter, movieCatalog);
        movieParser.execute();
    }

}
