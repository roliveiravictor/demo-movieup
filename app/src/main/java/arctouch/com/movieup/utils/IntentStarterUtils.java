package arctouch.com.movieup.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public final class IntentStarterUtils {

    public static void goFromWithExtraBundleTo(FragmentActivity activity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(ApplicationUtils.getContext(), clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}


