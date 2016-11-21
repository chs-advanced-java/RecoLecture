package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static ajlyfe.lectureapp.Activity.TeacherClassOverview.AUTO_DESCRIPTION;
import static ajlyfe.lectureapp.Activity.TeacherClassOverview.NULL_CLASS;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Display splash screen
        setContentView(R.layout.splashscreen);

        final int[] counter = new int[1];
        RelativeLayout splashScreenHolder = (RelativeLayout) findViewById(R.id.splashScreenHolder);
        splashScreenHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0]++;
                if (counter[0] >= 10) {
                    Toast.makeText(LoginActivity.this, "You win a cookie", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        final ImageView circle = (ImageView) findViewById(R.id.splashCircle);
        final Animation grow = AnimationUtils.loadAnimation(this, R.anim.grow);

        pulsator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circle.setVisibility(View.VISIBLE);
                circle.startAnimation(grow);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkLoginIn();
                    }
                }, 1000);
            }
        }, new Random().nextInt(2500) + 500);

        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
    }

    public void checkLoginIn() {
        preferences = Utils.getPrefs(Utils.PREFS_CLASSES, this);
        editor = preferences.edit();

        if (preferences.getBoolean("loggedIn", false)) {
            if (preferences.getBoolean("isTeacher", false)) {
                startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
            }
            else {
                startActivity(new Intent(LoginActivity.this, StudentActivityMain.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
            }
        } else {
            Utils.verifyStoragePermissions(this);
            SharedPreferences.Editor editor = getSharedPreferences(Utils.PREFS_CLASSES, MODE_PRIVATE).edit();
            editor.putBoolean(Utils.PREF_DUMMY_CLASSES, true);

            ArrayList<TeacherClassCard> mClasses = new ArrayList<>();
            mClasses.add(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
            mClasses.add(1, new TeacherClassCard("Spanish I", AUTO_DESCRIPTION));
            mClasses.add(2, new TeacherClassCard("Spanish II", AUTO_DESCRIPTION));
            mClasses.add(3, new TeacherClassCard("Spanish III", AUTO_DESCRIPTION));
            mClasses.add(4, new TeacherClassCard("Spanish IV", AUTO_DESCRIPTION));
            mClasses.add(5, new TeacherClassCard("Spanish V", AUTO_DESCRIPTION));

            Gson gson = new Gson();
            String json = gson.toJson(mClasses);
            editor.putString(Utils.PREF_CLASS_LIST, json);
            editor.apply();

            startLogin();
        }
    }

    public void startLogin() {
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView colorBlock = (ImageView) findViewById(R.id.colorBlock);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                colorBlock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        colorBlock.startAnimation(fadeOut);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final ViewGroup passwordCrouton = (ViewGroup) findViewById(R.id.passwordCrouton);
        final ViewGroup usernameCrouton = (ViewGroup) findViewById(R.id.usernameCrouton);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equalsIgnoreCase("")) {
                    Crouton.makeText(LoginActivity.this, "Invalid password", Style.ALERT, passwordCrouton).show();
                }

                if (username.getText().toString().equalsIgnoreCase("student")) {
                    editor.putBoolean(Utils.PREF_LOGGED_IN, true);
                    editor.putBoolean(Utils.PREF_IS_TEACHER, false);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, StudentActivityMain.class));
                    finish();
                }
                else if (username.getText().toString().equalsIgnoreCase("teacher")) {
                    editor.putBoolean(Utils.PREF_LOGGED_IN, true);
                    editor.putBoolean(Utils.PREF_IS_TEACHER, true);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
                    finish();
                }
                else {
                    Crouton.makeText(LoginActivity.this, "Invalid username", Style.ALERT, usernameCrouton).show();
                }
            }
        });

        final TextView signup = (TextView) findViewById(R.id.signupPrompt);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationScreen.class));
            }
        });
    }
}