package arctouch.com.movieup.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import arctouch.com.movieup.adapter.MovieCatalogAdapter;
import arctouch.com.movieup.model.Movie;
import arctouch.com.movieup.utils.ApplicationUtils;

public class MovieInfoParser extends AsyncTask<String, String, String> {

    private static String POSTER_URL = "https://image.tmdb.org/t/p/w500/";

    private List<Movie> movieCatalog = null;
    private MovieCatalogAdapter movieCatalogAdapter;

    private HttpURLConnection connection = null;
    private BufferedReader infoReader = null;

    public MovieInfoParser(MovieCatalogAdapter movieCatalogAdapter, List<Movie> movieCatalog) {
        this.movieCatalogAdapter = movieCatalogAdapter;
        this.movieCatalog = movieCatalog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        try {
            connectMovieDB();
            String bufferedMovie = bufferMovie();
            parseMovieData(bufferedMovie);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (infoReader != null) {
                    infoReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        movieCatalogAdapter.notifyDataSetChanged();
    }

    private void connectMovieDB() {
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/76341?api_key=" + ApplicationUtils.getMovieToken());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String bufferMovie() {
        StringBuffer buffer = new StringBuffer();
        String line = "";

        try {
            InputStream connectionStream = connection.getInputStream();
            infoReader = new BufferedReader(new InputStreamReader(connectionStream));

            while ((line = infoReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private void parseMovieData(String result) {
        try {
            JSONObject jSONMovieObject = new JSONObject(result);

            Movie movie = new Movie();

            //Instead of fixed string, on real case would use values/strings.xml
            movie.setTitle("Title: " + jSONMovieObject.getString("original_title"));
            movie.setReleaseDate("Release Date: " + jSONMovieObject.getString("release_date"));
            movie.setPoster(getBitmapFromUrl(POSTER_URL + jSONMovieObject.getString("poster_path")));
            movie.setDescription("Description: " + jSONMovieObject.getString("overview"));

            String genres = "Genres: ";
            JSONArray jsonArray = new JSONArray(jSONMovieObject.getString("genres"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj1 = jsonArray.getJSONObject(i);
                genres += obj1.getString("name");

                if (i + 1 != jsonArray.length()) {
                    genres += ", ";
                } else {
                    genres += ".";
                }
            }
            movie.setGenre(genres);

            addMovieToCatalog(movie);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Bitmap getBitmapFromUrl(String posterPath) {
        Bitmap bitmapImage = null;
        try {
            URL url = new URL(posterPath);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInput = new BufferedInputStream(inputStream);

            bitmapImage = BitmapFactory.decodeStream(bufferedInput);

            bufferedInput.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e("Debug", "Error getting bitmap", e);
        }
        return bitmapImage;
    }

    private void addMovieToCatalog(Movie movie) {
        movieCatalog.add(movie);
    }
}