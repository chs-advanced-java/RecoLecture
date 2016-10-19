package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ajlyfe.lectureapp.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LoginActivity extends AppCompatActivity {

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final ViewGroup passwordCrouton = (ViewGroup) findViewById(R.id.passwordCrouton);
        final ViewGroup usernameCrouton = (ViewGroup) findViewById(R.id.usernameCrouton);

        preferenceSettings = getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        preferenceEditor.putBoolean("LoggedIn", false);
        preferenceEditor.putBoolean("isTeacher", false);
        preferenceEditor.commit();

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
                    preferenceEditor.putBoolean("LoggedIn", true);
                    preferenceEditor.putBoolean("isTeacher", false);
                    preferenceEditor.commit();
                    startActivity(new Intent(LoginActivity.this, StudentActivityMain.class));
                    finish();
                }
                else if (username.getText().toString().equalsIgnoreCase("teacher")) {
                    preferenceEditor.putBoolean("LoggedIn", true);
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