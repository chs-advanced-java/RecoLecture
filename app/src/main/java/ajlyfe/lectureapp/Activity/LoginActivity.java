package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.IAdapter;

import ajlyfe.lectureapp.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static android.R.attr.animation;

public class LoginActivity extends AppCompatActivity {

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Display splash screen
        setContentView(R.layout.splashscreen);
        final ImageView logoRight = (ImageView) findViewById(R.id.splashLogoRight);
        final ImageView logoLeft = (ImageView) findViewById(R.id.splashLogoLeft);

        final Animation[] animations = new Animation[4];

        animations[0] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse_right);
        animations[1] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse_right_inverse);
        animations[2] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse_left);
        animations[3] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse_left_inverse);

        animations[0].setInterpolator(new DecelerateInterpolator(5.0f));
        animations[1].setInterpolator(new LinearInterpolator());
        animations[2].setInterpolator(new LinearInterpolator());
        animations[3].setInterpolator(new LinearInterpolator());

        animations[0].setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                animations[1].setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        logoRight.startAnimation(animations[0]);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                logoRight.startAnimation(animations[1]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        animations[2].setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                animations[3].setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        logoRight.startAnimation(animations[2]);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                logoRight.startAnimation(animations[3]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        //logoRight.startAnimation(animations[0]);
        logoLeft.startAnimation(animations[2]);
        */ startLogin();

        super.onCreate(savedInstanceState);
    }

    public void startLogin() {
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final ViewGroup passwordCrouton = (ViewGroup) findViewById(R.id.passwordCrouton);
        final ViewGroup usernameCrouton = (ViewGroup) findViewById(R.id.usernameCrouton);

        preferenceSettings = getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        if (preferenceSettings.getBoolean("loggedIn", false) == true) {
            if (preferenceSettings.getBoolean("isTeacher", false) == true) {
                startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
            }
            else {
                startActivity(new Intent(LoginActivity.this, StudentActivityMain.class));
            }
        }

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

            }
        });
    }
}