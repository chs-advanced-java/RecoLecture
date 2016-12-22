package ajlyfe.lectureapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ajlyfe.lectureapp.Activity.LoginActivity;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

import static ajlyfe.lectureapp.Activity.TeacherClassOverview.AUTO_DESCRIPTION;
import static ajlyfe.lectureapp.Activity.TeacherClassOverview.NULL_CLASS;
import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragment {

    private EditTextPreference changeUsername;
    private EditTextPreference changePassword;
    private SwitchPreference darkTheme;
    //private SwitchPreference dummyClasses;
    private Preference createLecture;
    private Preference signOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set preference file
        getPreferenceManager().setSharedPreferencesName(Utils.SHARED_PREFERENCES);

        // "Inflate" preferences
        addPreferencesFromResource(R.xml.app_preferences);

        // Account category
        changeUsername = (EditTextPreference) findPreference(getString(R.string.preference_change_username));
        changePassword = (EditTextPreference) findPreference(getString(R.string.preference_change_password));

        // Appearance category
        darkTheme = (SwitchPreference) findPreference(getString(R.string.preference_dark_theme));

        // Developer category
        //dummyClasses = (SwitchPreference) findPreference(getString(R.string.preference_dummy_classes));
        createLecture = (Preference) findPreference(getString(R.string.preference_create_lecture));
        signOut = (Preference) findPreference(getString(R.string.preference_sign_out));

        Drawable changeUsernameIcon = getResources().getDrawable(R.drawable.ic_account_box_black_24dp);
        changeUsernameIcon.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP));
        changeUsername.setIcon(changeUsernameIcon);

        Drawable changePasswordIcon = getResources().getDrawable(R.drawable.ic_lock_black_24dp);
        changePasswordIcon.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP));
        changePassword.setIcon(changePasswordIcon);

        setClickListeners();
    }

    private void setClickListeners() {
        darkTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                toggleTheme(darkTheme.isChecked());
                return false;
            }
        });

        /*
        dummyClasses.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                toggleDummyClasses(dummyClasses.isChecked());
                return false;
            }
        });*/

        createLecture.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                createFakeLecture();
                return false;
            }
        });

        signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit().clear().apply();


                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return false;
            }
        });
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getActivity().getIntent();
        getActivity().finish();

        startActivity(intent);
    }

    private void toggleDummyClasses(boolean dummyClassesEnabled) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(Utils.PREF_DUMMY_CLASSES, dummyClassesEnabled);

        ArrayList<TeacherClassCard> mClasses = Utils.getTeacherClassList(getActivity());

        assert mClasses != null;
        if (mClasses.size() > 0) {
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
        }

        if (dummyClassesEnabled) {
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
            mClasses.add(new TeacherClassCard("Spanish I", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish II", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish III", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish IV", AUTO_DESCRIPTION));
            mClasses.add(new TeacherClassCard("Spanish V", AUTO_DESCRIPTION));

            Utils.setTeacherClassList(mClasses, getActivity());
        } else {
            ArrayList<TeacherClassCard> keepMe = new ArrayList<>();

            for (int i = 0; i < mClasses.size(); i++) {
                if (!mClasses.get(i).isDummy()) {
                    keepMe.add(mClasses.get(i));
                }
            }

            File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/");
            File[] filesArray = parentDir.listFiles();

            if (filesArray != null) {
                try {
                    for (File thisFile : filesArray) {
                        if (thisFile.toString().contains("Spanish")) {
                            thisFile.delete();
                        }
                    }
                } catch (IndexOutOfBoundsException exc) {
                    exc.printStackTrace();
                }
            }

            mClasses = keepMe;

            Utils.setTeacherClassList(mClasses, getActivity());
        }

        editor.apply();
    }

    private void createFakeLecture() {
        InputStream in = getResources().openRawResource(R.raw.victory);
        FileOutputStream out = null;
        byte[] buff = new byte[1024];
        int read;

        try {
            out = new FileOutputStream(Utils.getLecturePath() + Utils.UNIQUE_STRING + getLectureNumber() + ".1a2b3c" + ".mp3", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private int getLectureNumber() {
        int numberOfLectures = 0;

        File parentDir = new File(Utils.getLecturePath());
        File[] files = parentDir.listFiles();

        if (files == null) {
            return 0;
        }

        for (File thisFile : files) {
            if (thisFile.toString().contains(Utils.UNIQUE_STRING)) {
                numberOfLectures++;
            }
        }

        return numberOfLectures;
    }
}
