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
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    private static final String PREF_DARK_THEME = "useDarkTheme";
    private static final String PREF_NAME = "settings";

    // Storage Permissions variables
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static void setCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            activity.setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
    }

    public static View setCustomAdapterTheme(Activity activity, LayoutInflater inflater,
                                             @LayoutRes int resource, ViewGroup container, boolean attachToRoot) {
        SharedPreferences preferences = activity.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
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