package arctouch.com.movieup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import arctouch.com.movieup.R;
import arctouch.com.movieup.model.Movie;

public class MovieCatalogAdapter extends ArrayAdapter<Movie> {

    public MovieCatalogAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
    }

    @Override
    public View getView(int position, View movieView, ViewGroup parent) {
        if (movieView == null) {
            LayoutInflater movieViewInflater = LayoutInflater.from(getContext());
            movieView = movieViewInflater.inflate(R.layout.content_movie_row, null);
        }

        Movie movie = getItem(position);

        if (movie != null) {
            TextView title = (TextView) movieView.findViewById(R.id.title);
            TextView genre = (TextView) movieView.findViewById(R.id.genre);
            TextView releaseDate = (TextView) movieView.findViewById(R.id.releaseDate);
            ImageView poster = (ImageView) movieView.findViewById(R.id.poster);

            if (title != null) {
                title.setText(movie.getTitle());
            }

            if (genre != null) {
                genre.setText(movie.getGenre());
            }

            if (releaseDate != null) {
                releaseDate.setText(movie.getReleaseDate());
            }

            if (poster != null) {
                poster.setImageBitmap(movie.getPoster());
            }

        }

        return movieView;
    }

}