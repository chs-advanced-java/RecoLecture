package ajlyfe.lectureapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class Utils {

    public static final String PREFS_SETTINGS = "settings";
    public static final String PREFS_CLASSES = "classes";
    public static final String PREF_DARK_THEME = "useDarkTheme";
    public static SharedPreferences prefsSettings;
    public static SharedPreferences prefsClasses;

    // Storage Permissions variables
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static SharedPreferences getPrefs(String prefsFile, Activity activity) {
        return activity.getSharedPreferences(prefsFile, MODE_PRIVATE);
    }
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void setCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFS_SETTINGS, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            activity.setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
    }

    public static View setCustomAdapterTheme(Activity activity, LayoutInflater inflater,
                                             @LayoutRes int resource, ViewGroup container, boolean attachToRoot) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFS_SETTINGS, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        Context contextThemeWrapper;

        if (useDarkTheme) {
            contextThemeWrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.AppTheme_Dark_NoActionBar);
        } else {
            contextThemeWrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.AppTheme_NoActionBar);
        }

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        return localInflater.inflate(resource, container, attachToRoot);
    }



    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Granted
                } else {
                    //Denied
                }
                break;
            }
        }
    }*/
}