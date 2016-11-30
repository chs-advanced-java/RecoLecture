package ajlyfe.lectureapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Inflater;

import ajlyfe.lectureapp.Adapters.TeacherClassCard;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class Utils {

    public static final String SHARED_PREFERENCES = "shared_preferences";
    public static final String PREF_DARK_THEME = "useDarkTheme";
    public static final String PREF_DUMMY_CLASSES = "useDummyClasses";
    public static final String PREF_LOGGED_IN = "loggedIn";
    public static final String PREF_IS_TEACHER = "isTeacher";
    public static final String PREF_CLASS_LIST = "classList";

    // Escape characters for security purposes
    public static final Character[] ESCAPE_CHARACTERS = {
            '\"',
            '\'',
            '&',
            ';'
    };

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

    public static void setClassList(ArrayList<TeacherClassCard> mClasses, Activity activity) {
        SharedPreferences.Editor editor = getPrefs(SHARED_PREFERENCES, activity).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mClasses);
        editor.putString(Utils.PREF_CLASS_LIST, json);
        editor.apply();
    }

    public static ArrayList<TeacherClassCard> getClassList(Activity activity) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TeacherClassCard>>(){}.getType();
        String json = getPrefs(SHARED_PREFERENCES, activity).getString(Utils.PREF_CLASS_LIST, null);

        return gson.fromJson(json, type);
    }

    public static void setCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            activity.setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
    }

    public static View setCustomAdapterTheme(Activity activity, LayoutInflater inflater,
                                             @LayoutRes int resource, ViewGroup container, boolean attachToRoot) {
        SharedPreferences preferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
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

    public static int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }
}