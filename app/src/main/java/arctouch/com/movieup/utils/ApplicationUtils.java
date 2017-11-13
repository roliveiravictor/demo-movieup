package arctouch.com.movieup.utils;


import android.app.Application;
import android.content.Context;

public final class ApplicationUtils extends Application {

    public static String CATALOG = "CATALOG";

    private static String DB_API_K = "1f54bd990f1cdfb230adb312546d765d";
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ApplicationUtils.context = getApplicationContext();
    }

    public static Context getContext() {
        return ApplicationUtils.context;
    }

    public static String getMovieToken() {
        return DB_API_K;
    }
}
