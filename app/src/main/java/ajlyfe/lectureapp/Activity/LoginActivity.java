package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.IAdapter;

import java.util.Random;

import ajlyfe.lectureapp.Adapters.TestActivity;
import ajlyfe.lectureapp.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static android.R.attr.animation;

public class LoginActivity extends AppCompatActivity {

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

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

        super.onCreate(savedInstanceState);
    }

    public void checkLoginIn() {
        preferenceSettings = getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);

        if (preferenceSettings.getBoolean("loggedIn", false)) {
            if (preferenceSettings.getBoolean("isTeacher", false)) {
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
        } else { // stupid user is new, and i hate them.
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

        //Backdoor, solely for testing
        Button testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TestActivity.class));
            }
        });
        //End backdoor

        preferenceEditor = preferenceSettings.edit();

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equalsIgnoreCase("")) {
                    Crouton.makeText(LoginActivity.this, "Invalid password", Style.ALERT, passwordCrouton).show();
                }

                if (username.getText().toString().equalsIgnoreCase("student")) {
                    preferenceEditor.putBoolean("loggedIn", true);
                    preferenceEditor.putBoolean("isTeacher", false);
                    preferenceEditor.commit();
                    startActivity(new Intent(LoginActivity.this, StudentActivityMain.class));
                    finish();
                }
                else if (username.getText().toString().equalsIgnoreCase("teacher")) {
                    preferenceEditor.putBoolean("loggedIn", true);
                    preferenceEditor.putBoolean("isTeacher", true);
                    preferenceEditor.commit();
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