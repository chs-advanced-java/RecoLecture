/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private EditTextPreference changeUsername;
    private EditTextPreference changePassword;
    private SwitchPreference darkTheme;
    private Preference createLecture;
    private Preference signOut;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ProgressDialog loading;

    public static final String UPDATE_USER_URL_PT1 = "http://www.chs.mcvsd.org/sandbox/update-username-accountdb.php?username=";
            public static final String UPDATE_USER_URL_PT2 = "&newUsername=";

    public static final String UPDATE_PASS_URL_PT1 = "http://www.chs.mcvsd.org/sandbox/update-password-accountdb.php?username=";
        public static final String UPDATE_PASS_URL_PT2 = "&newPass=";

    public static final boolean PASSWORD = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, getActivity());
        editor = preferences.edit();

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

        signOut.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE).edit().clear().apply();

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture");
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (String aChildren : children) {
                        new File(dir, aChildren).delete();
                    }
                }

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return false;
            }
        });

        changeUsername.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener()
                {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue)
                    {
                        String newUsername = newValue.toString();
                        System.out.println(newUsername);
                        updateUserDatabase(preferences.getString(Utils.PREF_USERNAME, getString(R.string.preference_change_username)), newUsername);
                        editor.putString(Utils.PREF_USERNAME, newUsername);
                        editor.apply();
                        System.out.println(preferences.getString(Utils.PREF_USERNAME, getString(R.string.preference_change_username)));
                        Toast.makeText(getActivity(), "Username changed to " + newUsername + "!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
        );

        changePassword.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        String newPassword = newValue.toString();
                        System.out.println(preferences.getString(Utils.PREF_USERNAME, getString(R.string.preference_change_username)));
                        updatePasswordDatabase(preferences.getString(Utils.PREF_USERNAME, getString(R.string.preference_change_username)), newPassword);
                        editor.putString(Utils.PREF_PASSWORD, newPassword);
                        editor.apply();
                        System.out.println(preferences.getString(Utils.PREF_PASSWORD, getString(R.string.preference_change_password)));
                        Toast.makeText(getActivity(), "Your password has been changed!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }

        );
    }

    private void updateUserDatabase(final String oldUser, final String newUser) {
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String url = UPDATE_USER_URL_PT1 + oldUser + UPDATE_USER_URL_PT2 + newUser;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void updatePasswordDatabase(final String user, final String newPass) {
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String url = UPDATE_PASS_URL_PT1 + user + UPDATE_PASS_URL_PT2 + newPass;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)", null));
        }

        if (dummyClassesEnabled) {
            mClasses.set(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)", null));
            mClasses.add(new TeacherClassCard("Spanish I", AUTO_DESCRIPTION, null));
            mClasses.add(new TeacherClassCard("Spanish II", AUTO_DESCRIPTION, null));
            mClasses.add(new TeacherClassCard("Spanish III", AUTO_DESCRIPTION, null));
            mClasses.add(new TeacherClassCard("Spanish IV", AUTO_DESCRIPTION, null));
            mClasses.add(new TeacherClassCard("Spanish V", AUTO_DESCRIPTION, null));

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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {

    }
}
