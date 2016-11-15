package ajlyfe.lectureapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    private static final String PREF_DARK_THEME = "useDarkTheme";
    private static final String PREF_NAME = "settings";

    public static void setCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            activity.setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
    }

    public static View setCustomAdapterTheme(Activity activity, LayoutInflater inflater, @LayoutRes int resource, ViewGroup container, boolean attachToRoot) {
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
}